/**
 * @Class: BayesClassifyJob.java
 * @Description:
 * @Author: Zhaoliheng Jun 4, 2015
 * @Version: V1.0
 */

package com.chuandge.dmp.classifier;

import java.io.File;
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
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
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
import org.apache.mahout.common.commandline.DefaultOptionCreator;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.vectorizer.TFIDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;

public class BayesClassifyJob extends AbstractJob
{
    private static final Logger log = LoggerFactory.getLogger(BayesClassifyJob.class);
    private static String mModelDs = null;

    public BayesClassifyJob(String model)
    {
        mModelDs = model;
    }

    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception
    {
        ToolRunner.run(new Configuration(), new BayesClassifyJob("20news-all"),args);  
    }

    public int run(String[] args) throws Exception
    {
        /*//System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
        String strTmpPath = "/opt/workspace/DMP/build/classes";//System.getProperty("user.dir") + Const.SPLIT_PATH + mModelDs + "-bin";
        //Utils.createNewPath(strTmpPath);
        File jarFile = EJob.createTempJar(strTmpPath);
        EJob.addClasspath("/opt/workspace/DMP/WebContent/WEB-INF/lib");
        ClassLoader classLoader = EJob.getClassLoader();
        Thread.currentThread().setContextClassLoader(classLoader);*/

        addInputOption();  
        addOutputOption();
        addOption("model","m", "The file where bayesian model store ");  
        addOption("labelIndex","l", "The labels labelIndex ");
        addOption(DefaultOptionCreator.overwriteOption().create());

        if (parseArguments(args) == null) {
              return -1;  
        }
        Path input = getInputPath();  
        Path output = getOutputPath();  
        String labelIndex=getOption("labelIndex");
        String modelPath=getOption("model");  
        Configuration conf=getConf();
        conf.set("labelIndex", labelIndex);
        conf.set(ClassifyConst.NAME_MODEL_ID, mModelDs);
        HadoopUtil.cacheFiles(new Path(modelPath), conf);  
        if (hasOption(DefaultOptionCreator.OVERWRITE_OPTION))
        {
            HadoopUtil.delete(getConf(), output);
        }

        Job job = new Job(conf, mModelDs + " classify");

        /*job.setJarByClass(BayesClassifyJob.class);
        job.setJar("BayesClassifyJob.jar");
        job.setInputFormatClass(SequenceFileInputFormat.class);
        job.setMapperClass(BayesClassifyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(VectorWritable.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        //job.setOutputKeyClass(Text.class);
        //job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);*/

        job = prepareJob(getInputPath(), 
                getOutputPath(), 
                SequenceFileInputFormat.class,
                BayesClassifyMapper.class,
                Text.class, 
                VectorWritable.class, 
                SequenceFileOutputFormat.class);

        //((JobConf) job.getConfiguration()).setJar(jarFile.toString().trim());
        ((JobConf) job.getConfiguration()).setJar(ClassifyConst.LIBPATH + "/DMPClassifierJob.jar");

        log.info("Job " + mModelDs + " beginging.");

        if (job.waitForCompletion(true))
        {
            System.out.println("Job " + mModelDs + " accomplished.");
            log.info("Job " + mModelDs + " accomplished.");
            return 0;
        }
        log.info("Job " + mModelDs + " failed.");
        return -1;  
    }  
    /** 
     *  Mapper 类
     *  
     */  
    public static class BayesClassifyMapper extends Mapper<Text, Text, Text, VectorWritable>
    {
        private AbstractNaiveBayesClassifier classifier;
        private String labelIndex;
        private Map<Integer, String> labelMap;
        //private String dictionaryPath = "hdfs://172.18.200.135:8020/output/bayes/20news-all/20news-all-vectors/dictionary.file-0";
        //private String documentCountPath = "hdfs://172.18.200.135:8020/output/bayes/20news-all/20news-all-vectors/df-count/part-r-00000";

