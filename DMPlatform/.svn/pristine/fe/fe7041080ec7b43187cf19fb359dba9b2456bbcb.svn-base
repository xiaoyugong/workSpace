package com.chuandge.dmp.classifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.classifier.df.DFUtils;
import org.apache.mahout.classifier.df.DecisionForest;
import org.apache.mahout.classifier.df.data.DataConverter;
import org.apache.mahout.classifier.df.data.Dataset;
import org.apache.mahout.classifier.df.data.Instance;
import org.apache.mahout.classifier.df.mapreduce.Classifier;
import org.apache.mahout.common.AbstractJob;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.RandomUtils;
import org.apache.mahout.common.commandline.DefaultOptionCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/** 
 * 随机森林分类的Job 
 * @author gxy 2015-7-13
 */  
public class RFClassifyJob extends AbstractJob {
    
    private static final Logger log = LoggerFactory.getLogger(Classifier.class);

    public static void main(String[] args) throws Exception {
        ToolRunner.run(new Configuration(), new RFClassifyJob(), args);
    }

    /**
     * 获取参数，提交JOB
     */
    public int run(String[] args) throws Exception {
        addInputOption();
        addOutputOption();
        addOption("dataset", "ds", "Dataset path,describe file");
        addOption("model", "m", "model path,forest file");
        addOption(DefaultOptionCreator.overwriteOption().create());
        //addOption("analyze", "a", "analyze model,defalult false,new data not use",false);
        if (parseArguments(args) == null) {
            return -1;
        }

        Path input = getInputPath();
        Path output = getOutputPath();
        Path datasetPath = new Path(getOption("dataset"));
        Path modelPath = new Path(getOption("model"));
        //String analyze = getOption("analyze");
        if (hasOption(DefaultOptionCreator.OVERWRITE_OPTION))
        {
            HadoopUtil.delete(getConf(), output);
        }

        Configuration conf = getConf();
        
        log.info("Adding the dataset to the DistributedCache");
        // put the dataset into the DistributedCache
        DistributedCache.addCacheFile(datasetPath.toUri(), conf);
        log.info("Adding the decision forest to the DistributedCache");
        DistributedCache.addCacheFile(modelPath.toUri(), conf);
        //delete output
        HadoopUtil.delete(conf, output);
        
        Job job = prepareJob(input, 
                output,
                CTextInputFormat.class, 
                RFMapper.class, 
                Text.class, 
                Text.class,
                //TextOutputFormat.class);
                SequenceFileOutputFormat.class);
        ((JobConf) job.getConfiguration()).setJar(ClassifyConst.LIBPATH + "/DMPClassifierJob.jar");
        
        log.info("Running the job...");
        boolean succeeded = job.waitForCompletion(true);
        if (!succeeded) {
            return -1;
        }

        //  评估模型用的，分类新数据时不需要
        /*if(hasOption("analyze")){
            //解析结果，返回数组
            double[][] results = parseOutput(classifyJob);
            
            //评估模型
            if (results != null) {
                Dataset dataset = Dataset.load(getConf(), datasetPath);
                if (dataset.isNumerical(dataset.getLabelId())) {
                    RegressionResultAnalyzer regressionAnalyzer = new RegressionResultAnalyzer();
                    regressionAnalyzer.setInstances(results);
                    log.info("{}", regressionAnalyzer);
                } else {
                    ResultAnalyzer analyzer = new ResultAnalyzer(Arrays.asList(dataset.labels()), "unknown");
                    for (double[] res : results) {
                        analyzer.addInstance(String.valueOf((int)res[0]),new ClassifierResult(String.valueOf((int)res[1]), 1.0));
                    }
                    log.info("{}", analyzer);
                }
            }
        }*/
        
        return 0;
    }

    /**
     * 继承TextInputFormat
     */
    public static class CTextInputFormat extends TextInputFormat {
        @Override
        protected boolean isSplitable(JobContext jobContext, Path path) {
            return false;
        }
    }
    
    /**
     * Mapper类
     *分类的mapreduce
     *
     */
    public static class RFMapper extends Mapper<LongWritable, Text, Text, Text> {

        /** used to convert input values to data instances */
        private DataConverter converter;
        private DecisionForest forest;
        private final Random rng = RandomUtils.getRandom();
        private final Text lvalue = new Text();
        private Dataset dataset;
        private final Text lkey = new Text();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
          super.setup(context);    //To change body of overridden methods use File | Settings | File Templates.

          Configuration conf = context.getConfiguration();
          Path[] files = HadoopUtil.getCachedFiles(conf);

          if (files.length < 2) {
            throw new IOException("not enough paths in the DistributedCache");
          }
          dataset = Dataset.load(conf, files[0]);
          converter = new DataConverter(dataset);

          forest = DecisionForest.load(conf, files[1]);
          if (forest == null) {
            throw new InterruptedException("DecisionForest not found!");
          }
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
          String line = value.toString();
          if (!line.isEmpty()) {
            Instance instance = converter.convert(line);
            double prediction = forest.classify(dataset, rng, instance);
            lkey.set("DATA："+line);
            lvalue.set("     Prediction :"+dataset.getLabelString(prediction));
            context.write(lkey, lvalue);
          }
        }
      }
    
     /**
      * 解析结果，返回评估所需的数组
      * @param job
      * @return
      * @throws IOException
      */
    private double[][] parseOutput(JobContext job) throws IOException {
        Configuration conf = job.getConfiguration();
        FileSystem fs = getOutputPath().getFileSystem(conf);
        double[][] results;
        Path[] outfiles = DFUtils.listOutputFiles(fs, getOutputPath());
        BufferedReader buffR = null;
        String line;
        
        // read all the output
        List<double[]> resList = Lists.newArrayList();
        for (Path path : outfiles) {
//          FSDataOutputStream ofile = null;
//          try {
                FileSystem fsIn=FileSystem.get(path.toUri(),conf);
                FSDataInputStream in=fsIn.open(path);
                buffR = new BufferedReader(new InputStreamReader(in,"UTF-8"));
                while ((line = buffR.readLine()) != null){
                    int split1 = line.lastIndexOf(",");             
                    int split2 = line.lastIndexOf(":");     
                    String key = line.substring(split1 + 1,split1 + 2);
                    String value = line.substring(split2 + 1);
                    //将（原始分类 预测分类）写入文件
//                  if (ofile == null) {
//                      // this is the first value, it contains the name of the
//                      // input file
//                      ofile = fs.create(new Path(outputPath, value).suffix(".out"));
//                  } else {
//                      // The key contains the correct label of the data. The
//                      // value contains a prediction
//                      ofile.writeChars(value); // write the prediction
//                      ofile.writeChar('\n');

                        resList.add(new double[] {Double.valueOf(key), Double.valueOf(value) });
//                  }
                }
//          } finally {
//              Closeables.close(ofile, false);
//          }
        }
        results = new double[resList.size()][2];
        resList.toArray(results);
        return results;
    }
}