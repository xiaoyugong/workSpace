package parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.crunch.CrunchRuntimeException;  
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.mahout.common.HadoopUtil;

public class Parser {
	private Parser(){}
	@SuppressWarnings("deprecation")
	public static boolean Parser(String input,String output,String jobtracker,AKVRegex regex)throws IOException{
		boolean flag=true;
		Configuration conf =new Configuration();
		conf.set("mapreduce.jobtracker.address",jobtracker);
		
		FileSystem fsIn=FileSystem.get(URI.create(input),conf);
		FileSystem fsOut=FileSystem.get(URI.create(output),conf);
		HadoopUtil.delete(conf, new Path(output));
		Path pathIn=new Path(input);
		Path pathOut=new Path(output);
		SequenceFile.Reader reader =null;
		FSDataOutputStream out=fsOut.create(pathOut);
	
		try{
			reader =new SequenceFile.Reader(fsIn, pathIn,conf);
			Writable key=(Writable)ReflectionUtils.newInstance(reader.getKeyClass(), conf);
			Writable value=(Writable)ReflectionUtils.newInstance(reader.getValueClass(), conf);
			 @SuppressWarnings("unchecked")  
		        Class<Writable> writableClassK=(Class<Writable>) reader.getKeyClass();  
		          @SuppressWarnings("unchecked")  
		        Class<Writable> writableClassV=(Class<Writable>) reader.getValueClass();  
			while(reader.next(key,value)){
//				String k=regex.keyRegex(key);
//				System.out.println(k);
//				String v=regex.valueRegex(value);
//				System.out.println(v);
//				if(" ".equals(v)||v==null){
//					continue;
//				}
//				out.writeBytes(k+"\t"+v+"\n");
				Writable k=deepCopy(key, writableClassK); // Writable 的深度复制
	            Writable v=deepCopy(value,writableClassV);
	            System.out.println(k+" "+v); 
	            out.writeBytes(k.toString()+"\t"+v.toString()+"\n");
			}
		}catch(IOException e){
			flag=false;
		}finally{
			IOUtils.closeStream(reader);
			out.close();
		}
		return flag;
	}
	
	/** 
     * Writable 的深度复制 
     * 引自WritableDeepCopier 
     * @param fPath 
     * @return 
     * @throws IOException 
     */  
    public static Writable deepCopy(Writable source,Class<Writable> writableClass) {  
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();  
            DataOutputStream dataOut = new DataOutputStream(byteOutStream);  
            Writable copiedValue = null;  
           try {  
             source.write(dataOut);  
             dataOut.flush();  
              ByteArrayInputStream byteInStream = new ByteArrayInputStream(byteOutStream.toByteArray());  
             DataInput dataInput = new DataInputStream(byteInStream);  
             copiedValue = writableClass.newInstance();  
              copiedValue.readFields(dataInput);  
            } catch (Exception e) {  
             throw new CrunchRuntimeException("Error while deep copying " + source, e);  
            }  
            return copiedValue;  
          }  
}
