package com.goldtel.dmp.datamining.distributeditembased;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.hadoop.HdfsUtil;
import com.goldtel.dmp.interfaceentities.DIBEntityQuery;

public class DistributedItemBasedQuery
{
    private static final Logger log = LoggerFactory.getLogger(DistributedItemBasedQuery.class);

    /**
     * 注入查询结果
     * @param query
     * @throws IOException
     */
    public static List<String> putResult(DIBEntityQuery query) throws IOException
    {
        List<String> recommand = new LinkedList<String>();
        String strInPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_DISTRIBUTEDITEMBASED + Const.SPLIT_PATH + 
                           query.getDataSet() + Const.SPLIT_PATH + "part-r-00000";

        HdfsUtil hu = new HdfsUtil();
        hu.readHdfsFileDIB(strInPath, query, recommand);

        return recommand;
    }
}