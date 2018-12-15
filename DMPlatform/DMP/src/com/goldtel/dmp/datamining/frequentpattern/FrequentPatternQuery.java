package com.goldtel.dmp.datamining.frequentpattern;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.hadoop.HdfsUtil;
import com.goldtel.dmp.interfaceentities.FPEntityQuery;

public class FrequentPatternQuery
{
    private static final Logger log = LoggerFactory.getLogger(FrequentPatternQuery.class);

    /**
     * 注入查询结果
     * @param query
     * @throws IOException
     */
    public static List<String> putResult(FPEntityQuery query) throws IOException
    {
        List<String> recommand = new LinkedList<String>();
        String strInPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_FREQUENTpATTERN + Const.SPLIT_PATH + 
                        query.getDataSet() + Const.SPLIT_PATH + Const.RESULT_PATH + Const.SPLIT_PATH + FrequentPatternImpl.RESULT_FP;

        HdfsUtil hu = new HdfsUtil();
        hu.readHdfsFileFP(strInPath, query, recommand);

        return recommand;
    }
}