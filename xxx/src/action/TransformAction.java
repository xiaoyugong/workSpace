package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import model.JobInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobStatus;
import org.apache.hadoop.mapred.RunningJob;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.KnowledgeService;
import util.MonitorUtil;
import util.hadoop.HadoopUtils;

import com.opensymphony.xwork2.ActionSupport;

@Component("transform")
@Scope("prototype")
public class TransformAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private Log log=LogFactory.getLog(TransformAction.class);
	private boolean flag;
	private List<JobInfo> jobInfosList=null;
	
//	private int jobNums=3;
	
	private KnowledgeService knowledgeService=new KnowledgeService();
	
	public String execute(){

		/*if(!MonitorUtil.ISRUNNING){
			log.info("the cluster is running!!!");
			return ERROR;
		}*/
		try {
			if(MonitorUtil.monitorJobs!=null&&MonitorUtil.monitorJobs.size()>0){
				if(MonitorUtil.checkRunOver()){
					flag=true;
					jobInfosList=new ArrayList<JobInfo>(MonitorUtil.monitorJobs.values());
					/**
					 * 解析FP输出，存储数据库
					 */
					knowledgeService.readFpWriteToDB();
					MonitorUtil.monitorJobs.clear();
					return SUCCESS;
				}
				JobStatus jobStatus= MonitorUtil.getNewJobStatus();
				if(jobStatus==null){ //  集群是第一次启动，jobStatus是空
					flag=false;
					jobInfosList=new ArrayList<JobInfo>(MonitorUtil.monitorJobs.values());
					return SUCCESS;
				}
				/**
				 * 判断任务状态
				 */
				if(MonitorUtil.monitorJobs.containsKey(jobStatus.getJobName())){
					String jobName=jobStatus.getJobName();
					JobClient jobClient=HadoopUtils.getJobClient();
					RunningJob runjob=jobClient.getJob(jobStatus.getJobID());
					if(jobStatus.getRunState()==JobStatus.RUNNING&&jobStatus.getStartTime()>=MonitorUtil.monitorJobs.get(jobName).getStartTime()){
						log.info("jobid:"+jobStatus.getJobID().toString()+",status:"+JobStatus.RUNNING+
								"   map:"+ runjob.mapProgress()+"    reduce:"+runjob.reduceProgress()+"   name:"+jobStatus.getJobName());
						MonitorUtil.monitorJobs.put(jobStatus.getJobName(), new JobInfo(jobStatus.getJobID().toString(),
						jobName,runjob.mapProgress(),runjob.reduceProgress(),"running"));
					}else if(jobStatus.getRunState()==JobStatus.FAILED&&jobStatus.getStartTime()>=MonitorUtil.monitorJobs.get(jobName).getStartTime()){
						log.info("jobid:"+jobStatus.getJobID().toString()+",status:"+JobStatus.FAILED);
						MonitorUtil.monitorJobs.put(jobStatus.getJobName(), new JobInfo(jobStatus.getJobID().toString(),
								jobName,runjob.mapProgress(),runjob.reduceProgress(),"failed"));
					}else if(jobStatus.getRunState()==JobStatus.PREP){
						log.info("jobid:"+jobStatus.getJobID().toString()+",status:"+JobStatus.PREP);
					}else if(jobStatus.getRunState()==JobStatus.SUCCEEDED&&jobStatus.getStartTime()>=MonitorUtil.monitorJobs.get(jobName).getStartTime()){
						log.info("jobid:"+jobStatus.getJobID().toString()+",status:"+JobStatus.SUCCEEDED);
						MonitorUtil.monitorJobs.put(jobStatus.getJobName(), new JobInfo(jobStatus.getJobID().toString(),
								jobName,runjob.mapProgress(),runjob.reduceProgress(),"successed"));
					}else{
						log.info("unknown jobStatus:"+jobStatus.getRunState()+" ----------------------");
					}
				}else{
					log.info("not running any furthur jobs******************");
				}
			}else{
				//--** initial monitorJobs
				try{
//					MonitorUtil.initialMonitorJobs(jobNums);
					MonitorUtil.initialMonitorJobs();
				}catch(Exception e){
					log.error("initialMonitorJobs error:\n"+e.getMessage());
					return ERROR;
				}
			}
			jobInfosList=new ArrayList<JobInfo>(MonitorUtil.monitorJobs.values());
		} catch (IOException e) {
			log.error("monitor is error :\n"+e.getMessage());
			return ERROR;
		}
		flag=false;
		return SUCCESS;
	}
	
	public boolean getFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public List<JobInfo> getJobInfosList() {
		return jobInfosList;
	}
	public void setJobInfosList(List<JobInfo> jobInfosList) {
		this.jobInfosList = jobInfosList;
	}

	/**
	 * @return the jobNums
	 */
//	public int getJobNums() {
//		return jobNums;
//	}
//
//	/**
//	 * @param jobNums the jobNums to set
//	 */
//	public void setJobNums(int jobNums) {
//		this.jobNums = jobNums;
//	}

	/**
	 * @return the knowledgeService
	 */
	public KnowledgeService getKnowledgeService() {
		return knowledgeService;
	}

	/**
	 * @param knowledgeService the knowledgeService to set
	 */
	@Resource
	public void setKnowledgeService(KnowledgeService knowledgeService) {
		this.knowledgeService = knowledgeService;
	}
}
