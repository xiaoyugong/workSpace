/**
 * @Class: HdfsUtil.java
 * @Description:
 * @Author: Zhaoliheng Jun 3, 2015
 * @Version: V1.0
 */

package com.goldtel.dmp.hadoop;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.datamining.distributeditembased.DistributedItemBasedImpl;
import com.goldtel.dmp.datamining.frequentpattern.FrequentPatternImpl;
import com.goldtel.dmp.datamining.kmeans.KMeansImpl;
import com.goldtel.dmp.interfaceentities.ClusterEntityQuery;
import com.goldtel.dmp.interfaceentities.DIBEntityQuery;
import com.goldtel.dmp.interfaceentities.FPEntityQuery;

public class HdfsUtil
{
    private static final Logger log = LoggerFactory.getLogger(HdfsUtil.class);

    /**
     * 读取Hdfs文件到Hbase
     * @param strFile
     * @throws IOException
     */
    public void readHdfsFiletoHbase(String strFile) throws IOException
    {
        FileSystem fs = null;
        try
        {
            fs = HConnector.getHDFSConnector();
        }
        catch (IOException e)
        {
            log.error("readHdfsFiletoHbase: getHDFSConnector failed. " + e.getLocalizedMessage());
            throw e;
        }

        log.info("readHdfsFiletoHbase: read " + strFile);

        Path path = new Path(strFile);
        if (fs.exists(path))
        {
            FSDataInputStream fStream = fs.open(path);
            String strLine = fStream.readLine();
            while (null != strLine)
            {
                strLine = fStream.readLine();
            }
            System.out.print(strLine);
        }
        else
        {
            log.warn("readHdfsFiletoHbase: file not exists. " + strFile);
        }
    }

    /**
     * 查询协同分布算法结果，返回List<Key  [item:value,...]>
     * @param strFile
     * @param query         查询条件
     * @param recommand
     * @throws IOException
     */
    public void readHdfsFileDIB(String strFile, DIBEntityQuery query, List<String> recommand) throws IOException
    {
        FileSystem fs = null;
        try
        {
            fs = HConnector.getHDFSConnector();
        }
        catch (IOException e)
        {
            log.error("readHdfsFileDIB: getHDFSConnector failed. " + e.getLocalizedMessage());
            throw e;
        }

        log.info("readHdfsFileDIB: read " + strFile);

        Path path = new Path(strFile);
        if (fs.exists(path))
        {
            FSDataInputStream fStream = fs.open(path);
            String strLine = null;
            int count = 0;  //  Line number

            while ((strLine = fStream.readLine()) != null)
            {
                //System.out.println(strLine);
                //  是否符合开始结束行
                if (chkStartStop(query, count))
                {
                    String strKey = DistributedItemBasedImpl.readKey(strLine);
                    if (null != strKey)
                    {
                        if (chkContainChar(query.getUserFeature(), strKey))
                        {
                            //Map<String, String> mValue = DistributedItemBasedImpl.readValue(strLine, query.getNumRecommendItems());
                            //recommand.put(strKey, mValue);
                            String sValue = DistributedItemBasedImpl.readValue(strLine, query.getNumRecommendItems());
                            StringBuffer sb = new StringBuffer();
                            sb.append(strKey);
                            sb.append(Const.RESULT_KV_SPLIT);
                            sb.append(Const.SPLIT_ANALYZE_L);
                            sb.append(sValue);
                            sb.append(Const.SPLIT_ANALYZE_R);
                            recommand.add(sb.toString());
                        }
                    }
                    else
                    {
                        log.error("readHdfsFileDIB: key is null. " + strLine);
                    }
                }

                count++;    //  Next line number
            }
        }
        else
        {
            log.warn("readHdfsFileDIB: file not exists. " + strFile);
        }
    }

    /**
     * 检查是否符合开始结束行
     * @param query
     * @return
     */
    private boolean chkStartStop(DIBEntityQuery query, int iLine)
    {
        boolean bRes = false;
        int iStart = query.getUserStart();
        int iStop  = query.getUserStop();

        if (-1 >= iStop)
        {
            if (iStart <= iLine)
            {
                bRes = true;
            }
        }
        else
        {
            if ((iStart <= iLine) && (iStop >= iLine))
            {
                bRes = true;
            }
        }
        
        return bRes;
    }

    /**
     * 检查是否包含指定字符串
     * @param query
     * @param str
     * @return
     */
    private boolean chkContainChar(String query, String str)
    {
        boolean bRes = false;
        if (null == query)
        {
            bRes = true;
        }
        else
        {
            if (str.indexOf(query) >= 0)   //  区分大小写
            {
                bRes = true;
            }
        }

        return bRes;
    }

