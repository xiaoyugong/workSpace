/**
 * @Class: ClusterEntityResponse.java
 * @Description:
 * @Author: Zhaoliheng Jun 10, 2015
 * @Version: V1.0
 */

package com.goldtel.dmp.classify;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuandge.dmp.classifier.RFClassifyJob;
import com.goldtel.dmp.Const;
import com.goldtel.dmp.Utils;

@XmlRootElement(name = "file")
@XmlAccessorType(XmlAccessType.FIELD)
public class RFClassify
{
    private static final Logger log = LoggerFactory.getLogger(RFClassify.class);

    /**
     * 注入查询结果
     * @param query
     * @throws IOException
     */
    public String putResult(String model, String ds) throws Exception
    {
        String strInPath = Const.HDFS_MYCLU + Const.INPATH + Const.SPLIT_PATH + ds;
        String strOutPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_RANDOMFORESTS + Const.SPLIT_PATH + ds + Const.SPLIT_PATH + ds;
        String strModelPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_RANDOMFORESTS + Const.SPLIT_PATH + 
                model + Const.SPLIT_PATH + model + "-builder";
        String strLabelIndexPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_RANDOMFORESTS + Const.SPLIT_PATH + 
                model + Const.SPLIT_PATH + model + ".info";

        Configuration conf = new Configuration();

        //  Classify new data
        Map<String, String> para = new HashMap<String, String>();
        para.put("-i", strInPath);                //  输入文件
        para.put("-o", strOutPath + "-classify");  //  输出的序列文件
        para.put("-m", strModelPath);         //  模型文件
        para.put("-ds", strLabelIndexPath);    //  labelindex文件
        para.put("-ow", "");
        String[] args = Utils.createDescriptionStr(para);
        int i = ToolRunner.run(conf, new RFClassifyJob(), args);
        if (-1 == i)
        {
            String strErr = "putResult(ds = " + ds + ", model = " + model + ") error.";
            log.error(strErr);
            throw new Exception(strErr);
        }

        return "<file>" + ds + Const.RESULT_CLASSIFY_OK + "</file>";
    }
} 
