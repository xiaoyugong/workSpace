package mahout.NBS;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.VectorWritable;

public class TFMapper extends Mapper<LongWritable, Text, Text, VectorWritable> {
	private String SCV;
	private String SCL;
    
    /** 
     * 初始化分隔符参数  
     */  
	@Override
	public void setup(Context ctx) {
		SCV = ctx.getConfiguration().get("SCV");
		SCL = ctx.getConfiguration().get("SCL");
	}
    
    /** 
     * 解析字符串，并输出 
     * @throws InterruptedException  
     * @throws IOException  
     */  
    @Override  
    public void map(LongWritable key,Text value,Context ctx) throws IOException, InterruptedException {
		String[] valueStr = value.toString().split(SCL);
		if (valueStr.length != 2) {
			return; // 没有两个说明解析错误,退出
		}
		String name = valueStr[1];
		String[] vector = valueStr[0].split(SCV);
		RandomAccessSparseVector v = new RandomAccessSparseVector(vector.length);
		for (int i = 0; i < vector.length; i++) {
			double item = 0;
			try {
				item = Double.parseDouble(vector[i]);
			} catch (Exception e) {
				return; // 如果不可以转换，说明输入数据有问题
			}
			v.setQuick(i, item);
		}
		NamedVector nv = new NamedVector(v, name);
		VectorWritable vw = new VectorWritable(nv);
		ctx.write(new Text(name), vw);
    }
}