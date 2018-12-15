package com.goldtel.dmp.datamining.kmeans;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.conversion.InputDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.HadoopUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.Utils;
import com.goldtel.dmp.clusterwriter.ReadClusterWritable;
import com.goldtel.dmp.datamining.canopy.CanopyImpl;
import com.goldtel.dmp.hadoop.HdfsUtil;
import com.goldtel.dmp.interfaceentities.DMEntityCondition;

public class KMeansImpl
{
	private static final Logger log = LoggerFactory.getLogger(CanopyImpl.class);
	private static final String DIRECTORY_CONTAINING_CONVERTED_INPUT = "data";

	protected String strInPath = Const.HDFS_MYCLU + Const.INPATH + Const.SPLIT_PATH;// + Const.DMNAME_KMEANS + Const.SPLIT_PATH;
	protected String strOutPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_KMEANS + Const.SPLIT_PATH;
	protected String strOutPathl = Const.LOCAL_OUTPATH + Const.SPLIT_PATH + Const.DMNAME_KMEANS + Const.SPLIT_PATH;
	protected String mstrKey = null;

    protected Map<String, String> paraDm = new HashMap<String, String>();    //  Canopy算法参数
    protected Map<String, String> paraDms = new HashMap<String, String>();    //  Kmeans算法参数
    
    protected String mCurCenterPoint = null;        //  当前聚类中心点名称，解析聚类结果时用到

	//	初始化数据来源
	public KMeansImpl(DMEntityCondition cond)
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
	        
