package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.InputBuffer;
import org.mortbay.log.Log;

public class HandleClusterTextFile {
    /**
     * 处理 kmeans结果解析的text格式的数据，里面仅包含聚类名字和聚类内的点：
     * 用于R语言可视化kmeans聚类结果
     * @throws IOException
     */
    private static void handleClusterTextFile(String inFile) throws IOException {
    	Configuration conf = new Configuration();
    	FileSystem fs = FileSystem.get(URI.create(inFile), conf);
    	
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        String outFile = null;
        String s = null;
        int i = 0;
        
        try {
        	InputStream is = fs.open(new Path(inFile));
        	Reader reader = new InputStreamReader(is);
            br = new BufferedReader(reader); 
            
            while ((s = br.readLine()) != null) {
                if(s.contains("L-")){
                	i++;
                	outFile = "/outputKmeans/cluster" + i + ".csv";
                	Log.info("write cluster" + i + ".csv");
                	continue;
                }
                else if(s.contains("Weight")){
                	continue;
                }
                else{
                	fw = new FileWriter(new File(outFile), true);
                	bw = new BufferedWriter(fw);
                	
                	int beginIndex = s.lastIndexOf("[");
                	int endIndex = s.lastIndexOf("]");
                	String result = s.substring(beginIndex+1,endIndex);
                    bw.write(result);
                    bw.write("\n");
                    
                    if (bw != null) bw.close();
                }
            }
        } catch (Exception e) {
            if (br != null) br.close();
        } finally{
        	br.close();
        	bw.close();
        	fs.close();
        }
    }

    public static void main(String[] args) throws IOException {
        String inFile = "hdfs://172.18.200.135:8020/outOFgxy/outputKmeans/clusters-final";
        handleClusterTextFile(inFile);
    }
}