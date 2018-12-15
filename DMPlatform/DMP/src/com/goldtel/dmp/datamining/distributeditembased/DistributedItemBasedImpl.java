package com.goldtel.dmp.datamining.distributeditembased;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.cf.taste.hadoop.item.RecommenderJob;
import org.apache.mahout.common.HadoopUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.Utils;
import com.goldtel.dmp.hadoop.HdfsUtil;
import com.goldtel.dmp.interfaceentities.DMEntityCondition;

public class DistributedItemBasedImpl
{
    private static final Logger log = LoggerFactory.getLogger(DistributedItemBasedImpl.class);

    protected String strInPath = Const.HDFS_MYCLU + /*Const.HDFS +*/ Const.INPATH + Const.SPLIT_PATH;   // + Const.DMNAME_DISTRIBUTEDITEMBASED + Const.SPLIT_PATH;
	protected String strOutPath = Const.HDFS_MYCLU + /*Const.HDFS +*/ Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_DISTRIBUTEDITEMBASED + Const.SPLIT_PATH;
	protected String mstrKey = null;
	protected Map<String, String> paraDm = null;    //  算法参数

	//	初始化数据来源
	public DistributedItemBasedImpl(DMEntityCondition cond)
	{
		if (null != cond)
		{
		    strInPath += cond.getDataSet();
	        strOutPath += cond.getDataSet();
	        mstrKey = cond.getDataSet();

	        paraDm = Utils.putMap(cond.getDmPara(), Const.SPLIT_DM_COND);
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String run() throws Exception
	{
		String tmpPath = Utils.getTempPath(this.getClass().getSimpleName());
		paraDm.put("-i",strInPath);
        paraDm.put("-o",strOutPath);
        paraDm.put("--tempDir",tmpPath);
        String[] arg = Utils.createDescriptionStr(paraDm);

		Configuration conf=new Configuration();
		HadoopUtil.delete(conf, new Path(strOutPath));
		RecommenderJob job=new RecommenderJob();
		ToolRunner.run(conf, job, arg);
		
		HadoopUtil.delete(conf, new Path(tmpPath));
		return "<file>" + "协同分布算法对数据源[" + mstrKey + "]的推荐分析操作已完成。" + "</file>";
	}

    /**
     * 解析推荐结果文件key值
     * @param strLine
     * @return
     */
    public static String readKey(String strLine)
    {
        String strKey = null;
        if (null != strLine)
        {
            strKey = strLine.substring(0, strLine.lastIndexOf(Const.SPLIT_ANALYZE_L)).trim();
        }

        return strKey;
    }

    /**
     * 将结果文件的一行中的推荐信息解析成推荐物品，推荐值的形式
     * @param strValue
     * @param num
     * @return
     */
    public static String readValue(String strLine, int num)
    {
        if (null == strLine)
        {
            log.info("readValue: strLine is null");
        }

        String strValue = strLine.substring(strLine.lastIndexOf(Const.SPLIT_ANALYZE_L) + 1, strLine.lastIndexOf(Const.SPLIT_ANALYZE_R)).trim();
        StringBuffer sb = new StringBuffer();

        if (null != strValue)
        {
            String[] arrValue = strValue.split(Const.SPLIT_ANALYZE_S);
            int iMax = arrValue.length;
            if ((num >= 0) && (iMax > num))
            {
                iMax = num;
            }

            for (int i = 0; i < iMax; i++)
            {
                sb.append(arrValue[i]);
                sb.append(Const.SPLIT_ANALYZE_S);
            }

            if (sb.length() > 0)
            {
                sb.deleteCharAt(sb.length() - Const.SPLIT_ANALYZE_S.length());
            }
        }

        return sb.toString();
    }
}