package mahout.NBS;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.classifier.ClassifierResult;
import org.apache.mahout.classifier.naivebayes.AbstractNaiveBayesClassifier;
import org.apache.mahout.classifier.naivebayes.BayesUtils;
import org.apache.mahout.classifier.naivebayes.NaiveBayesModel;
import org.apache.mahout.classifier.naivebayes.StandardNaiveBayesClassifier;
import org.apache.mahout.classifier.naivebayes.test.TestNaiveBayesDriver;
import org.apache.mahout.common.AbstractJob;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/** 
 * 用于分类的Job 
 * 
 */  
public class BayesClassifyJob2 extends AbstractJob {  
	private static final Logger log = LoggerFactory.getLogger(TestNaiveBayesDriver.class);
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
        ToolRunner.run(new Configuration(), new BayesClassifyJob2(),args);  
    }  
      
    public int run(String[] args) throws Exception {  
        addInputOption();  
        addOutputOption();  
        addOption("model","m", "The file where bayesian model store ");  
        addOption("labelindex","l", "The labelindex path ");  
        addOption(buildOption("testComplementary", "c", "test complementary?", false, false, String.valueOf(false)));
        if (parseArguments(args) == null) {  
              return -1;  
        }  
        Path input = getInputPath();  
        Path output = getOutputPath();
        String modelPath = getOption("model");  
        String labelindex = getOption("labelindex");
        boolean complementary = hasOption("testComplementary");
        Configuration conf=getConf();
        conf.set("labelindex", labelindex);
        HadoopUtil.cacheFiles(new Path(modelPath), conf);  
        HadoopUtil.delete(conf, output);  
        
        Job job=new Job(conf);  
        job.setJobName("Use bayesian model to classify the  input:"+input.getName());  
        job.setJarByClass(BayesClassifyJob2.class);   
        job.setJar("/home/dashengong/remoteJAR/BayesClassifyJob2.jar");
        
        job.setInputFormatClass(TextInputFormat.class);  
        job.setOutputFormatClass(TextOutputFormat.class);  
          
        job.setMapperClass(BayesClassifyMapper.class);  
        job.setMapOutputKeyClass(Text.class);  
        job.setMapOutputValueClass(VectorWritable.class);  
        job.setNumReduceTasks(0);  
        job.setOutputKeyClass(Text.class);  
        job.setOutputValueClass(VectorWritable.class);  
        SequenceFileInputFormat.setInputPaths(job, input);  
        SequenceFileOutputFormat.setOutputPath(job, output);  
          
        if(job.waitForCompletion(true)){  
        	//评估模型
//        	Map<Integer, String> labelMap = BayesUtils.readLabelIndex(getConf(), new Path(labelindex)); 
//            //loop over the results and create the confusion matrix
//            SequenceFileDirIterable<Text, VectorWritable> dirIterable =
//                new SequenceFileDirIterable<Text, VectorWritable>(getOutputPath(),
//                                                                  PathType.LIST,
//                                                                  PathFilters.partFilter(),
//                                                                  getConf());
//            ResultAnalyzer analyzer = new ResultAnalyzer(labelMap.values(), "DEFAULT");
//            analyzeResults(labelMap, dirIterable, analyzer);
//            log.info("{} Results: {}", complementary ? "Complementary" : "Standard NB", analyzer);
            return 0;  
        }  
        return -1;  
    }  
    /** 
     *  自定义Mapper
     * 
     */  
    public static class BayesClassifyMapper extends Mapper<LongWritable, Text, Text, VectorWritable>{  
        private AbstractNaiveBayesClassifier classifier;  
            @Override  
          public void setup(Context context) throws IOException, InterruptedException {  
            System.out.println("Setup");  
            Configuration conf = context.getConfiguration();  
            Path modelPath = HadoopUtil.getSingleCachedFile(conf);  
            NaiveBayesModel model = NaiveBayesModel.materialize(modelPath, conf);  
            classifier = new StandardNaiveBayesClassifier(model);  
          }  
  
          //分类原始数据
//          @Override  
//          public void map(Text key, VectorWritable value, Context context) throws IOException, InterruptedException {  
//            Vector result = classifier.classifyFull(value.get());  
//            String label = "";
//            String labelIndex=context.getConfiguration().get("labelindex");  
//            Map<Integer, String> labelMap = BayesUtils.readLabelIndex(context.getConfiguration(), new Path(labelIndex)); 
//            int bestIdx = Integer.MIN_VALUE;  
//            double bestScore = Long.MIN_VALUE;  
//            for (Vector.Element element : result.all()) {  
//                if (element.get() > bestScore) {  
//                    bestScore = element.get();  
//                    bestIdx = element.index();  
//                }  
//            }  
//            if (bestIdx != Integer.MIN_VALUE) {  
//                ClassifierResult classifierResult = new ClassifierResult(labelMap.get(bestIdx), bestScore);  
//                label = classifierResult.getLabel();  
//            }  
//            //文件名：分类   分类属性   
//            context.write(new Text(key.toString()), new VectorWritable(result));  
//          } 
            
            
		// 分类新数据
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			// 读取文本构造vector
			String values = value.toString();
			if ("".equals(values)) {
				context.getCounter("Records", "Bad Record").increment(1);
				return;
			}
			String[] line = values.split(",");
			Vector original = transformToVector(line);
			// 分类
			Vector result = classifier.classifyFull(original);
			String label = "";
			String labelIndex = context.getConfiguration().get("labelindex");
			Map<Integer, String> labelMap = BayesUtils.readLabelIndex(context.getConfiguration(), new Path(labelIndex));
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
			// 文件名：分类 分类属性
			context.write(new Text(label),new VectorWritable(result));
		}

		/**
		 * 构造vector
		 * 
		 * @param line
		 * @return
		 */
		private Vector transformToVector(String[] line) {
			Vector v = new RandomAccessSparseVector(line.length);
			for (int i = 0; i < line.length; i++) {
				double item = 0;
				try {
					item = Double.parseDouble(line[i]);
				} catch (Exception e) {
					return null; // 如果不可以转换，说明输入数据有问题
				}
				v.setQuick(i, item);
			}
			return v;
		}  
          
    }
    
    // 评估模型
// 	private static void analyzeResults(Map<Integer, String> labelMap,
// 			SequenceFileDirIterable<Text, VectorWritable> dirIterable,
// 			ResultAnalyzer analyzer) {
// 		for (Pair<Text, VectorWritable> pair : dirIterable) {
// 			int bestIdx = Integer.MIN_VALUE;
// 			double bestScore = Long.MIN_VALUE;
// 			for (Vector.Element element : pair.getSecond().get().all()) {
// 				if (element.get() > bestScore) {
// 					bestScore = element.get();
// 					bestIdx = element.index();
// 				}
// 			}
// 			if (bestIdx != Integer.MIN_VALUE) {
// 				ClassifierResult classifierResult = new ClassifierResult(labelMap.get(bestIdx), bestScore);
// 				analyzer.addInstance(pair.getFirst().toString(),classifierResult);
// 			}
// 		}
// 	}
}
    
