package service;

import java.io.IOException;

import org.springframework.stereotype.Component;

import util.hadoop.HadoopUtils;

@Component
public class ModelService {
	/**
	 * 上传数据
	 * @param localPath
	 * @param deltaOrAll
	 * @return
	 * @throws IOException 
	 */
	public boolean upload(String localPath,String deltaOrAll) throws IOException{
		
		boolean flag= true;
		String uploadPath=null;
		try{
			uploadPath=HadoopUtils.upload(localPath, deltaOrAll);
			if (uploadPath == null) {
				flag = false;
			}
		}catch(Exception e){
			if(uploadPath!=null){
				HadoopUtils.delete(uploadPath);
			}
			flag=false;
		}
		return flag;
	}
}