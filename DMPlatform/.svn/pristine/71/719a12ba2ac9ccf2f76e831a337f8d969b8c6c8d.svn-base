/**
 * @Class: QueryResults.java
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

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.classify.BayesClassify;
import com.goldtel.dmp.classify.RFClassify;


public class ClassifyData
{
    private static final Logger log = LoggerFactory.getLogger(ClassifyData.class);

    /**
     * 按Bayes分类结果归类新数据
     * @param asyncResponse
     * @param ds    待分类的数据集
     * @param data  已分类的数据集
     * @return
     * @throws Exception
     */
    @GET
    @Path("/bayes/{model}&{ds}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void classifyBayes(@Suspended final AsyncResponse asyncResponse,@PathParam("model") final String model, @PathParam("ds") final String ds) throws Exception
    {
        if (null == ds || null == model)
        {
            log.error("classifyBayes: Inputted parameter has null.");
            throw new Exception("Inputted parameter has null.");
        }
        log.info("classifyBayes: dataSet = " + ds + ", model = " + model);
        System.out.println("classifyBayes: dataSet = " + ds + ", model = " + model);

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
                    System.out.println("classifyBayes Accomplication");
                }
                else
                {
                    throwable.printStackTrace();
                }
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run()
            {
                BayesClassify resp = new BayesClassify();
                try
                {
                    String res = resp.putResult(model, ds);
                    asyncResponse.resume(res);
                    //asyncResponse.resume(resp);
                }
                catch (Exception e)
                {
                    log.error("classifyBayes: " + e.getLocalizedMessage());
                    asyncResponse.resume("classifyBayes: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }

    /**
     * 按Random Forest分类结果归类新数据
     * 输入、输出路径、描述文件路径、模型文件、评估模型
     * @param asyncResponse
     * @param model
     * @param ds
     * @throws Exception
     */
    @GET
    @Path("/rf/{model}&{ds}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void classifyRandomForest(@Suspended final AsyncResponse asyncResponse,@PathParam("model") final String model, @PathParam("ds") final String ds) throws Exception
    {
        if (null == ds || null == model)
        {
            log.error("classifyRandomForest: Inputted parameter has null.");
            throw new Exception("Inputted parameter has null.");
        }
        log.info("classifyRandomForest: dataSet = " + ds + ", model = " + model);
        System.out.println("classifyRandomForest: dataSet = " + ds + ", model = " + model);

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
                    System.out.println("classifyRandomForest Accomplication");
                }
                else
                {
                    throwable.printStackTrace();
                }
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run()
            {
                RFClassify resp = new RFClassify();
                try
                {
                    String res = resp.putResult(model, ds);
                    asyncResponse.resume(res);
                }
                catch (Exception e)
                {
                    log.error("classifyRandomForest: " + e.getLocalizedMessage());
                    asyncResponse.resume("classifyRandomForest: " + e.getLocalizedMessage());
                }
            }
        }).start();
    }
}
