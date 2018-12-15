package mahout.NBS;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.classifier.naivebayes.BayesUtils;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.math.map.OpenObjectIntHashMap;

public class IndexInstancesMap extends Mapper <Text, VectorWritable, IntWritable, VectorWritable>{
	public enum Counter { SKIPPED_INSTANCES };
	private OpenObjectIntHashMap<String> labelIndex;
	
	public void setup(Context ctx) throws IOException, InterruptedException{
		super.setup(ctx);
		labelIndex=BayesUtils.readIndexFromCache(ctx.getConfiguration());
	}
	
	public void map(Text key,VectorWritable value,Context ctx) throws IOException, InterruptedException{
		String label = key.toString();
		if (labelIndex.containsKey(label)) {
		      ctx.write(new IntWritable(labelIndex.get(label)), value);
		    } else {
		      ctx.getCounter(Counter.SKIPPED_INSTANCES).increment(1);
		    }
	}
}
