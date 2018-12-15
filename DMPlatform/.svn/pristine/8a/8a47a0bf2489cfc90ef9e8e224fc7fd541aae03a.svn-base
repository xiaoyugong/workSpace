package com.goldtel.dmp.datamining.canopy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.conversion.InputDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.ClassUtils;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.commandline.DefaultOptionCreator;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.utils.clustering.ClusterDumper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.Utils;
import com.goldtel.dmp.clusterwriter.ReadClusterWritable;
import com.goldtel.dmp.hadoop.HdfsUtil;
import com.goldtel.dmp.interfaceentities.DMEntityCondition;
import com.goldtel.dmp.parser.AKVRegex;
import com.goldtel.dmp.parser.Parser;

public class CanopyImpl
{
	private static final Logger log = LoggerFactory.getLogger(CanopyImpl.class);
	private static final String DIRECTORY_CONTAINING_CONVERTED_INPUT = "data";

	protected String strInPath = Const.HDFS_MYCLU + Const.INPATH + Const.SPLIT_PATH;// + Const.DMNAME_CANNOPY + Const.SPLIT_PATH;
	protected String strOutPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_CANNOPY + Const.SPLIT_PATH;
	protected String strOutPathl = Const.LOCAL_OUTPATH + Const.SPLIT_PATH + Const.DMNAME_CANNOPY + Const.SPLIT_PATH;
	protected String mstrKey = null;
	protected Map<String, String> paraDm = new HashMap<String, String>();    //  算法参数

	//	初始化数据来源
	public CanopyImpl(DMEntityCondition cond)
	{
		if (null != cond)
		{
		    strInPath += cond.getDataSet();
	        strOutPath += cond.getDataSet();
	        strOutPathl += cond.getDataSet();
	        mstrKey = cond.getDataSet();

	        for (String str : cond.getDmPara())
	        {
	            String[] value = str.split(Const.SPLIT_DM_COND);
	            if (value.length == 2)
	            {
	                paraDm.put(value[0].trim(), value[1].trim());
	            }
	        }
		}
	}

	public String run() throws Exception
	{
		String tmpPath = Utils.getTempPath(this.getClass().getSimpleName());
		log.info("Preparing Input");
		Path input = new Path(strInPath);
		Path output = new Path(Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_CANNOPY + Const.SPLIT_PATH);
		Path pSer = new Path(output, mstrKey);
		Path directoryContainingConvertedInput = new Path(pSer + "_vw", DIRECTORY_CONTAINING_CONVERTED_INPUT);

		Configuration conf=new Configuration();
		HadoopUtil.delete(conf, directoryContainingConvertedInput);
		HadoopUtil.delete(conf, pSer);

		InputDriver.runJob(input, directoryContainingConvertedInput, "org.apache.mahout.math.RandomAccessSparseVector");

		paraDm.put("-i",directoryContainingConvertedInput.toString());
		paraDm.put("-o",strOutPath);
		paraDm.put("-ow", "");
		paraDm.put("-cl", "");
		paraDm.put("--tempDir",tmpPath);
		String[] arg = Utils.createDescriptionStr(paraDm);

		CanopyDriver job=new CanopyDriver();
		log.info("Running Canopy to get initial clusters");
		//	用Canopy算法确定初始簇的个数和簇的中心
		ToolRunner.run(conf, job, arg);

		HadoopUtil.delete(conf, new Path(tmpPath));

		// ClusterDumper类将聚类的结果解析到文件中
	    ReadClusterWritable.readClusterFile(conf,
	    		strOutPath + "/clusters-*-final",
	    		strOutPath + "/clusteredPoints",
				strOutPathl);
	    ReadClusterWritable.readClusterFileCenter(conf,
                strOutPath + "/clusters-*-final",
                strOutPath + "/clusteredPoints",
                strOutPathl);
	    
	    String dstPath = strOutPath + Const.SPLIT_PATH + Const.RESULT_PATH + Const.SPLIT_PATH;
        HdfsUtil.uploadFileToHdfs(dstPath + Const.RESULT_CLUSTER, strOutPathl + Const.SPLIT_PATH + Const.RESULT_CLUSTER);
        HdfsUtil.uploadFileToHdfs(dstPath + Const.RESULT_CLUSTER_CENTER, strOutPathl + Const.SPLIT_PATH + Const.RESULT_CLUSTER_CENTER);

		return "<file>" + "Canopy算法对数据源[" + mstrKey + "]的聚类操作已完成。" + "</file>";
	}
}