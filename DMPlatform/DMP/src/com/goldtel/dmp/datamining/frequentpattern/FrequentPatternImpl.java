package com.goldtel.dmp.datamining.frequentpattern;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.fpm.pfpgrowth.FPGrowthDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.Utils;
import com.goldtel.dmp.interfaceentities.DMEntityCondition;
import com.goldtel.dmp.interfaceentities.FPEntityQuery;
import com.goldtel.dmp.parser.*;

public class FrequentPatternImpl
{
    private static final Logger log = LoggerFactory.getLogger(FrequentPatternImpl.class);

	protected String strInPath = Const.HDFS_MYCLU + Const.INPATH + Const.SPLIT_PATH;   // + Const.DMNAME_FREQUENTpATTERN + Const.SPLIT_PATH;
	protected String strOutPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_FREQUENTpATTERN + Const.SPLIT_PATH;
	protected String mstrKey = null;
	protected Map<String, String> paraDm = null;   // 算法参数

	public static final String RESULT_FP = "result";
	public static final String RESULT_FP_VALUE_SPLIT = "\\), ";   //  FP挖掘结果文件中值之间的分隔符
	public static final String RESULT_FP_VALUE_PATCH = ", ";   //  FP挖掘结果文件中值之间的分隔符

	//	初始化数据来源
	public FrequentPatternImpl(DMEntityCondition cond)
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
		
		try
		{
			//调用fp算法
			FPGrowthDriver.main(arg);
			FPParser(strOutPath);

			HadoopUtil.delete(new Configuration(), new Path(tmpPath));
					
		} catch (Exception e) {
			e.printStackTrace();
			log.error("run: " + e.getLocalizedMessage());
			throw e;
		}

		return "<file>" + "FP树关联算法对数据源[" + mstrKey + "]的推荐分析操作已完成。" + "</file>";
	}

	/**
	 * 用于解析结果，解析后的结果重新写入hdfs
	 * @return
	 */
	private boolean FPParser(String outPath) throws IOException
	{
		boolean flag = false;
		try
		{
			flag=Parser.Parser(outPath + "/frequentpatterns/part-r-00000",
					outPath + Const.SPLIT_PATH + Const.RESULT_PATH + Const.SPLIT_PATH + RESULT_FP, Const.HDFS_IP, new AKVRegex());
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
            strKey = strLine.substring(0, strLine.indexOf('(')).trim();
        }

        return strKey;
    }

    /**
     * 将结果文件的一行中的推荐信息解析成推荐物品，推荐值的形式
     * @param strValue
     * @param num
     * @return
     */
    public static String readValue(String strLine, int num, int iType)
    {
        if (null == strLine)
        {
            log.info("readValue: strLine is null");
        }

        String strValue = strLine.substring(strLine.indexOf(Const.RESULT_KV_SPLIT) + Const.RESULT_KV_SPLIT.length(),
                                            strLine.lastIndexOf(Const.SPLIT_ANALYZE_RS) + Const.SPLIT_ANALYZE_RS.length()).trim();
        StringBuffer sb = new StringBuffer();

        if (null != strValue)
        {
            String[] arrValue = strValue.split(RESULT_FP_VALUE_SPLIT);
            int iMax = arrValue.length;

            switch (iType)
            {
                case FPEntityQuery.ALL:
                {
                    for (int i = 0; i < iMax; i++)
                    {
                        String strTemp = arrValue[i].trim();
                        if (!strTemp.endsWith(Const.SPLIT_ANALYZE_RS))
                        {
                            strTemp += Const.SPLIT_ANALYZE_RS;
                        }
                        sb.append(strTemp);
                        sb.append(RESULT_FP_VALUE_PATCH);
                    }
                    break;
                }
                case FPEntityQuery.MAX:
                {
                    if (iMax > num)
                    {
                        iMax = num;
                    }

                    for (int i = 0; i < iMax; i++)
                    {
                        String strTemp = arrValue[i].trim();
                        if (!strTemp.endsWith(Const.SPLIT_ANALYZE_RS))
                        {
                            strTemp += Const.SPLIT_ANALYZE_RS;
                        }
                        sb.append(strTemp);
                        sb.append(RESULT_FP_VALUE_PATCH);
                    }
                    break;
                }
                case FPEntityQuery.MIN:
                {
                    if (iMax > num)
                    {
                        iMax = num;
                    }

                    for (int i = 0; i < iMax; i++)
                    {
                        String strTemp = arrValue[i].trim();
                        if (!strTemp.endsWith(Const.SPLIT_ANALYZE_RS))
                        {
                            strTemp += Const.SPLIT_ANALYZE_RS;
                        }
                        sb.append(strTemp);
                        sb.append(RESULT_FP_VALUE_PATCH);
                    }
                    break;
                }
                default:
                {
                    log.error("readValue: iType error. iType = " + iType);
                }
            }

            if (sb.length() >= RESULT_FP_VALUE_PATCH.length())
            {
                sb.deleteCharAt(sb.length() - RESULT_FP_VALUE_PATCH.length());
            }
        }

        return sb.toString().trim();
    }
}