package mahout.NBS;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.apache.mahout.classifier.ClassifierResult;
import org.apache.mahout.classifier.naivebayes.AbstractNaiveBayesClassifier;
import org.apache.mahout.classifier.naivebayes.BayesUtils;
import org.apache.mahout.classifier.naivebayes.NaiveBayesModel;
import org.apache.mahout.classifier.naivebayes.StandardNaiveBayesClassifier;
import org.apache.mahout.common.AbstractJob;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.vectorizer.TFIDF;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;

public class BayesClassifyJob extends AbstractJob {  
	
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
        ToolRunner.run(new Configuration(), new BayesClassifyJob(),args);  
    }  
      
    public int run(String[] args) throws Exception {  
        addInputOption();  
        addOutputOption();  
        addOption("model","m", "The file where bayesian model store ");  
        addOption("labelIndex","l", "The labels labelIndex ");  
        if (parseArguments(args) == null) {  
              return -1;  
        }  
		Path input = getInputPath();
		Path output = getOutputPath();
		String labelIndex = getOption("labelIndex");
		String modelPath = getOption("model");
		Configuration conf = getConf();
		conf.set("labelIndex", labelIndex);
		HadoopUtil.cacheFiles(new Path(modelPath), conf);
		HadoopUtil.delete(conf, output);
 
		Job classifyJob = new Job(conf,"Use bayesian model to classify the  input:"+input.getName());
		classifyJob = prepareJob(getInputPath(), 
                getOutputPath(), 
                SequenceFileInputFormat.class,
                BayesClassifyMapper.class,
                Text.class, 
                VectorWritable.class, 
                SequenceFileOutputFormat.class);
        classifyJob.setJar("/home/dashengong/remoteJAR/BayesClassifyJob.jar");
		if (classifyJob.waitForCompletion(true)) {
			return 0;
		}
        return -1;  
    }  
    /** 
     *  Mapper 类
     *  
     */  
    public static class BayesClassifyMapper extends Mapper<Text, Text, Text, VectorWritable>{  
        private AbstractNaiveBayesClassifier classifier; 
        private String labelIndex;
        private Map<Integer, String> labelMap;
        //训练分类器时生成的训练集数据，用于对新数据分类时构造tfidf-vector
        private String dictionaryPath = "hdfs://172.18.200.135:8020/output/bayes/20news-all/20news-all-vectors/dictionary.file-0";
        private String documentCountPath = "hdfs://172.18.200.135:8020/output/bayes/20news-all/20news-all-vectors/df-count/part-r-00000";

		@Override
		public void setup(Context context) throws IOException,InterruptedException {
			Configuration conf = context.getConfiguration();
			Path modelPath = HadoopUtil.getSingleCachedFile(conf);
			// 读取模型
			NaiveBayesModel model = NaiveBayesModel.materialize(modelPath, conf);
			// 生成分类器
			classifier = new StandardNaiveBayesClassifier(model);
		}

         /**
          * 分类
          */
          @Override  
          public void map(Text key, Text  value, Context context) throws IOException, InterruptedException { 
			Configuration conf = context.getConfiguration();
			String label = "";
        	//获取tfidf-vector
			Vector vector = new RandomAccessSparseVector(10000);
			vector = createTfidfVector(value);
  	        //分类
            Vector result = classifier.classifyFull(vector);  
            
           //构造输出 
            labelIndex=conf.get("labelIndex");  
            labelMap = BayesUtils.readLabelIndex(conf, new Path(labelIndex)); 
            int bestIdx = Integer.MIN_VALUE;  
            double bestScore = Long.MIN_VALUE;  
            for (Vector.Element element : result.all()) {  
                if (element.get() > bestScore) {  
                    bestScore = element.get();  
                    bestIdx = element.index();  
                }  
            }  
            if (bestIdx != Integer.MIN_VALUE) {  
                ClassifierResult classifierResult = new ClassifierResult(labelMap.get(bestIdx), bestScore);  
                label = classifierResult.getLabel();  
            }  
            //文件名：分类   分类属性   
            context.write(new Text(Pattern.compile("/").split(key.toString())[1]+":"+label), new VectorWritable(result));  
          }
          
          /**
           * 构造tfidf-vector
           * @param value
           * @return vector
           * @throws IOException
           */
          public Vector createTfidfVector(Text value) throws IOException {
			Configuration conf = new Configuration();
			// 读取 dictionary、df-count
			Map<String, Integer> dictionary = readDictionnary(conf, new Path(dictionaryPath));
			Map<Integer, Long> dc = readDocumentCount(conf, new Path(documentCountPath));

			@SuppressWarnings("resource")
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
			// 读取训练集包含的文档个数
			int documentCount = dc.get(-1).intValue();

			// 待分类文本
			String content = value.toString();

			Multiset<String> words = ConcurrentHashMultiset.create();

			TokenStream ts = analyzer.tokenStream("text", new StringReader(content));
			CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);
			ts.reset();
			int wordCount = 0;
			// 统计在 dictionary 里出现的待分类的新文档的词
			while (ts.incrementToken()) {
				if (termAtt.length() > 0) {
					String word = ts.getAttribute(CharTermAttribute.class).toString();
					Integer wordId = dictionary.get(word);

					if (wordId != null) {
						words.add(word);
						wordCount++;
					}
				}
			}
			// 计算 TF-IDF，并构造 Vector
			Vector vector = new RandomAccessSparseVector(10000);
			TFIDF tfidf = new TFIDF();
			for (Multiset.Entry<String> entry : words.entrySet()) {
				String word = entry.getElement();
				int count = entry.getCount();
				Integer wordId = dictionary.get(word);
				Long freq = dc.get(wordId);
				double tfIdfValue = tfidf.calculate(count, freq.intValue(),wordCount, documentCount);
				vector.setQuick(wordId, tfIdfValue);
			}
			return vector;
          }
          
          /**
           * 读取训练时生成的dictionary
           * @param conf
           * @param dictionnaryPath
           * @return dictionnary
           */
          public static Map<String, Integer> readDictionnary(Configuration conf, Path dictionnaryPath) {
              Map<String, Integer> dictionnary = new HashMap<String, Integer>();
              for (Pair<Text, IntWritable> pair : new SequenceFileIterable<Text, IntWritable>(dictionnaryPath, true, conf)) {
                  dictionnary.put(pair.getFirst().toString(), pair.getSecond().get());
              }
              return dictionnary;
          }
          
        /**
         * 读取训练时生成的frequency
         * @param conf
         * @param documentFrequencyPath
         * @return documentFrequency
         */
          public static Map<Integer, Long> readDocumentCount(Configuration conf, Path documentFrequencyPath) {
              Map<Integer, Long> documentFrequency = new HashMap<Integer, Long>();
              for (Pair<IntWritable, LongWritable> pair : new SequenceFileIterable<IntWritable, LongWritable>(documentFrequencyPath, true, conf)) {
                  documentFrequency.put(pair.getFirst().get(), pair.getSecond().get());
              }
              return documentFrequency;
          }

    }  
} 