	        for (String str2 : cond.getDmParas())
	        {
	            String[] value2 = str2.split(Const.SPLIT_DM_COND);
	            if (value2.length == 2)
	            {
	                paraDms.put(value2[0].trim(), value2[1].trim());
	            }
	        }
		}
	}

	public String run() throws Exception
	{
		{
			//	Preparing Input
			log.info("Preparing Input");
			Path input = new Path(strInPath);
			Path output = new Path(Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_KMEANS + Const.SPLIT_PATH);
			String tmpPath = Utils.getTempPath(this.getClass().getSimpleName());
			Path pSer = new Path(output, mstrKey);
			Path directoryContainingConvertedInput = new Path(pSer + "_vw", DIRECTORY_CONTAINING_CONVERTED_INPUT);
			Path outputc = new Path(Const.HDFS_MYCLU + Const.OUTPATH + "/CanopyKmeans/" + mstrKey);

			Configuration conf = new Configuration();
			HadoopUtil.delete(conf, directoryContainingConvertedInput);
			HadoopUtil.delete(conf, outputc);
			HadoopUtil.delete(conf, pSer);

			InputDriver.runJob(input, directoryContainingConvertedInput, "org.apache.mahout.math.RandomAccessSparseVector");

			paraDm.put("-i",directoryContainingConvertedInput.toString());   //  输入路径，序列文件
	        paraDm.put("-o",outputc.toString());                               //  输出路径
	        paraDm.put("-ow", "");                                             //  覆盖已存在的路径
	        paraDm.put("-cl", "");                                             //  对数据进行分类
	        paraDm.put("--tempDir",tmpPath);
	        String[] arg = Utils.createDescriptionStr(paraDm);

			CanopyDriver job=new CanopyDriver();
			log.info("Running Canopy to get initial clusters");
			//	用Canopy算法确定初始簇的个数和簇的中心
			ToolRunner.run(conf, job, arg);

			//	解析聚类结果，中间结果不计入文件
		    String tmpPathk = Utils.getTempPath(this.getClass().getSimpleName());

		    paraDms.put("-i", directoryContainingConvertedInput.toString());				//	输入路径，序列文件
		    paraDms.put("-c", outputc + "/" + Cluster.INITIAL_CLUSTERS_DIR +"-final");	//	初始聚类中心文件，上一步聚类的结果
		    paraDms.put("-o", strOutPath);												//	输出路径
		    paraDms.put("-ow", "");														//	覆盖已存在的路径
		    //parak.put("-cd", "0.5");													//	判断推出循环的阈值
		    //parak.put("-x", "10");														//	最大迭代次数
		    paraDms.put("-cl", "");														//	对数据进行分类
		    paraDms.put("--tempDir", tmpPathk);
		    String[] argk = Utils.createDescriptionStr(paraDms);

		    KMeansDriver kmjob = new KMeansDriver();
		    ToolRunner.run(conf, kmjob, argk);

		    HadoopUtil.delete(conf, new Path(tmpPath));
		    HadoopUtil.delete(conf, new Path(tmpPathk));

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
		}

		return "<file>" + "K-Means算法对数据源[" + mstrKey + "]的聚类操作已完成。" + "</file>";
	}

    /**
     * 读取行数据，格式解析为: 聚类中心名字,记录个数,聚类中心属性
     * @param strLine
	 * @param name
	 * @return
	 */
    public static String[] readCenterPoints(final String strLine, final String name, final boolean bHasPara) throws Throwable
    {
        if (null == strLine || strLine.length() == 0)
        {
            log.warn("readCenterPoints: read an empty line.");
            return null;
        }
        
        String[] center = null;
        if (bHasPara)
        {
            center = new String[4];
        }
        else
        {
            center = new String[2];
        }

        if (null != name && name.length() > 0)
        {
            String stname = strLine.substring(0, strLine.indexOf(Const.SPLIT_ANALYZE_LG));
            if (name.equalsIgnoreCase(stname))
            {
                center[0] = stname;
                center[1] = strLine.substring(strLine.indexOf("n=") + 2, strLine.indexOf("c=")).trim();
                if (bHasPara)
                {
                    center[2] = strLine.substring(strLine.indexOf("c=[") + 3, strLine.indexOf("] r=[")).trim();
                    center[3] = strLine.substring(strLine.indexOf("] r=[") + 5, strLine.indexOf("]}")).trim();
                }

                return center;
            }
            else
            {
                return null;
            }
        }
        else
        {
            center[0] = strLine.substring(0, strLine.indexOf(Const.SPLIT_ANALYZE_LG));
            center[1] = strLine.substring(strLine.indexOf("n=") + 2, strLine.indexOf("c=")).trim();
            if (bHasPara)
            {
                center[2] = strLine.substring(strLine.indexOf("c=[") + 3, strLine.indexOf("] r=[")).trim();
                center[3] = strLine.substring(strLine.indexOf("] r=[") + 5, strLine.indexOf("]}")).trim();
            }
            
            return center;
        }
    }
    
    /**
     * 读取行数据，格式解析为: 聚类中心名字,记录个数,聚类中心属性
     * @param strLine
     * @param name
     * @return
     */
    public String[] readRecorders(final String strLine, final String name, final boolean bHasPara)
    {
        String[] res = null;
        if (null == strLine || strLine.length() == 0)
        {
            log.warn("readRecorders: read an empty line.");
            return null;
        }

        if (null == name || name.length() == 0)
        {
            if (strLine.indexOf("distance=") >= 0)  //  只读取记录，不读取中心点
            {
                //  读取所有记录
                res = readValue(strLine, bHasPara);
            }
        }
        else
        {
            //  读取聚类中心名称与name的类下面的记录
            String strTmp = strLine.trim();
            if ((strTmp.indexOf("[props - optional]") < 0) &&(strTmp.indexOf("distance=") < 0))
            {
                mCurCenterPoint = strTmp.substring(0, strTmp.indexOf(Const.SPLIT_ANALYZE_LG));
            }
            else if ((strTmp.indexOf("distance=") >= 0) && (name.equalsIgnoreCase(mCurCenterPoint)))  //  读取记录
            {
                res = readValue(strLine, bHasPara);
            }
        }
        
        return res;
    }

    /**
     * 读取聚类结果记录属性
     * @param line
     * @return
     */
    private String[] readValue(final String strLine, final boolean bHasPara)
    {
        
        String dis = strLine.substring(strLine.indexOf("distance=") + 9, strLine.indexOf("]:")).trim();    //  distance
        String tmp = strLine.substring(strLine.indexOf("]:") + 2, strLine.length());
        String num = tmp.substring(0, tmp.indexOf("=")).trim();       //  number of properties
        tmp = tmp.substring(tmp.indexOf(Const.SPLIT_ANALYZE_L) + 1, tmp.indexOf(Const.SPLIT_ANALYZE_R)).trim();
        String[] pro = tmp.split(Const.SPLIT_ANALYZE_S);

        String[] res = null;
        if (bHasPara)   //  要返回属性
        {
            res = new String[pro.length + 2];
            res[0] = dis;
            res[1] = num;
            for (int i = 0; i < pro.length; i++)
            {
                if (pro[i].indexOf(":") >= 0)   //  有的结果属性前面会有属性序号，去掉序号
                {
                    res[i + 2] = pro[i].substring(pro[i].indexOf(":") + 1, pro[i].length()).trim();
                }
                else
                {
                    res[i + 2] = pro[i].trim();
                }
            }
        }
        else
        {
            res = new String[2];
            res[0] = dis;
            res[1] = num;
        }

        return res;
    }
}