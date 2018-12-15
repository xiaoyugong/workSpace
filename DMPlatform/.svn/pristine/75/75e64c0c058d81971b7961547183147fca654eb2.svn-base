package com.goldtel.dmp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.TimeoutHandler;

import jersey.repackaged.com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.glassfish.jersey.process.JerseyProcessingUncaughtExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.auth.Authentication;
import com.goldtel.dmp.interfaceentities.ClusterEntityQuery;
import com.goldtel.dmp.interfaceentities.ClusterEntityResponse;

//	这里@Path定义了类的层次路径。
//	指定了资源类提供服务的URI路径
//	测试地址: localhost:8080/DMP/rest/DMPImplService
@Path("DMPImplService")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class DMPImpl
{
    private static final Logger log = LoggerFactory.getLogger(DMPImpl.class);
	private static final ExecutorService QUEUE_EXECUTOR = Executors.newCachedThreadPool(new ThreadFactoryBuilder()
    														.setNameFormat("blocking-post-chat-resource-executor-%d")
    														.setUncaughtExceptionHandler(new JerseyProcessingUncaughtExceptionHandler()).build());
	/**
	 * 获取数据挖掘结果
	 * 测试地址: localhost:8080/DMP/rest/DMPImplService/result/wahaha
	 * @param asyncResponse	返回结果
	 * @param ibKey			定位要获取结果集市的标志，查询结果从该数据集市中获取，该标志由挖掘算法完成时返回
	 */
	@GET
	@Path("/result/{ibKey}")
	public void asyncGetResult(@Suspended final AsyncResponse asyncResponse, @PathParam("ibKey") final String ibKey)
	{
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
                    System.out.println("asyncGetResult Accomplication");
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
	            String result = veryExpensiveOperation(ibKey);
	            asyncResponse.resume(result);
	        }

	        private String veryExpensiveOperation(String key)
	        {
	        	String strKey = key;

	        	return "asyncGetResult " + strKey;
	        }
	    }).start();
	}

	/**
	 * 数据挖掘算法入口
	 * @param appId
	 * @param headers
	 * @param uriInfo
	 * @param securityContext
	 * @return
	 * @throws Exception
	 */
	@Path("/dm")
    public  Class<DataMining> dm(@PathParam("appId") String appId,@Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) throws Exception
    {
        log.info("dm: get result query message. appId = " + appId);
        Authentication.getInstance().auth(uriInfo, securityContext);

        return DataMining.class;
    }

	/**
	 * 根据分类模型对新数据进行分类
	 * @param appId
	 * @param headers
	 * @param uriInfo
	 * @param securityContext
	 * @return
	 * @throws Exception
	 */
	@Path("/classify")
    public  Class<ClassifyData> classify(@PathParam("appId") String appId,@Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) throws Exception
    {
        log.info("classify: get result query message. appId = " + appId);
        Authentication.getInstance().auth(uriInfo, securityContext);

        return ClassifyData.class;
    }

	/**
	 * 挖掘结果使用接口
	 * @param appId
	 * @param headers
	 * @param uriInfo
	 * @param securityContext
	 * @return 
	 * @return 
	 * @return
	 * @throws Exception
	 */
	@Path("/query")
    public  Class<QueryResults> query(@PathParam("appId") String appId,@Context HttpHeaders headers,
            @Context UriInfo uriInfo, @Context SecurityContext securityContext) throws Exception
    {
	    log.info("query: get result query message. appId = " + appId);
	    Authentication.getInstance().auth(uriInfo, securityContext);

	    return QueryResults.class;
    }

	/**
	 * 测试地址: localhost:8080/DMP/rest/DMPImplService/dmcallp
	 * @param asyncResponse
	 * @param calName
	 */
	@POST
	@Path("/dmcallp")
	public void asynPostDataMining(@Suspended final AsyncResponse asyncResponse,
									@FormParam("calName") final String calName)
	{
		asyncResponse.resume(calName);
	}

	@GET
    // 这里@Path定义了类的层次路径。指定了资源类提供服务的URI路径。172.18.200.135:8080/DMP/rest/DMPImplService/show/log300&VL-1&true&false
    @Path("/show/{ds}&{cn}&{p}&{h}")
    // @Produces定义了资源类方法会生成的媒体类型。
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
    // @PathParam向@Path定义的表达式注入URI参数值。
    public Response show(@QueryParam("callback") String callback,@PathParam("ds") final String ds, @PathParam("cn") final String cn,
                        @PathParam("p") final String p, @PathParam("h") final String h)
    {
	    ClusterEntityQuery query = new ClusterEntityQuery();
	    query.setDataSet(ds);
	    String cc = cn;
	    if (cc.equalsIgnoreCase("null"))
	    {
	        cc = null;
	    }
	    query.setCenterName(cc);
	    query.setBIsOnlyCenterPoints(Boolean.valueOf(p.trim()));
	    query.setBHasPara(Boolean.valueOf(h.trim()));
        //String callback = request.getParams().toString();
        ClusterEntityResponse resp = new ClusterEntityResponse();
        try
        {
            resp.putResult(query, "kmeans");
        }
        catch (Exception e)
        {
            log.error("queryCluster: " + e.getLocalizedMessage());
        }
        String ss = resp.toString();

        return Response.ok(resp).build();
    }
}