/**
 * @Class: BayesClassify.java
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
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.Pair;
import org.apache.mahout.text.SequenceFilesFromDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuandge.dmp.classifier.BayesClassifyJob;
import com.goldtel.dmp.Const;
import com.goldtel.dmp.Utils;
import com.goldtel.dmp.interfaceentities.BaseInterfaceEntity;
import com.goldtel.dmp.parser.AKVRegex;
import com.goldtel.dmp.parser.Parser;

@XmlRootElement(name = "file")
@XmlAccessorType(XmlAccessType.FIELD)
public class BayesClassify extends BaseInterfaceEntity
{
    private static final long serialVersionUID = 67806300100008L;
    private static final Logger log = LoggerFactory.getLogger(BayesClassify.class);
    public static final String RESULT_FP = "result";

    /**
     * 注入查询结果
     * @param query
     * @throws IOException
     */
    public String putResult(String model, String ds) throws Exception
    {
        setDataSet(model);

        String strInPath = Const.HDFS_MYCLU + Const.INPATH + Const.SPLIT_PATH + ds;
        String strOutPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_BAYESIAN + Const.SPLIT_PATH + ds + Const.SPLIT_PATH + ds;
        String strModelPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_BAYESIAN + Const.SPLIT_PATH + 
                getDataSet() + Const.SPLIT_PATH + "model";
        String strLabelIndexPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_BAYESIAN + Const.SPLIT_PATH + 
                getDataSet() + Const.SPLIT_PATH + "labelindex";
        Configuration conf = new Configuration();

        String tmpPath = Utils.getTempPath(this.getClass().getSimpleName());
        Map<String, String> paraeq = new HashMap<String, String>();
        paraeq.put("-i", strInPath);                //  输入文件
        paraeq.put("-o", strOutPath + "-seq");  //  输出的序列文件
        paraeq.put("-ow", "");
        paraeq.put("-xm", "sequential");            //  Map/Reduce or Sequential
        paraeq.put("--tempDir",tmpPath + "/s1");
        String[] argseq = Utils.createDescriptionStr(paraeq);
        ToolRunner.run(conf, new SequenceFilesFromDirectory(), argseq);

        Map<String, String> para = new HashMap<String, String>();
        para.put("-i", strOutPath + "-seq");                //  输入文件
        para.put("-o", strOutPath + "-classify");  //  输出的序列文件
        para.put("-m", strModelPath);         //  模型文件
        para.put("-l", strLabelIndexPath);    //  labelindex文件
        para.put("-ow", "");
        //paraeq.put("--tempDir",tmpPath + "/s1");
        String[] args = Utils.createDescriptionStr(para);
        int i = ToolRunner.run(conf, new BayesClassifyJob(model), args);
        if (-1 == i)
        {
            String strErr = "putResult(ds = " + ds + ", model = " + model + ") error.";
            log.error(strErr);
            throw new Exception(strErr);
        }
        HadoopUtil.delete(conf, new Path(tmpPath));

        return "<file>" + ds + Const.RESULT_CLASSIFY_OK + "</file>";
    }

    /**
     * 读取训练时生成的dictionary
     * @param conf
     * @param dictionnaryPath
     * @return dictionnary
     */
    public static Map<String, Integer> readDictionnary(Configuration conf, Path dictionnaryPath) {
        Map<String, Integer> dictionnary = new HashMap<String, Integer>();
        for (Pair<Text, IntWritable> pair : new SequenceFileIterable<Text, IntWritable>(dictionnaryPath, true, conf)) {
            dictionnary.put(pair.getFirst().toString(), pair.getSecond().get());
        }
        return dictionnary;
    }

    /**
     * 读取训练时生成的frequency
     * @param conf
     * @param documentFrequencyPath
     * @return documentFrequency
     */
    public static Map<Integer, Long> readDocumentFrequency(Configuration conf, Path documentFrequencyPath) {
        Map<Integer, Long> documentFrequency = new HashMap<Integer, Long>();
        for (Pair<IntWritable, LongWritable> pair : new SequenceFileIterable<IntWritable, LongWritable>(documentFrequencyPath, true, conf)) {
            documentFrequency.put(pair.getFirst().get(), pair.getSecond().get());
        }
        return documentFrequency;
    }
    
    private boolean FPParser(String outPath, String ds) throws IOException
    {
        boolean flag = false;
        try
        {
            flag=Parser.Parser(outPath + "-classify/part-m-00000",
                    outPath + "-" + Const.RESULT_PATH + Const.SPLIT_PATH + RESULT_FP, Const.HDFS_IP, new AKVRegex());
            System.out.println(flag);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            log.error("FPParser: " + e.getLocalizedMessage());
            throw e;
        }

        return flag;
    }
} 
