package com.goldtel.dmp.datamining.bayes;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.classifier.naivebayes.test.TestNaiveBayesDriver;
import org.apache.mahout.classifier.naivebayes.training.TrainNaiveBayesJob;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.text.SequenceFilesFromDirectory;
import org.apache.mahout.utils.SplitInput;
import org.apache.mahout.vectorizer.SparseVectorsFromSequenceFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.Utils;

public class BayesImpl
{
    private static final Logger log = LoggerFactory.getLogger(BayesImpl.class);

	protected String strInPath = Const.HDFS_MYCLU + Const.INPATH + Const.SPLIT_PATH;// + "/BayesImpl/";
	protected String strOutPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_BAYESIAN + Const.SPLIT_PATH;
	protected String strOutPathMCR = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_BAYESIAN + Const.SPLIT_PATH;
	protected String mstrKey = null;
	protected String[] mstrPara3 = null;

	//	初始化数据来源
	public BayesImpl(String strKey, String strPara3)
	{
		strInPath += strKey;
		strOutPath = strOutPath + strKey + Const.SPLIT_PATH + strKey;
		strOutPathMCR = strOutPathMCR + strKey + Const.SPLIT_PATH;
		mstrKey = strKey;
		if (null != strPara3 && strPara3.length() > 0)
		{
		    mstrPara3 = strPara3.split(Const.SPLIT_ANALYZE_S);
		}
	}

	public String run() throws Exception
	{
		String tmpPath = Utils.getTempPath(this.getClass().getSimpleName());
		Configuration conf=new Configuration();
		Map<String, String> paraeq = new HashMap<String, String>();
		paraeq.put("-i", strInPath);				//	输入文件
		paraeq.put("-o", strOutPath + "-seq");	//	输出的序列文件
		paraeq.put("-ow", "");
		paraeq.put("-xm", "sequential");			//	Map/Reduce or Sequential
		//paraeq.put("-xm", "mapreduce");          //  Map/Reduce or Sequential
		paraeq.put("--tempDir",tmpPath + "/s1");
		String[] argseq = Utils.createDescriptionStr(paraeq);
		ToolRunner.run(conf, new SequenceFilesFromDirectory(), argseq);

		Map<String, String> parasv = new HashMap<String, String>();
		parasv.put("-i", strOutPath + "-seq");		//	输入的序列文件
		parasv.put("-o", strOutPath + "-vectors");	//	输出的向量文件
		parasv.put("-lnorm", "");						//	输出文件是否归一化
		parasv.put("-nv", "");							//	输出向量为NamedVector格式
		parasv.put("-ow", "");
		parasv.put("-nr", "12");        //  Reduce number, New adding
		parasv.put("-wt", "tfidf");						//	权重类型为tfidf
		String[] argssv = Utils.createDescriptionStr(parasv);
		ToolRunner.run(conf, new SparseVectorsFromSequenceFiles(), argssv);
		
		Map<String, String> parasp = new HashMap<String, String>();
		parasp.put("-i", strOutPath + "-vectors/tfidf-vectors");	//	输入的向量文件
		parasp.put("-tr", strOutPath + "-train-vectors");			//	训练文物的输出文件
		parasp.put("-te", strOutPath + "-test-vectors");			//	测试任务的输出文件
		//parasp.put("-rp", "40");									//	使用MR模式时，抽取出来作为测试数据的百分比
		parasp.put("-ow", "");
		parasp.put("--sequenceFiles", "");							//	序列文件？
		//parasp.put("-xm", "sequential");							//	算法执行模式分布式还是单机
		parasp.put("-mro", strOutPath + "-split-maps");             //  Output files for map produce
		parasp.put("--tempDir",tmpPath + "/s3");
		
		if (null != mstrPara3 && mstrPara3.length > 0)
		{
		    for (int i = 0; i < mstrPara3.length; i++)
		    {
		        String[] temp = mstrPara3[i].split(Const.SPLIT_DM_COND);
		        if (null != temp && temp.length == 2)
		        {
		            parasp.put(temp[0].trim(), temp[1].trim());
		        }
		        else
		        {
		            log.error("Step3 has illegal parameters. " + mstrPara3[i]);
		        }
		    }
		}
		
		String[] argssp = Utils.createDescriptionStr(parasp);
		ToolRunner.run(conf, new SplitInput(), argssp);

		Map<String, String> paratr = new HashMap<String, String>();
		//paratr.put("-i", strOutPath + "-train-vectors");			//	输入的训练文件
		paratr.put("-i", strOutPath + "-split-maps");          //  输入的训练文件 in m/r styles
		paratr.put("-el", "");										//	从输入文件中获得标识
		paratr.put("-o", strOutPathMCR + "model");					//	输出的模型文件
		paratr.put("-li", strOutPathMCR + "labelindex");			//	标识索引的存储路径
		paratr.put("-ow", "");
		paratr.put("--tempDir",tmpPath + "/s5");
		String[] argstr = Utils.createDescriptionStr(paratr);
		ToolRunner.run(conf, new TrainNaiveBayesJob(), argstr);

		Map<String, String> parats = new HashMap<String, String>();
		//parats.put("-i", strOutPath + "-test-vectors");			//	输入的测试文件
		parats.put("-i", strOutPath + "-split-maps");           //  输入的测试文件 in m/r styles
		parats.put("-m", strOutPathMCR + "model");					//	训练模型
		parats.put("-l", strOutPathMCR + "labelindex");				//	标识索引的存储路径
		parats.put("-ow", "");
		parats.put("-o", strOutPath + "-testing");				//	输出文件
		parats.put("--tempDir",tmpPath + "/s6");
		String[] argsts = Utils.createDescriptionStr(parats);
		ToolRunner.run(conf, new TestNaiveBayesDriver(), argsts);

		HadoopUtil.delete(conf, new Path(tmpPath));

		return "<file>" + "朴素贝叶斯算法对数据源[" + mstrKey + "]的分类类操作已完成。" + "</file>";
	}
}