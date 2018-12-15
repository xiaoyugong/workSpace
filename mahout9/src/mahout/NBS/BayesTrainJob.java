package mahout.NBS;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.classifier.naivebayes.BayesUtils;
import org.apache.mahout.classifier.naivebayes.NaiveBayesModel;
import org.apache.mahout.classifier.naivebayes.training.ThetaMapper;
import org.apache.mahout.classifier.naivebayes.training.WeightsMapper;
import org.apache.mahout.common.AbstractJob;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.commandline.DefaultOptionCreator;
import org.apache.mahout.common.iterator.sequencefile.PathFilters;
import org.apache.mahout.common.iterator.sequencefile.PathType;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.common.mapreduce.VectorSumReducer;
import org.apache.mahout.math.VectorWritable;

public class BayesTrainJob extends AbstractJob {
	private static final String TEXT2VECTOR_OUT = "text2vector";
	public static final String SUMMED_OBSERVATIONS = "summedObservations";
	public static final String WEIGHTS = "weights";
	public static final String SCV = ",";
	public static final String SCL = ":";
	private static final String ALPHA_I = "alphaI";

	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new BayesTrainJob(), args);
	}

	public int run(String[] args) throws Exception {
		addInputOption();
		addOutputOption();
		// 增加向量之间的分隔符，默认为逗号；
		addOption("splitCharacterVector", "scv",
				"Vector split character,default is ','", ",");
		// 增加向量和标示的分隔符，默认为冒号；
		addOption("splitCharacterLabel", "scl",
				"Vector and Label split character,default is ':'", ":");
		addOption(ALPHA_I, "a", "smoothing parameter", String.valueOf(1.0f));
		addOption(DefaultOptionCreator.overwriteOption().create());
		if (parseArguments(args) == null) {
			return -1;
		}

		Path input = getInputPath();
		Path output = getOutputPath();
		String scv = getOption("splitCharacterVector");
		String scl = getOption("splitCharacterLabel");
		float alphaI = Float.parseFloat(getOption(ALPHA_I));
		Configuration conf = getConf();
		HadoopUtil.delete(conf, output);
		conf.set("SCV", scv);
		conf.set("SCL", scl);
		HadoopUtil.setSerializations(conf);
		HadoopUtil.delete(conf, getOutputPath());
	    HadoopUtil.delete(conf, getTempPath());
	    
	 // 写indexlabel
	    String labelPath = "hdfs://172.18.200.135/outOFgxy/outputNBS/indexlabel";		
		long labelNumber = writeIndexLabel(labelPath);
		HadoopUtil.cacheFiles(new Path(labelPath), getConf());
		
		// 第一个JOB，数据格式转换成vector
		Path text2VectorOut = new Path(getOutputPath().toString()+"/"+TEXT2VECTOR_OUT);
		HadoopUtil.delete(conf, text2VectorOut);
		Job Text2Vector = prepareJob(input, 
				text2VectorOut,
				TextInputFormat.class, 
				TFMapper.class, 
				Text.class,
				VectorWritable.class, 
				SequenceFileOutputFormat.class);
		Text2Vector.setJar("/home/dashengong/remoteJAR/nbs.jar");
		boolean succeeded = Text2Vector.waitForCompletion(true);
		if (!succeeded) {
			return -1;
		}

		// 第二个JOB
		Job indexInstances = prepareJob(text2VectorOut,
				getTempPath(SUMMED_OBSERVATIONS),
				SequenceFileInputFormat.class, 
				IndexInstancesMap.class,
				IntWritable.class, 
				VectorWritable.class,
				VectorSumReducer.class, 
				IntWritable.class,
				VectorWritable.class, 
				SequenceFileOutputFormat.class);
		indexInstances.setCombinerClass(VectorSumReducer.class);
		indexInstances.setJar("/home/dashengong/remoteJAR/nbs.jar");
		succeeded = indexInstances.waitForCompletion(true);
		if (!succeeded) {
			return -1;
		}

		// 第三个JOB
		Job weightSummer = prepareJob(getTempPath(SUMMED_OBSERVATIONS),
				 getTempPath(WEIGHTS), 
				 SequenceFileInputFormat.class,
				WeightsMapper.class, 
				Text.class, 
				VectorWritable.class,
				VectorSumReducer.class, 
				Text.class, 
				VectorWritable.class,
				SequenceFileOutputFormat.class);
		weightSummer.getConfiguration().set(WeightsMapper.class.getName() + ".numLabels", String.valueOf(labelNumber));
		weightSummer.setCombinerClass(VectorSumReducer.class);
		weightSummer.setJar("/home/dashengong/remoteJAR/nbs.jar");
		succeeded = weightSummer.waitForCompletion(true);
		if (!succeeded) {
			return -1;
		}
	    HadoopUtil.cacheFiles(getTempPath(WEIGHTS), getConf());
	    
		// 将模型写入文件
		getConf().setFloat(ThetaMapper.ALPHA_I, alphaI);
		NaiveBayesModel naiveBayesModel = BayesUtils.readModelFromDir(getTempPath(), getConf());
		naiveBayesModel.validate();
		naiveBayesModel.serialize(getOutputPath(), getConf());

		return 0;
	}
	
	// 从输入文件中读出全部标识，并加以转换,然后写入文件
	private long writeIndexLabel( String labelPath) throws IOException {
		long labelSize = 0;
		Path out = new Path(labelPath);
		labelSize = WriteIndexLabel.writeLabel(getConf(), out,getInputPath());
		return labelSize;
	}
}
