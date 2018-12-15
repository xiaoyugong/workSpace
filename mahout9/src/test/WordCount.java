package test;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.common.HadoopUtil;

public class WordCount extends Configured implements Tool{

	public static class WordCountMap extends
			Mapper<LongWritable, Text, Text, IntWritable> {

		private final IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			StringTokenizer token = new StringTokenizer(line);
			while (token.hasMoreTokens()) {
				word.set(token.nextToken());
				context.write(word, one);
			}
		}
	}

	public static class WordCountReduce extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		
		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}


	public int run(String[] args) throws Exception {
//		File jarFile = EJob.createTempJar("F://Workspace/mahout9/bin");
//        EJob.addClasspath("F://Workspace/hadoopConf");	
//        ClassLoader classLoader = EJob.getClassLoader();
//        Thread.currentThread().setContextClassLoader(classLoader);
        
		HadoopUtil.delete(getConf(), new Path("/outOFgxy/WC"));
		
		Configuration conf = new Configuration();
		Job job = new Job(conf);
//		job.setJar(jarFile.toString());
		job.setJarByClass(WordCount.class);
		job.setJobName("wordcount");

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(WordCountMap.class);
		job.setReducerClass(WordCountReduce.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path("/test"));
		FileOutputFormat.setOutputPath(job, new Path("/outOFgxy/WC"));

		return(job.waitForCompletion(true)?0:-1);
		
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new WordCount(), args);
		System.exit(exitCode);
	}	
}