    /**
     * 查询FP算法结果，返回List<key  ([item],value), ...>
     * @param strFile
     * @param query
     * @param recommand
     * @throws IOException
     */
    public void readHdfsFileFP(String strFile, FPEntityQuery query, List<String> recommand) throws IOException
    {
        FileSystem fs = null;
        try
        {
            fs = HConnector.getHDFSConnector();
        }
        catch (IOException e)
        {
            log.error("readHdfsFileFP: getHDFSConnector failed. " + e.getLocalizedMessage());
            throw e;
        }

        log.info("readHdfsFileFP: read " + strFile);

        Path path = new Path(strFile);
        if (fs.exists(path))
        {
            FSDataInputStream fStream = fs.open(path);
            String strLine = null;

            while ((strLine = fStream.readLine()) != null)
            {
                //System.out.println(strLine);
                if (!chkMaxNumber(query.getNumRecorders(), recommand.size()))
                {
                    String strKey = FrequentPatternImpl.readKey(strLine);
                    if (null != strKey)
                    {
                        if (chkContainChar(query.getFeature(), strKey))
                        {
                            String sValue = FrequentPatternImpl.readValue(strLine, query.getNumRelation(), query.getType());
                            StringBuffer sb = new StringBuffer();
                            sb.append(strKey);
                            sb.append(Const.RESULT_KV_SPLIT);
                            sb.append(sValue);
                            recommand.add(sb.toString());
                        }
                    }
                    else
                    {
                        log.error("readHdfsFileFP: key is null. " + strLine);
                    }
                }
            }
        }
    }

    /**
     * 检查最大条数是否已经满足
     * @param iThreshold
     * @param iNum
     * @return
     */
    private boolean chkMaxNumber(int iThreshold, int iNum)
    {
        boolean bRes = false;

        if (-1 < iThreshold)
        {
            if (iThreshold <= iNum)
            {
                bRes = true;
            }
        }

        return bRes;
    }

    /**
     * 上传本地文件到Hdfs
     * @param dstPath
     * @param srcPath
     * @throws Exception
     */
    public static void uploadFileToHdfs(String dstPath, String srcPath) throws Exception
    {
        FileSystem fs = null;
        try
        {
            fs = HConnector.getHDFSConnector();
        }
        catch (IOException e)
        {
            log.error("uploadFileToHdfs: getHDFSConnector failed. " + e.getLocalizedMessage());
            throw e;
        }
        log.info("uploadFileToHdfs: read " + srcPath + " to " + dstPath);
        
        Path dPath = new Path(dstPath);
        Path sPath = new Path(srcPath);
        if (fs.exists(dPath))
        {
            String strErr = "uploadFileToHdfs: file " + dstPath + "has existed, can't upload.";
            log.error(strErr);
            throw new Exception(strErr);
        }
        else
        {
            try
            {
                fs.copyFromLocalFile(sPath, dPath);
            }
            catch (IOException e)
            {
                log.error("uploadFileToHdfs: copyFromLocalFile failed. " + e.getLocalizedMessage());
                throw e;
            }
        }
    }
    
    /**
     * 查询聚类算法结果，返回格式参看ClusterEntityResponse
     * @param strFile
     * @param query
     * @param recommand
     * @throws IOException
     */
    public void readHdfsFileCluster(String strFile, ClusterEntityQuery query, List<String> recommand) throws IOException
    {
        FileSystem fs = null;
        try
        {
            fs = HConnector.getHDFSConnector();
        }
        catch (IOException e)
        {
            log.error("readHdfsFileCluster: getHDFSConnector failed. " + e.getLocalizedMessage());
            throw e;
        }

        log.info("readHdfsFileCluster: read " + strFile);

        Path path = new Path(strFile);
        if (fs.exists(path))
        {
            FSDataInputStream fStream = fs.open(path);
            String strLine = null;
            KMeansImpl km = new KMeansImpl(null);

            while ((strLine = fStream.readLine()) != null)
            {
                //System.out.println(strLine);
                String[] line = null;
                if (query.getBIsOnlyCenterPoints())
                {
                    try
                    {
                        line = KMeansImpl.readCenterPoints(strLine, query.getCenterName(), query.getBHasPara());
                    }
                    catch (Throwable et)
                    {
                        log.error("readHdfsFileCluster: readCenterPoints error. " + strLine + ", error: " + et.getLocalizedMessage());
                    }
                }
                else
                {
                    try
                    {
                        line = km.readRecorders(strLine, query.getCenterName(), query.getBHasPara());
                    }
                    catch (Throwable et)
                    {
                        log.error("readHdfsFileCluster: readRecorders error. " + strLine + ", error: " + et.getLocalizedMessage());
                    }
                }

                if (null != line)   //  符合条件的解析结果加到recommand里面
                {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < line.length; i++)
                    {
                        sb.append(line[i]);
                        sb.append(Const.SPLIT_DM_COND);
                    }
                    if (sb.length() >= Const.SPLIT_DM_COND.length())
                    {
                        sb.deleteCharAt(sb.length() - Const.SPLIT_DM_COND.length());
                    }
                    recommand.add(sb.toString().trim());
                }
            }
        }
    }
}
