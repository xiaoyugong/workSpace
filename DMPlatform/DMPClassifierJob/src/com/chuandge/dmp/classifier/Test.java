package com.chuandge.dmp.classifier;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

/** 
 * 随机森林分类的Job 
 * @author gxy 2015-7-13
 */  
public class Test
{
    public static void main(String[] args) throws Exception {
        ToolRunner.run(new Configuration(), new RFClassifyJob(), args);
    }
}