        @Override
        public void setup(Context context) throws IOException,InterruptedException
        {
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
          public void map(Text key, Text  value, Context context) throws IOException, InterruptedException
          {
            String mId = context.getConfiguration().get(ClassifyConst.NAME_MODEL_ID);
            Configuration conf = context.getConfiguration();
            String label = "";
            //获取tfidf-vector
            Vector vector = new RandomAccessSparseVector(10000);
            vector = createTfidfVector(value, mId);
            //分类
            Vector result = classifier.classifyFull(vector);  
            
           //构造输出 
            labelIndex=conf.get("labelIndex");  
            labelMap = BayesUtils.readLabelIndex(conf, new Path(labelIndex)); 
            int bestIdx = Integer.MIN_VALUE;  
            double bestScore = Long.MIN_VALUE;
            for (Vector.Element element : result.all())
            {  
                if (element.get() > bestScore)
                {  
                    bestScore = element.get();  
                    bestIdx = element.index();  
                }  
            }  
            if (bestIdx != Integer.MIN_VALUE)
            {  
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
          public Vector createTfidfVector(Text value, String mid) throws IOException
          {
            //训练分类器时生成的训练集数据，用于对新数据分类时构造tfidf-vector
            String dictionaryPath = ClassifyConst.HDFS_MYCLU + ClassifyConst.OUTPATH + ClassifyConst.SPLIT_PATH + ClassifyConst.DMNAME_BAYESIAN +
                    ClassifyConst.SPLIT_PATH + mid + ClassifyConst.SPLIT_PATH + mid + "-vectors" + ClassifyConst.SPLIT_PATH + "dictionary.file-0";
            String documentCountPath = ClassifyConst.HDFS_MYCLU + ClassifyConst.OUTPATH + ClassifyConst.SPLIT_PATH + ClassifyConst.DMNAME_BAYESIAN +
                    ClassifyConst.SPLIT_PATH + mid + ClassifyConst.SPLIT_PATH + mid + "-vectors" + ClassifyConst.SPLIT_PATH + "df-count/part-r-00000";
              
            Configuration conf = new Configuration();
            // 读取 dictionary、df-count
            Map<String, Integer> dictionary = readDictionnary(conf, new Path(dictionaryPath));
            Map<Integer, Long> dc = readDocumentCount(conf, new Path(documentCountPath));

            @SuppressWarnings("resource")
            //Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_43);
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
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
            while (ts.incrementToken())
            {
                if (termAtt.length() > 0)
                {
                    String word = ts.getAttribute(CharTermAttribute.class)
                            .toString();
                    Integer wordId = dictionary.get(word);

                    if (wordId != null)
                    {
                        words.add(word);
                        wordCount++;
                    }
                }
            }
            // 计算 TF-IDF，并构造 Vector
            Vector vector = new RandomAccessSparseVector(10000);
            TFIDF tfidf = new TFIDF();
            for (Multiset.Entry<String> entry : words.entrySet())
            {
                String word = entry.getElement();
                int count = entry.getCount();
                Integer wordId = dictionary.get(word);
                Long freq = dc.get(wordId);
                double tfIdfValue = tfidf.calculate(count, freq.intValue(),
                        wordCount, documentCount);
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
          public Map<String, Integer> readDictionnary(Configuration conf, Path dictionnaryPath)
          {
              Map<String, Integer> dictionnary = new HashMap<String, Integer>();
              for (Pair<Text, IntWritable> pair : new SequenceFileIterable<Text, IntWritable>(dictionnaryPath, true, conf))
              {
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
          public Map<Integer, Long> readDocumentCount(Configuration conf, Path documentFrequencyPath)
          {
              Map<Integer, Long> documentFrequency = new HashMap<Integer, Long>();
              for (Pair<IntWritable, LongWritable> pair : new SequenceFileIterable<IntWritable, LongWritable>(documentFrequencyPath, true, conf))
              {
                  documentFrequency.put(pair.getFirst().get(), pair.getSecond().get());
              }
              return documentFrequency;
          }
    }
} 