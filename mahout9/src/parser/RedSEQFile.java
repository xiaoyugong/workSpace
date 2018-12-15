package parser;

import java.util.ArrayList;  
import java.util.Iterator;
import java.util.List;  
  


import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.Path;  
import org.apache.hadoop.io.Writable;  
import org.apache.mahout.common.StringTuple;  
import org.apache.mahout.common.iterator.sequencefile.PathFilters;  
import org.apache.mahout.common.iterator.sequencefile.PathType;  
import org.apache.mahout.common.iterator.sequencefile.SequenceFileDirValueIterable;  
import org.apache.mahout.math.VectorWritable;
  
public class RedSEQFile {  
  
    /** 
     * @param args 
     */  
    private static Configuration conf;  
      
    public static void main(String[] args) {  
        conf=new Configuration();  
        conf.set("mapreduce.jobtracker.address", "172.18.200.135:8020");  
        String path="hdfs://172.18.200.135:8020/outOFgxy/outputRF/forest.seq";  
          
       List<VectorWritable> l= getValue(path,conf);  
       for(Iterator<VectorWritable> i = l.iterator(); i.hasNext();)  
           System.out.println(i.next().toString());  
    }  
      
     /** 
     * 把序列文件读入到一个变量中； 
     * @param path 序列文件 
     * @param conf  Configuration 
     * @return  序列文件读取的变量 
     */  
    public static List<VectorWritable> getValue(String path,Configuration conf){  
        Path hdfsPath=new Path(path);  
        List<VectorWritable> list = new ArrayList<VectorWritable>();  
        for (Writable value : new SequenceFileDirValueIterable<Writable>(hdfsPath, PathType.LIST,  
                PathFilters.partFilter(), conf)) {  
              Class<? extends Writable> valueClass = value.getClass();  
              if (valueClass.equals(StringTuple.class)) {  
                  VectorWritable st = (VectorWritable) value;  
                  System.out.println(st+"000");
                  list.add(st);  
              } else {  
                throw new IllegalStateException("Bad value class: " + valueClass);  
              }  
            }  
        return list;  
    }  
  
}  
