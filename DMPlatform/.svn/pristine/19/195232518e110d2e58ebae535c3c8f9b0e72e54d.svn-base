package com.goldtel.dmp.datamining.randomforests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.classifier.df.mapreduce.BuildForest;
import org.apache.mahout.classifier.df.mapreduce.TestForest;
import org.apache.mahout.classifier.df.tools.Describe;
import org.apache.mahout.common.HadoopUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.Utils;
import com.goldtel.dmp.interfaceentities.RFEntityCondition;

public class RandomForestsImpl
{
    private static final Logger log = LoggerFactory.getLogger(RandomForestsImpl.class);

	protected String strInPath = Const.HDFS_MYCLU + Const.INPATH + Const.SPLIT_PATH;// + "/RandomForestsImpl/";
	protected String strOutPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_RANDOMFORESTS + Const.SPLIT_PATH;
	protected String mstrKey = null;

	Map<String, String[]> para = new HashMap<String, String[]>();  //  描述文件参数
	Map<String, String> parab = new HashMap<String, String>();     //  随机森林模型参数

	//	初始化数据来源
	public RandomForestsImpl(RFEntityCondition cond) throws Exception
	{
		if (null != cond)
		{
		    strInPath += cond.getDataSet();
	        strOutPath = strOutPath + cond.getDataSet() + Const.SPLIT_PATH + cond.getDataSet();
	        mstrKey = cond.getDataSet();

	        String[] arrDesc = cond.getDesc();
	        if (null != arrDesc)
	        {
	            for (String str : arrDesc)
	            {
	                String[] value = str.split(Const.SPLIT_DM_COND);
	                if (value.length >= 2)
	                {
	                    String[] val = new String[value.length - 1];
	                    for (int i = 1; i < value.length; i++)
	                    {
	                        val[i - 1] = value[i].trim();
	                    }
	                    para.put(value[0].trim(), val);
	                }
	                else
                    {
                        log.error("RandomForestsImpl: inputted desc parameter error. " + str);
                        throw new Exception("随机森林算法输入desc参数错误。");
                    }
	            }
	        }
	        
	        String[] arrBuild = cond.getBuild();
	        if (null != arrBuild)
	        {
	            for (String str : arrBuild)
	            {
	                String[] value = str.split(Const.SPLIT_DM_COND);
	                if (value.length == 2)
	                {
	                    parab.put(value[0].trim(), value[1].trim());
	                }
	                else
	                {
	                    log.error("RandomForestsImpl: inputted build parameter error. " + str);
	                    throw new Exception("随机森林算法输入build参数错误。");
	                }
	            }
	        }
		}
	}

	public String run() throws Exception
	{
		Configuration conf = new Configuration();
		HadoopUtil.delete(conf, new Path(strOutPath));
		String buildPath = strOutPath + "-builder";
		HadoopUtil.delete(conf, new Path(buildPath));
		HadoopUtil.delete(conf, new Path(strOutPath));

		String[] inpath = {strInPath};
		para.put("-p", inpath);						//	输入路径
		String[] dsppath = {strOutPath + ".info"};
		para.put("-f", dsppath);					//	生成的描述文件路径和文件名
		//String[] val = {"I","9","N","L"};
		//para.put("-d", val);						//	输入数据描述:I-第一列(数据集ID)可忽略，9 N-后面9个属性列都是数值，L-最后一个属性是每条记录的分类结果
		String[] pa = Utils.createDescription(para);
		HadoopUtil.delete(conf, new Path(pa[Arrays.asList(pa).indexOf("-f")+1]));
		Describe.main(pa);

		parab.put("-d", strInPath);				//	输入路径
		parab.put("-ds", strOutPath + ".info");	//	描述文件路径和文件名
		//parab.put("-sl", "3");					//	建树过程中随机选取的属性个数
		//parab.put("-ms", "3");					//	树是否产生分支的阈值，如果小于这个值就产生叶子而不产生分支
		//parab.put("-p", "");
		//parab.put("-t", "5");					//	生成的诀策树个数
		parab.put("-o", buildPath);				//	存储随机森林的输出文件路径
		String[] arg2 = Utils.createDescriptionStr(parab);
		BuildForest.main(arg2);
		//ToolRunner.run(conf, new BuildForest(), arg2);

		Map<String, String> parae = new HashMap<String, String>();
		parae.put("-i", strInPath);
		parae.put("-ds", strOutPath + ".info");
		parae.put("-m", buildPath);				//	存储随机森林的文件路径
		parae.put("-mr", "");					//	MapReduce模式
		parae.put("-a", "");					//	是否显示模型评估信息
		parae.put("-o", strOutPath);
		String[] arg3 = Utils.createDescriptionStr(parae);
		TestForest.main(arg3);

		return "<file>" + "随机森林算法对数据源[" + mstrKey + "]的分类操作已完成。" + "</file>";
	}
}