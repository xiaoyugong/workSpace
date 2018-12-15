/**
 * 
 */
package action;

import java.io.IOException;

import javax.annotation.Resource;

import service.ModelService;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

import util.hadoop.HadoopUtils;
import mahout.FP;

@SuppressWarnings("serial")
@Component("modelAction")
@Scope("prototype")
public class ModelAction extends ActionSupport{

	private ModelService modelService;
	private String info;
	private String uploadDeltaOrAll;
	private String ftpPath;
	private int minSupport;
	private int maxHeap;
	private int numGroups;
	private int treeCache;
	private String splitter;
	private int numReducer;
	private String input;
	private String output;
	
	@Override
	public String execute(){
		System.out.println(HadoopUtils.getConf());
		return SUCCESS;
	}
	
	/**
	 * 上传数据
	 * @return
	 * @throws IOException 
	 */
	public String upload() throws IOException{

		if(modelService.upload(ftpPath, uploadDeltaOrAll)){
			setInfo("上传成功");
		}else{
			setInfo("上传失败");
		}
		return "upload";
	}
	
	public String train() throws InterruptedException{
		// 启动FP关联规则算法进程
		HadoopUtils.FP_INPATH = "hdfs://mycluster/input/" + input ;
		HadoopUtils.FP_OUTPATH = "hdfs://mycluster/output/fp/" + input;
		new Thread(new FP(minSupport, maxHeap, numGroups, treeCache, splitter, numReducer,input,output)).start();
		return "transform";
	}
	
	public int getMinSupport() {
		return minSupport;
	}


	public void setMinSupport(int minSupport) {
		this.minSupport = minSupport;
	}


	public int getMaxHeap() {
		return maxHeap;
	}


	public void setMaxHeap(int maxHeap) {
		this.maxHeap = maxHeap;
	}


	public int getNumGroups() {
		return numGroups;
	}


	public void setNumGroups(int numGroups) {
		this.numGroups = numGroups;
	}


	public int getTreeCache() {
		return treeCache;
	}


	public void setTreeCache(int treeCache) {
		this.treeCache = treeCache;
	}


	public String getSplitter() {
		return splitter;
	}


	public void setSplitter(String splitter) {
		this.splitter = splitter;
	}


	public int getNumReducer() {
		return numReducer;
	}


	public void setNumReducer(int numReducer) {
		this.numReducer = numReducer;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public ModelService getModelService() {
		return modelService;
	}
	@Resource
	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	public String getUploadDeltaOrAll() {
		return uploadDeltaOrAll;
	}

	public void setUploadDeltaOrAll(String uploadDeltaOrAll) {
		this.uploadDeltaOrAll = uploadDeltaOrAll;
	}

	public String getFtpPath() {
		return ftpPath;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
}
