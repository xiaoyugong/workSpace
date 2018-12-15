/**
 * Hadoop, HBase链接器
 * @Class: HConnector.java
 * @Description:
 * @Author: Zhaoliheng Jun 3, 2015
 * @Version: V1.0
 */

package com.goldtel.dmp.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HConnector
{
    private static final Logger log = LoggerFactory.getLogger(HConnector.class);
    private static FileSystem hdfsSystem;   //  The unique HDFS Connector
 
    private HConnector()
    {
        //  Empty
    }

    /**
     * 获取HDFS句柄
     * @return
     * @throws IOException
     */
    public static synchronized FileSystem getHDFSConnector() throws IOException
    {
        if (null == hdfsSystem)
        {
            Configuration conf = new Configuration();
            try
            {
                hdfsSystem = FileSystem.get(conf);
            }
            catch (IOException e)
            {
                log.error("getHDFSConnector get hdfsSystem failed. " + e.getLocalizedMessage());
                throw e;
            }
        }

        log.info("getHDFSConnector get hdfsSystem ok.");
        return hdfsSystem;
    }

    /**
     * 关闭文件系统
     * @throws IOException
     */
    public static synchronized void closeHDFSConnector() throws IOException
    {
        if (null != hdfsSystem)
        {
            try
            {
                hdfsSystem.close();
            }
            catch (IOException e)
            {
                log.error("closeHDFSConnector get hdfsSystem failed. " + e.getLocalizedMessage());
                throw e;
            }
        }

        log.info("closeHDFSConnector close hdfsSystem ok.");
    }
}
