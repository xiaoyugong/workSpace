/**
 * 
 */
package util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.Map;

import model.JobInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobStatus;
import org.apache.hadoop.mapreduce.JobID;

import util.hadoop.HadoopUtils;

import com.google.common.io.Closeables;

public class MonitorUtil {
	private static Log log=LogFactory.getLog(MonitorUtil.class);
	
	public static String lastJob;
	public static Map<String ,JobInfo> monitorJobs;
	public static int times=1;
	public static void initialMonitorJobs() throws IOException{
//		JobClient jobClient=HadoopUtils.getJobClient();
//		JobStatus[] jobStatusAll=jobClient.getAllJobs();
//		JobStatus jobStatus=null;
//		int id =0;
//		String jobIden="";
//		/**
//		 * 防止当前云平台是第一次启动，这个时候没有任务列表，获取的jobStatus是空;
//		 */
//		if(jobStatusAll==null||jobStatusAll.length<=0){
//			//修改TaskTracker代码，把集群启动时间写入hdfs，然后在这里读取出来
//			id=0;
//			jobIden=readJTStartTime();
//			
//		}else{
//			int f=0;
//			int j=0;
//			for(int i=0;i<jobStatusAll.length;i++){
//				if(jobStatusAll[i].getJobID().getId()>j){
//					j=jobStatusAll[i].getJobID().getId();
//					f=i;
//				}
//			}
//			jobStatus=jobStatusAll[f];
//			id=jobStatus.getJobID().getId();
//			jobIden=jobStatus.getJobID().getJtIdentifier();
//		}
//		
//		log.info("initial monitorJobs with the start jobID :"+id);
//		 
//		monitorJobs=new LinkedHashMap<String,JobInfo>();
//		String jobId="";
//		for(int i=0;i<jobNums;i++){
//			jobId= new JobID(jobIden,id+1+i).toString();
//			monitorJobs.put(jobId, new JobInfo(jobId));
//		}
//		lastJobId=jobId;
		String jobname1="Parallel Counting Driver running over input: "+HadoopUtils.FP_INPATH;
		String jobname2="PFP Growth Driver running over input"+HadoopUtils.FP_INPATH;
		String jobname3="PFP Aggregator Driver running over input: "+HadoopUtils.FP_OUTPATH+"/fpgrowth";
		monitorJobs=new LinkedHashMap<String,JobInfo>();
		monitorJobs.put(jobname1, new JobInfo(jobname1,System.currentTimeMillis()));
		monitorJobs.put(jobname2, new JobInfo(jobname2,System.currentTimeMillis()));
		monitorJobs.put(jobname3, new JobInfo(jobname3,System.currentTimeMillis()));
		lastJob=jobname3;
		log.info("initial monitor jobs map done !!!");
	}
	/**
	 * 获取最新的jobStatus
	 * @return
	 * @throws IOException
	 */
	public static JobStatus getNewJobStatus() throws IOException{
		JobStatus[] jobStatusAll=new JobClient(new InetSocketAddress(HadoopUtils.getHost(),
					HadoopUtils.getJobtrackerPort()), HadoopUtils.getConf()).getAllJobs();
		if(jobStatusAll.length<=0){
			return null;
		}
		int f=0;
		int tmp=0;
		for(int i=0;i<jobStatusAll.length;i++){
				if(monitorJobs.containsKey(jobStatusAll[i].getJobName())){
					if (jobStatusAll[i].getJobID().getId()>tmp) {
						tmp=jobStatusAll[i].getJobID().getId();
						f=i;
					}
				}
		}
		JobStatus jobStatus=jobStatusAll[f];
		return jobStatus;
	}
	/**
	 * 集群是否正在运行
	 */
	public static boolean ISRUNNING=false;

	/**
	 * 算法是否运行完毕
	 * @return
	 */
	public static boolean checkRunOver(){
		boolean flag= false;
		JobInfo jobInfo= monitorJobs.get(lastJob);
		if(jobInfo==null){
			return flag;
		}
		if("successed".equals(jobInfo.getRunState())){
			flag=true;
		}
		return flag;
	}
	
	private static String readJTStartTime() throws IOException{
		Path path= new Path("/private/jobtracker/starttime");
	    FileSystem fs = FileSystem.get(path.toUri(), HadoopUtils.getConf());
	    FSDataInputStream in = fs.open(path);
	    try {
	      return in.readUTF();
	    } finally {
	      Closeables.closeQuietly(in);
	    }
	    
	}
}
