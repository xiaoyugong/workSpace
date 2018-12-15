package mahout.RF;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.classifier.df.data.DescriptorException;
import org.apache.mahout.classifier.df.tools.Describe;
import org.apache.mahout.common.HadoopUtil;

public class Describes {

	public static void main(String[] args) throws  IOException, DescriptorException {
		// TODO Auto-generated method stub
		String arg[]=new String[]{"-p","hdfs://172.18.200.135:8020/input/rfdata2",  
                "-f","hdfs://172.18.200.135:8020/outOFgxy/newglass.info","-d","I","9","N"}; 
		 HadoopUtil.delete(new Configuration(), new Path(arg[Arrays.asList(arg).indexOf("-f")+1]));
		 Describe.main(arg);
	}
}
