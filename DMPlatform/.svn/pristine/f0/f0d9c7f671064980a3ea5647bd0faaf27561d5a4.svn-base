/**
 * @Class: DataMining.java
 * @Description:
 * @Author: Zhaoliheng Jun 4, 2015
 * @Version: V1.0
 */

package com.goldtel.dmp;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.datamining.bayes.BayesImpl;
import com.goldtel.dmp.datamining.canopy.CanopyImpl;
import com.goldtel.dmp.datamining.distributeditembased.DistributedItemBasedImpl;
import com.goldtel.dmp.datamining.frequentpattern.FrequentPatternImpl;
import com.goldtel.dmp.datamining.kmeans.KMeansImpl;
import com.goldtel.dmp.datamining.kmeans.KMeansSeImpl;
import com.goldtel.dmp.datamining.randomforests.RandomForestsImpl;
import com.goldtel.dmp.interfaceentities.DMEntityCondition;
import com.goldtel.dmp.interfaceentities.RFEntityCondition;

public class DataMining
{
    private static final Logger log = LoggerFactory.getLogger(DataMining.class);

    /**
     * DIB算法挖掘
     * @param asyncResponse
     * @param callback
     * @param ds        数据集
     * @param para1     算法参数，格式：参数名1:参数值1,参数名2:参数值2...
     * @throws Exception
     */
    @GET
    @Path("/dib/{ds}&{para}")
    // @Produces定义了资源类方法会生成的媒体类型。
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void dmDIB(@Suspended final AsyncResponse asyncResponse,
                      @QueryParam("callback") String callback,
                      @PathParam("ds") final String ds,
                      @PathParam("para") final String para1) throws Exception
    {
        if (null == ds || 0 == ds.trim().length() || null == para1 || 0 == para1.trim().length())
        {
            log.error("dmDIB: Inputted parameters have null.");
            throw new Exception("Inputted parameters have null.");
        }

        log.info("dmDIB: dataSet = " + ds + ", para1 = " + para1);
        System.out.println("dmDIB: dataSet = " + ds + ", para1 = " + para1);

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
                    System.out.println("dmDIB Accomplication");
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
                    DMEntityCondition cond = new DMEntityCondition();
                    cond.setDataSet(ds);
                    cond.setDmPara(Utils.putList(para1, Const.SPLIT_ANALYZE_S));
                    DistributedItemBasedImpl dm = new DistributedItemBasedImpl(cond);
                    String result = dm.run();

                    asyncResponse.resume(result);
                }
                catch (Exception e)
                {
                    log.error("dmDIB: " + e.getLocalizedMessage());
                    asyncResponse.resume("dmDIB: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /**
     * FP算法挖掘
     * @param asyncResponse
     * @param callback
     * @param ds        数据集
     * @param para1     算法参数，格式：参数名1:参数值1,参数名2:参数值2....
     * @throws Exception
     */
    @GET
    @Path("/fp/{ds}&{para}")
    // @Produces定义了资源类方法会生成的媒体类型。
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void dmFP(@Suspended final AsyncResponse asyncResponse,
                     @QueryParam("callback") String callback,
                     @PathParam("ds") final String ds,
                     @PathParam("para") final String para1) throws Exception
    {
        if (null == ds || 0 == ds.trim().length() || null == para1 || 0 == para1.trim().length())
        {
            log.error("dmFP: Inputted parameters have null.");
            throw new Exception("Inputted parameters have null.");
        }

        log.info("dmFP: dataSet = " + ds + ", para1 = " + para1);
        System.out.println("dmFP: dataSet = " + ds + ", para1 = " + para1);

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
                    System.out.println("dmFP Accomplication");
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
                    DMEntityCondition cond = new DMEntityCondition();
                    cond.setDataSet(ds);
                    cond.setDmPara(Utils.putListSplit(para1, Const.SPLIT_ANALYZE_S));
                    FrequentPatternImpl dm = new FrequentPatternImpl(cond);
                    String result = dm.run();

                    asyncResponse.resume(result);
                }
                catch (Exception e)
                {
                    log.error("dmFP: " + e.getLocalizedMessage());
                    asyncResponse.resume("dmFP: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /**
     * Canopy算法挖掘
     * @param query
     * @return
     * @throws Exception
     */
    @GET
    @Path("/canopy")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response dmCanopy(DMEntityCondition cond) throws Exception
    {
        if (null == cond)
        {
            log.error("dmCanopy: Inputted parameter is null.");
            throw new Exception("Inputted parameter is null.");
        }
        log.info("dmCanopy: dataSet = " + cond.getDataSet());

        CanopyImpl dm = new CanopyImpl(cond);
        String result = null;
        try
        {
            result = dm.run();
        }
        catch (Exception e)
        {
            log.error("dmCanopy: " + e.getLocalizedMessage());
            throw e;
        }

        return Response.ok(result).build();
    }

    /**
     * Canopy算法挖掘
     * @param asyncResponse
     * @param callback
     * @param ds        数据集
     * @param para1     算法参数，格式：参数名1:参数值1,参数名2:参数值2...
     * @throws Exception
     */
    @GET
    @Path("/canopy/{ds}&{para}")
    // @Produces定义了资源类方法会生成的媒体类型。
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void dmCanopy(@Suspended final AsyncResponse asyncResponse,
                         @QueryParam("callback") String callback,
                         @PathParam("ds") final String ds,
                         @PathParam("para") final String para1) throws Exception
    {
        if (null == ds || 0 == ds.trim().length() || null == para1 || 0 == para1.trim().length())
        {
            log.error("dmCanopy: Inputted parameters have null.");
            throw new Exception("Inputted parameters have null.");
        }

        log.info("dmCanopy: dataSet = " + ds + ", para1 = " + para1);
        System.out.println("dmCanopy: dataSet = " + ds + ", para1 = " + para1);

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
                    System.out.println("dmCanopy Accomplication");
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
                    DMEntityCondition cond = new DMEntityCondition();
                    cond.setDataSet(ds);
                    cond.setDmPara(Utils.putList(para1, Const.SPLIT_ANALYZE_S));
                    CanopyImpl dm = new CanopyImpl(cond);
                    String result = dm.run();

                    asyncResponse.resume(result);
                }
                catch (Exception e)
                {
                    log.error("dmCanopy: " + e.getLocalizedMessage());
                    asyncResponse.resume("dmCanopy: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /**
     * K-Means算法挖掘
     * @return
     * @throws Exception
     */
    @GET
    @Path("/kmeans")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response dmKmeans(DMEntityCondition cond) throws Exception
    {
        if (null == cond)
        {
            log.error("dmKmeans: Inputted parameter is null.");
            throw new Exception("Inputted parameter is null.");
        }
        log.info("dmKmeans: dataSet = " + cond.getDataSet());
        System.out.println("dmKmeans: dataSet = " + cond.getDataSet());
        KMeansImpl dm = new KMeansImpl(cond);

        String result = null;
        try
        {
            result = dm.run();
        }
        catch (Exception e)
        {
            log.error("dmKmeans: " + e.getLocalizedMessage());
            throw e;
        }

        return Response.ok(result).build();
    }

    /**
     * K-Means算法挖掘 
     * @param asyncResponse
     * @param callback
     * @param ds        数据集
     * @param para1     算法参数，格式：参数名1:参数值1,参数名2:参数值2...，这个是第一个用来计算聚类中心个数的Canopy算法的参数
     * @param para2     算法参数，格式：参数名1:参数值1,参数名2:参数值2...，如果算法包含了两个挖掘算法，这个是第二个K-Means算法的参数
     * @throws Exception
     */
    @GET
    @Path("/kmeans/{ds}&{para1}&{para2}")
    // @Produces定义了资源类方法会生成的媒体类型。
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void dmKmeans(@Suspended final AsyncResponse asyncResponse,
                         @QueryParam("callback") String callback,
                         @PathParam("ds") final String ds,
                         @PathParam("para1") final String para1,
                         @PathParam("para2") final String para2) throws Exception
    {
        if (null == ds || 0 == ds.trim().length() || null == para1 || 0 == para1.trim().length() || null == para2 || 0 == para2.trim().length())
        {
            log.error("dmKmeans: Inputted parameters have null.");
            throw new Exception("Inputted parameters have null.");
        }

        log.info("dmKmeans: dataSet = " + ds + ", para1 = " + para1 + ", para2 = " + para2);
        System.out.println("dmKmeans: dataSet = " + ds + ", para1 = " + para1 + ", para2 = " + para2);

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
                    System.out.println("dmKmeans Accomplication");
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
                    DMEntityCondition cond = new DMEntityCondition();
                    cond.setDataSet(ds);
                    cond.setDmPara(Utils.putList(para1, Const.SPLIT_ANALYZE_S));
                    cond.setDmParas(Utils.putList(para2, Const.SPLIT_ANALYZE_S));
                    KMeansImpl dm = new KMeansImpl(cond);
                    String result = dm.run();

                    asyncResponse.resume(result);
                }
                catch (Exception e)
                {
                    log.error("dmKmeans: " + e.getLocalizedMessage());
                    asyncResponse.resume("dmKmeans: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /**
     * Inputted file is sequence: localhost:8080/DMP/rest/DMPImplService/dm/kmeansse/kmeansdatase&kmeansdatacenter&-dm:org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure,-k:7,-cd:0.5,-x:10
     * @param asyncResponse
     * @param callback
     * @param ds
     * @param para1
     * @param para2
     * @throws Exception
     */
    @GET
    @Path("/kmeansse/{ds}&{center}&{para}")
    // @Produces定义了资源类方法会生成的媒体类型。
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void dmKmeansSe(@Suspended final AsyncResponse asyncResponse,
                         @QueryParam("callback") String callback,
                         @PathParam("ds") final String ds,
                         @PathParam("center") final String center,
                         @PathParam("para") final String para) throws Exception
    {
        if (null == ds || 0 == ds.trim().length() || null == center || 0 == center.trim().length() || null == para || 0 == para.trim().length())
        {
            log.error("dmKmeans Se: Inputted parameters have null.");
            throw new Exception("Inputted parameters have null.");
        }

        log.info("dmKmeans Se: dataSet = " + ds + ", center = " + center + ", para = " + para);
        System.out.println("dmKmeans Se: dataSet = " + ds + ", center = " + center + ", para = " + para);

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
                    System.out.println("dmKmeans Se Accomplication");
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
                    DMEntityCondition cond = new DMEntityCondition();
                    cond.setDataSet(ds);
                    cond.setKMeansCenter(center);
                    //cond.setDmPara(Utils.putList(para1, Const.SPLIT_ANALYZE_S));
                    cond.setDmParas(Utils.putList(para, Const.SPLIT_ANALYZE_S));
                    KMeansSeImpl dm = new KMeansSeImpl(cond);
                    String result = dm.run();

                    asyncResponse.resume(result);
                }
                catch (Exception e)
                {
                    log.error("dmKmeans Se: " + e.getLocalizedMessage());
                    asyncResponse.resume("dmKmeans Se: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /** 这里@Path定义了类的层次路径。指定了资源类提供服务的URI路径
     * @param ds 数据集
     * @param para3 输入数据生成训练和测试两部分的参数，格式 参数名:参数值,...
     */
    @GET
    @Path("/bayes/{ds}&{para3}")
    // @Produces定义了资源类方法会生成的媒体类型。
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    // @PathParam向@Path定义的表达式注入URI参数值。
    public void dmNaiveBayes(@Suspended final AsyncResponse asyncResponse,
                             @QueryParam("callback") String callback,
                             @PathParam("ds") final String ds,
                             @PathParam("para3") final String para3) throws Exception
    {
        if (null == ds)
        {
            log.error("dmNaiveBayes: Inputted parameter is null.");
            throw new Exception("Inputted parameter is null.");
        }
        log.info("dmNaiveBayes: dataSet = " + ds + ", para3 = " + para3);
        System.out.println("dmNaiveBayes: dataSet = " + ds + ", para3 = " + para3);

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
                    System.out.println("dmNaiveBayes Accomplication");
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
                    BayesImpl dm = new BayesImpl(ds, para3);
                    String result = dm.run();

                    asyncResponse.resume(result);
                }
                catch (Exception e)
                {
                    log.error("dmNaiveBayes: " + e.getLocalizedMessage());
                    asyncResponse.resume("dmNaiveBayes: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /** 这里@Path定义了类的层次路径。指定了资源类提供服务的URI路径
     * @param ds 数据集
     * @param pdesc 输入生成描述文件的参数，格式 参数名:参数值1:参数值2:...,...
     * @param pbuild 输入建立随机森林模型的参数，格式 参数名:参数值,...
     */  
    @GET
    @Path("/rf/{ds}&{pdesc}&{pbuild}")
    // @Produces定义了资源类方法会生成的媒体类型。
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void dmRandomForests(@Suspended final AsyncResponse asyncResponse,
                                @QueryParam("callback") String callback,
                                @PathParam("ds") final String ds,
                                @PathParam("pdesc") final String pDesc,
                                @PathParam("pbuild") final String pBuild) throws Exception
    {
        if (null == ds)
        {
            log.error("dmRandomForests: Inputted parameter is null.");
            throw new Exception("Inputted parameter is null.");
        }
        log.info("dmRandomForests: dataSet = " + ds + ", pdesc = " + pDesc + ", pbuild = " + pBuild);
        System.out.println("dmRandomForests: dataSet = " + ds + ", pdesc = " + pDesc + ", pbuild = " + pBuild);

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
                    System.out.println("dmRandomForests Accomplication");
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
                    RFEntityCondition cond = new RFEntityCondition();
                    cond.setDataSet(ds);
                    cond.setDesc(Utils.convertParaToArr(pDesc));
                    cond.setBuild(Utils.convertParaToArr(pBuild));
                    RandomForestsImpl dm = new RandomForestsImpl(cond);
                    String result = dm.run();

                    asyncResponse.resume(result);
                }
                catch (Exception e)
                {
                    log.error("dmRandomForests: " + e.getLocalizedMessage());
                    asyncResponse.resume("dmRandomForests: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }
}
