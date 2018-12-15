/**
 * @Class: QueryResults.java
 * @Description:
 * @Author: Zhaoliheng Jun 4, 2015
 * @Version: V1.0
 */

package com.goldtel.dmp;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.clusterwriter.ReadSequenceWritable;
import com.goldtel.dmp.datamining.distributeditembased.DistributedItemBasedQuery;
import com.goldtel.dmp.datamining.frequentpattern.FrequentPatternQuery;
import com.goldtel.dmp.interfaceentities.BayesQueryResponse;
import com.goldtel.dmp.interfaceentities.ClusterEntityQuery;
import com.goldtel.dmp.interfaceentities.ClusterEntityResponse;
import com.goldtel.dmp.interfaceentities.DIBEntityQuery;
import com.goldtel.dmp.interfaceentities.DIBEntityResponse;
import com.goldtel.dmp.interfaceentities.FPEntityQuery;
import com.goldtel.dmp.interfaceentities.FPEntityResponse;
import com.goldtel.dmp.interfaceentities.RFQueryResponse;
import com.goldtel.dmp.parser.AKVRegex;
import com.goldtel.dmp.parser.Parser;

public class QueryResults
{
    private static final Logger log = LoggerFactory.getLogger(QueryResults.class);

    /**
     * Query DIM result
     * @param query
     * @return
     * @throws Exception
     */
    @GET
    @Path("/dib")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response queryDIB(DIBEntityQuery query) throws Exception
    {
        if (null == query)
        {
            log.error("queryDIB: Inputted parameter is null.");
            throw new Exception("Inputted parameter is null.");
        }
        log.info("queryDIB: dataSet = " + query.getDataSet() + ", numRecommendItems = " + query.getNumRecommendItems() + ", userFeature = " + query.getUserFeature() + 
                ", userStart = " + query.getUserStart() + ", userStop = " + query.getUserStop());

        DIBEntityResponse resp = new DIBEntityResponse();
        try
        {
            resp.setRecommand(DistributedItemBasedQuery.putResult(query));
        }
        catch (Exception e)
        {
            log.error("queryDIB: " + e.getLocalizedMessage());
            throw e;
        }

        return Response.ok(resp).build();
    }

