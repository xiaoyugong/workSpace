package mahout.NBS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.common.Pair;

public class WriteIndexLabel {

	// 把数字和标识的映射写入文件
	public static long writeLabel(Configuration conf, Path out, Path input) throws IOException {
		// TODO Auto-generated method stub
		FileSystem fs = FileSystem.get(out.toUri(), conf);
		FileSystem fsIn=FileSystem.get(input.toUri(),conf);
		FSDataInputStream in=fsIn.open(input);
		BufferedReader buffR = null;
		String line;
		@SuppressWarnings("deprecation")
		SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf, out, Text.class, IntWritable.class);
		Collection<String> seen = new HashSet<String>();
		int i = 0;
		try {
			buffR = new BufferedReader(new InputStreamReader(in,"UTF-8"));
				while ((line = buffR.readLine()) != null){
					String theLabel = line.split(BayesTrainJob.SCL)[1];
					if (!seen.contains(theLabel)) {
						writer.append(new Text(theLabel), new IntWritable(i++));
						seen.add(theLabel);
					}
				}	
		} finally {
			writer.close();
		}
		System.out.println("labels number is : " + i);
		return i;
	}
}