    /**
     * Query DIM result
     * @param asyncResponse
     * @param ds            数据集
     * @param numRec        每条记录推荐的物品个数
     * @param userFeature   用户特征（包含指定名字的用户）
     * @param userStart     起始用户号（从0开始）
     * @param userStop      结束用户号（包含该下标的用户）
     * @throws Exception
     */
    @GET
    @Path("/dib/{ds}&{numRec}&{userFeature}&{userStart}&{userStop}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void queryDIB(@Suspended final AsyncResponse asyncResponse,
            @PathParam("ds") final String ds,
            @PathParam("numRec") final Integer numRec,
            @PathParam("userFeature") final String userFeature,
            @PathParam("userStart") final Integer userStart,
            @PathParam("userStop") final Integer userStop) throws Exception
    {
        log.info("queryDIB: dataSet = " + ds + ", numRec = " + numRec + ", userFeature = " + userFeature + ", userStart = " + userStart + ", userStop = " + userStop);

        asyncResponse.setTimeoutHandler(new TimeoutHandler()
        {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse)
            {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(Const.iTIMEOUT, TimeUnit.SECONDS);

        asyncResponse.register(new CompletionCallback()
        {
            @Override
            public void onComplete(Throwable throwable)
            {
                if (throwable == null)
                {
                    // no throwable - the processing ended successfully
                    // (response already written to the client)
                    System.out.println("queryDIB Accomplication");
                }
                else
                {
                    throwable.printStackTrace();
                }
            }
        });

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    DIBEntityQuery query = buildDIBEntityQuery(ds, numRec, userFeature, userStart, userStop);
                    DIBEntityResponse resp = new DIBEntityResponse();
                    resp.setRecommand(DistributedItemBasedQuery.putResult(query));
                    asyncResponse.resume(resp);
                }
                catch (Exception e)
                {
                    log.error("queryDIB: " + e.getLocalizedMessage());
                    asyncResponse.resume("queryDIB: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    private DIBEntityQuery buildDIBEntityQuery(String ds, int numRec, String userFeature, int userStart, int userStop) throws Exception
    {
        DIBEntityQuery query = new DIBEntityQuery();
        query.setDataSet(ds);
        try
        {
            query.setNumRecommendItems(numRec);
            if (!"NULL".equals(userFeature))
            {
                query.setUserFeature(userFeature);
            }

            query.setUserStart(userStart);
            query.setUserStop(userStop);
        }
        catch (Throwable e)
        {
            log.error("buildFPEntityQuery: " + e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        }

        return query;
    }

    /**
     * Query FP result
     * @return
     * @throws Exception
     */
    @GET
    @Path("/fp")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response queryFP(FPEntityQuery query) throws Exception
    {
        if (null == query)
        {
            log.error("queryFP: Inputted parameter is null.");
            throw new Exception("Inputted parameter is null.");
        }
        log.info("queryFP: dataSet = " + query.getDataSet() + ", numRelation = " + query.getNumRelation() + ", feature = " + query.getFeature() + 
                ", type = " + query.getType() + ", numRecorders = " + query.getNumRecorders());
        System.out.println("queryFP: dataSet = " + query.getDataSet() + ", numRelation = " + query.getNumRelation() + ", feature = " + query.getFeature() + 
                ", type = " + query.getType() + ", numRecorders = " + query.getNumRecorders());

        FPEntityResponse resp = new FPEntityResponse();
        try
        {
            resp.setRecommand(FrequentPatternQuery.putResult(query));
        }
        catch (Exception e)
        {
            log.error("queryFP: " + e.getLocalizedMessage());
            throw e;
        }

        return Response.ok(resp).build();
    }

    /**
     * Query FP result
     * @param asyncResponse
     * @param ds            数据集
     * @param numRelation   每条记录返回的关联组个数，-1表示全部
     * @param feature       记录特征（选取记录的Key的包含了指定字符串的记录），NULL表示参数为空
     * @param type          返回记录方式， ALL|MAX|MIN
     *                      ALL - 返回全部关联信息，此时numRelation不起作用
     *                      MAX - 从关联度最大的开始返回
     *                      MIN - 从关联度最小的开始返回
     * @param numRecorders  返回记录条数
     * @throws Exception
     */
    @GET
    @Path("/fp/{ds}&{numRelation}&{feature}&{type}&{numRecorders}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void queryFP(@Suspended final AsyncResponse asyncResponse,
            @PathParam("ds") final String ds,
            @PathParam("numRelation") final Integer numRelation,
            @PathParam("feature") final String feature,
            @PathParam("type") final String type,
            @PathParam("numRecorders") final Integer numRecorders) throws Exception
    {
        log.info("queryFP: dataSet = " + ds + ", numRelation = " + numRelation + ", feature = " + feature + ", type = " + type + ", numRecorders = " + numRecorders);

        asyncResponse.setTimeoutHandler(new TimeoutHandler()
        {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse)
            {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(Const.iTIMEOUT, TimeUnit.SECONDS);

        asyncResponse.register(new CompletionCallback()
        {
            @Override
            public void onComplete(Throwable throwable)
            {
                if (throwable == null)
                {
                    // no throwable - the processing ended successfully
                    // (response already written to the client)
                    System.out.println("queryFP Accomplication");
                }
                else
                {
                    throwable.printStackTrace();
                }
            }
        });

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    FPEntityQuery query = buildFPEntityQuery(ds, numRelation, feature, type, numRecorders);
                    FPEntityResponse resp = new FPEntityResponse();
                    resp.setRecommand(FrequentPatternQuery.putResult(query));
                    asyncResponse.resume(resp);
                }
                catch (Exception e)
                {
                    log.error("queryFP: " + e.getLocalizedMessage());
                    asyncResponse.resume("queryFP: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /**
     * 组装FP算法查询类
     * @param ds
     * @param numRelation
     * @param feature
     * @param type
     * @param numRecorders
     * @return
     * @throws Exception
     */
    private FPEntityQuery buildFPEntityQuery(String ds, int numRelation, String feature, String type, int numRecorders) throws Exception
    {
        FPEntityQuery query = new FPEntityQuery();
        query.setDataSet(ds);
        try
        {
            query.setNumRelation(numRelation);
            if (!"NULL".equals(feature))
            {
                query.setFeature(feature);
            }

            if ("ALL".equals(type))
            {
                query.setType(0);
            }
            else if ("MAX".equals(type))
            {
                query.setType(1);
            }
            else if ("MIN".equals(type))
            {
                query.setType(2);
            }
            else
            {
                throw new Exception("输入参数【返回记录方式】异常。");
            }
            query.setNumRecorders(numRecorders);
        }
        catch (Throwable e)
        {
            log.error("buildFPEntityQuery: " + e.getLocalizedMessage());
            throw new Exception(e.getLocalizedMessage());
        }

        return query;
    }

    /**
     * 查询Canopy聚类算法结果
     * @param asyncResponse
     * @param ds    查询数据集
     * @param paraM 查询参数，格式为 -op:true/false,-cn:聚类中心名称,-p:true/false
     *              -op表示是否只查询聚类中心点，默认为真;
     *              -cn为null时表示查询全部聚类中心;-op为false时，查询聚类中心全匹配设定字符串的类别下的所有记录,-op为true时，查询聚类中心全匹配设定字符串的聚类中心;
     *              -p表示是否返回记录属性，默认为false;
     * @throws Exception
     */
    @GET
    @Path("/canopy/{ds}&{param}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void queryCanopy(@Suspended final AsyncResponse asyncResponse,
            @PathParam("ds") final String ds,
            @PathParam("param") final String paraM) throws Exception
    {
        log.info("queryCanopy: dataSet = " + ds + ", param = " + paraM);

        asyncResponse.setTimeoutHandler(new TimeoutHandler()
        {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse)
            {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(Const.iTIMEOUT, TimeUnit.SECONDS);

        asyncResponse.register(new CompletionCallback()
        {
            @Override
            public void onComplete(Throwable throwable)
            {
                if (throwable == null)
                {
                    // no throwable - the processing ended successfully
                    // (response already written to the client)
                    System.out.println("queryCanopy Accomplication");
                }
                else
                {
                    throwable.printStackTrace();
                }
            }
        });

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    ClusterEntityQuery query = buildClusterEntityQuery(ds, paraM);
                    ClusterEntityResponse resp = queryCluster(query, Const.DMNAME_CANNOPY);
                    asyncResponse.resume(resp);
                }
                catch (Exception e)
                {
                    log.error("queryCanopy: " + e.getLocalizedMessage());
                    asyncResponse.resume("queryCanopy: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /**
     * 查询KMeans聚类算法结果  
     * @param asyncResponse
     * @param ds    查询数据集
     * @param paraM 查询参数，格式为 -op:true/false,-cn:聚类中心名称,-p:true/false
     *              -op表示是否只查询聚类中心点，默认为真;
     *              -cn为null时表示查询全部聚类中心;-op为false时，查询聚类中心全匹配设定字符串的类别下的所有记录,-op为true时，查询聚类中心全匹配设定字符串的聚类中心;
     *              -p表示是否返回记录属性，默认为false;
     * @throws Exception
     */
    @GET
    @Path("/kmeans/{ds}&{param}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void queryKMeans(@Suspended final AsyncResponse asyncResponse,
            @PathParam("ds") final String ds,
            @PathParam("param") final String paraM) throws Exception
    {
        log.info("queryKMeans: dataSet = " + ds + ", param = " + paraM);

        asyncResponse.setTimeoutHandler(new TimeoutHandler()
        {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse)
            {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(Const.iTIMEOUT, TimeUnit.SECONDS);

        asyncResponse.register(new CompletionCallback()
        {
            @Override
            public void onComplete(Throwable throwable)
            {
                if (throwable == null)
                {
                    // no throwable - the processing ended successfully
                    // (response already written to the client)
                    System.out.println("queryBayes Accomplication");
                }
                else
                {
                    throwable.printStackTrace();
                }
            }
        });

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    ClusterEntityQuery query = buildClusterEntityQuery(ds, paraM);
                    ClusterEntityResponse resp = queryCluster(query, Const.DMNAME_KMEANS);
                    asyncResponse.resume(resp);
                }
                catch (Exception e)
                {
                    log.error("queryKMeans: " + e.getLocalizedMessage());
                    asyncResponse.resume("queryKMeans: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /**
     * 查询聚类算法结果
     * @param query
     * @param calName 算法名称
     * @return
     * @throws Exception
     */
    private ClusterEntityResponse queryCluster(ClusterEntityQuery query, String calName) throws Exception
    {
        ClusterEntityResponse resp = new ClusterEntityResponse();
        try
        {
            resp.putResult(query, calName);
        }
        catch (Exception e)
        {
            log.error("queryCluster: " + e.getLocalizedMessage());
            throw e;
        }

        return resp;
    }

    /**
     * 组装聚类算法查询类
     * @param ds
     * @param paraM
     * @return
     * @throws Exception
     */
    private ClusterEntityQuery buildClusterEntityQuery(String ds, String paraM) throws Exception
    {
        ClusterEntityQuery qry = new ClusterEntityQuery();
        qry.setBIsOnlyCenterPoints(true);
        qry.setBHasPara(false);

        if (null == ds || ds.trim().length() == 0)
        {
            log.error("buildClusterEntityQuery: Inputted parameters have null. dataSet = " + ds + ", param = " + paraM);
            throw new Exception("Inputted parameters have null. dataSet = " + ds + ", param = " + paraM);
        }
        else
        {
            qry.setDataSet(ds.trim());
            if (null != paraM && paraM.trim().length() > 0)
            {
                String[] para = paraM.trim().split(Const.SPLIT_ANALYZE_S);
                for (int i = 0; i < para.length; i++)
                {
                    String[] t = para[i].trim().split(Const.SPLIT_DM_COND, 2);
                    if (t.length != 2)
                    {
                        log.error("buildClusterEntityQuery: Inputted parameters error. dataSet = " + ds + ", err param = " + para[i]);
                        throw new Exception("Inputted parameters error. " + para[i]);
                    }
                    else
                    {
                        try
                        {
                            if (ClusterEntityQuery.PARA_OP.equalsIgnoreCase(t[0].trim()))
                            {
                                qry.setBIsOnlyCenterPoints(Boolean.valueOf(t[1].trim()));
                            }
                            else if (ClusterEntityQuery.PARA_CN.equalsIgnoreCase(t[0].trim()))
                            {
                                qry.setCenterName(t[1].trim());
                            }
                            else if (ClusterEntityQuery.PARA_P.equalsIgnoreCase(t[0].trim()))
                            {
                                qry.setBHasPara(Boolean.valueOf(t[1].trim()));
                            }
                            else
                            {
                                throw new Exception("Error parameter: " + para[i]);
                            }
                        }
                        catch (Throwable e)
                        {
                            log.error("buildClusterEntityQuery: Error parameter. dataSet = " + ds + ", err param = " + para[i]);
                            throw new Exception("Error parameter: " + para[i]);
                        }
                    }
                }
            }
        }

        return qry;
    }

    /**
     * 查询Bayes新数据的分类结果
     * @param asyncResponse
     * @param ds    新分类的数据集
     * @param startn  待查询的数据起始行，如果该项值为0，表示从最开始查询
     * @param stopn  待查询的数据结束行，如果该项值为0，表示一直查到分类结果结束
     * @return
     * @throws Exception
     */
    @GET
    @Path("/bayes/{ds}&{nstart}&{nstop}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void queryBayes(@Suspended final AsyncResponse asyncResponse,
            @PathParam("ds") final String ds,
            @PathParam("nstart") final Long nStart,
            @PathParam("nstop") final Long nStop) throws Exception
    {
        log.info("queryBayes: dataSet = " + ds + ", nstart = " + nStart + ", nstop = " + nStop);
        System.out.println("queryBayes: dataSet = " + ds + ", nstart = " + nStart + ", nstop = " + nStop);

        if (null == ds || null == nStart || nStart < 0 || null == nStop || nStop < 0)
        {
            log.error("queryBayes: Inputted dataset has null.");
            throw new Exception("Inputted dataset has null.");
        }

        asyncResponse.setTimeoutHandler(new TimeoutHandler()
        {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse)
            {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(Const.iTIMEOUT, TimeUnit.SECONDS);

        asyncResponse.register(new CompletionCallback()
        {
            @Override
            public void onComplete(Throwable throwable)
            {
                if (throwable == null)
                {
                    // no throwable - the processing ended successfully
                    // (response already written to the client)
                    System.out.println("queryBayes Accomplication");
                }
                else
                {
                    throwable.printStackTrace();
                }
            }
        });

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    BayesQueryResponse resp = new BayesQueryResponse();
                    String strOutPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_BAYESIAN + Const.SPLIT_PATH + ds
                            + Const.SPLIT_PATH + ds + "-classify/part-m-00000";
                    List<String> lst = ReadSequenceWritable.readSeqFileBayes(ds, strOutPath, nStart, nStop);
                    resp.setRecommand(lst);
                    asyncResponse.resume(resp);
                }
                catch (Exception e)
                {
                    log.error("queryBayes: " + e.getLocalizedMessage());
                    asyncResponse.resume("queryBayes: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /**
     * 查询随机森林新数据的分类结果
     * @param asyncResponse
     * @param ds
     * @param nstart
     * @param nstop
     * @throws Exception
     */
    @GET
    @Path("/rf/{ds}&{nstart}&{nstop}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void queryRandomForest(@Suspended final AsyncResponse asyncResponse,
            @PathParam("ds") final String ds,
            @PathParam("nstart") final Long nStart,
            @PathParam("nstop") final Long nStop) throws Exception
    {
        log.info("queryRandomForest: dataSet = " + ds + ", nstart = " + nStart + ", nstop = " + nStop);
        System.out.println("queryRandomForest: dataSet = " + ds + ", nstart = " + nStart + ", nstop = " + nStop);

        if (null == ds || null == nStart || nStart < 0 || null == nStop || nStop < 0)
        {
            log.error("queryRandomForest: Inputted dataset has null.");
            throw new Exception("Inputted dataset has null.");
        }

        asyncResponse.setTimeoutHandler(new TimeoutHandler()
        {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse)
            {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(Const.iTIMEOUT, TimeUnit.SECONDS);

        asyncResponse.register(new CompletionCallback()
        {
            @Override
            public void onComplete(Throwable throwable)
            {
                if (throwable == null)
                {
                    // no throwable - the processing ended successfully
                    // (response already written to the client)
                    System.out.println("queryRandomForest Accomplication");
                }
                else
                {
                    throwable.printStackTrace();
                }
            }
        });

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    RFQueryResponse resp = new RFQueryResponse();
                    String strOutPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + Const.DMNAME_RANDOMFORESTS + Const.SPLIT_PATH + ds
                            + Const.SPLIT_PATH + ds + "-classify/part-m-00000";

                    List<String> lst = ReadSequenceWritable.readSeqFileRF(ds, strOutPath, nStart, nStop);
                    resp.setRecommand(lst);
                    asyncResponse.resume(resp);
                }
                catch (Exception e)
                {
                    log.error("queryRandomForest: " + e.getLocalizedMessage());
                    asyncResponse.resume("queryRandomForest: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /**
     * Read result of classify
     * @param asyncResponse     172.18.200.135:8888/DMP/rest/DMPImplService/query/qcr/bayes&20news-all
     * @param alg   alg name, eg: bayes
     * @param ds    data set name
     * @throws Exception
     */
    @GET
    @Path("/qcr/{alg}&{ds}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void queryClassifyRes(@Suspended final AsyncResponse asyncResponse,
            @PathParam("alg") final String alg,
            @PathParam("ds") final String ds) throws Exception
    {
        if (null == ds)
        {
            log.error("queryClassifyRes: Inputted dataset has null.");
            throw new Exception("Inputted dataset has null.");
        }

        asyncResponse.setTimeoutHandler(new TimeoutHandler()
        {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse)
            {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(Const.iTIMEOUT, TimeUnit.SECONDS);

        asyncResponse.register(new CompletionCallback()
        {
            @Override
            public void onComplete(Throwable throwable)
            {
                if (throwable == null)
                {
                    // no throwable - the processing ended successfully
                    // (response already written to the client)
                    System.out.println("queryClassifyRes Accomplication");
                }
                else
                {
                    throwable.printStackTrace();
                }
            }
        });

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    RFQueryResponse resp = new RFQueryResponse();
                    String strOutPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + alg + Const.SPLIT_PATH + ds + Const.SPLIT_PATH + ds;// + "-testing/part-m-00000";

                    boolean flag=FPParser(strOutPath);
                    List<String> lst = new LinkedList<String>();
                    lst.add("Query result: " + String.valueOf(flag));
                    resp.setRecommand(lst);
                    asyncResponse.resume(resp);
                }
                catch (Exception e)
                {
                    log.error("queryClassifyRes: " + e.getLocalizedMessage());
                    asyncResponse.resume("queryClassifyRes: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    private boolean FPParser(String outPath) throws IOException
    {
        boolean flag = false;
        try
        {
            flag=Parser.Parser(outPath + "-testing/part-m-00002", outPath + "-resultss", Const.HDFS_IP, new AKVRegex());
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
}
