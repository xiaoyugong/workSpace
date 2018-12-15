package com.parkbobo.manager.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.DriverAuthentication;
import com.parkbobo.service.DriverAuthenticationService;
import com.parkbobo.service.UsersService;
import com.parkbobo.utils.MyPrintUtil;
import com.parkbobo.utils.PageBean;

/**
 * @author Administrator
 *车主认证
 */

@Controller("driverAuthAction")
@Scope("prototype")
public class DriverAuthAction extends BaseAction {

	private PageBean<DriverAuthentication> pageBean;
	@Resource(name="usersService")
	private UsersService usersService;
	private DriverAuthentication driverAuth; 
	private String userid;
	private String method;
	private String area;
	@Resource(name="driverAuthenticationService")
	private DriverAuthenticationService driverAuthenticationService;
	private File driverAttachment;//获取上传文件  
    public File getDriverAttachment() {
		return driverAttachment;
	}

	public void setDriverAttachment(File driverAttachment) {
		this.driverAttachment = driverAttachment;
	}

	private String driverAttachmentFileName;//获取上传文件名称  
    private String driverAttachmentContentType;//获取上传文件类型  
	public String list()
	{
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			String hql = "from DriverAuthentication  where 1=1"; 
			if(driverAuth!=null){
					
				if(driverAuth.getUsers()!=null)
					hql+=" and users.username like '%"+driverAuth.getUsers().getUsername().trim()+"%' " ;
				if(driverAuth.getStatus()!=null&&driverAuth.getStatus()!=9)
						hql += "and  status="+driverAuth.getStatus();
				}
			
			hql += " order by status, posttime desc ";
			pageBean = driverAuthenticationService.loadPage(hql, getPageSize(), getPage());
//			Collections.sort(usersPage.getList(), new ApproveTypeSort()); 
			
			return "list";
		}else if(area!=null && !"".equals(area)){
			String hql = "from DriverAuthentication  where users.area like '%"+area+"%'"; 
			if(driverAuth!=null){
					
				if(driverAuth.getUsers()!=null)
					hql+=" and users.username like '%"+driverAuth.getUsers().getUsername().trim()+"%' " ;
				if(driverAuth.getStatus()!=null&&driverAuth.getStatus()!=9)
						hql += "and  status="+driverAuth.getStatus();
				}
			
			hql += " order by status, posttime desc ";
			pageBean = driverAuthenticationService.loadPage(hql, getPageSize(), getPage());
//			Collections.sort(usersPage.getList(), new ApproveTypeSort()); 
			
			return "list";
		}else{
			return "list";
		}
		
	}
	/**
	 * 认证划分
	 * @throws IOException 
	 * 
	 * */
	public String updateArea() throws IOException{
		if(method!=null && "save".equals(method)){
			//保存操作
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			DriverAuthentication authentication = driverAuthenticationService.getByUserid(userid);
			if(area!=null && !"".equals(area)){
				authentication.getUsers().setArea(area);	
				try {
					driverAuthenticationService.update(authentication);
					out.print("{\"status\":\"true\"}");					
				} catch (Exception e) {
					out.print("{\"status\":\"false\"}");		
				}
				out.flush();
				out.close();
			}
			return NONE;
		}else{
			return "updateArea";			
		}
	}
	
	/**
	 * 跳转到审核界面
	 * @return
	 */
	public String check(){
		driverAuth =driverAuthenticationService.getByUserid( driverAuth.getUserid());
		return "check";
	}
	
	
	
	
	/**
	 * 审核
	 * @return
	 */
	public String update() {
		DriverAuthentication driverAuthentication = driverAuthenticationService.getByUserid(this.driverAuth.getUserid());
		driverAuthentication.setStatus(driverAuth.getStatus());
		driverAuthentication.setMemo(driverAuth.getMemo());
		driverAuthentication.getUsers().setDriverStatus((short)(driverAuth.getStatus()+1));
		driverAuthenticationService.merge(driverAuthentication);
		this.saveLog("车主"+driverAuthentication.getUsers().getUsername()+",审核为:"+convertStart(driverAuth.getStatus()));
		return forward("/driverAuth_list");
	}
	private String convertStart(Short status){
		HashMap<Short,String> statusMap = new HashMap();
		statusMap.put((short)1,"通过" );
		statusMap.put((short)2,"未通过" );
		return statusMap.get(status);
	}
	public String delete()
	{
		driverAuthenticationService.bulkDelete(getIds());
		return forward("/driverAuth_list");
	}

	/**
	 * 跳转到上传附件
	 * @return
	 */
	public String uploadAttachment() {
		String userid = driverAuth.getUserid();
		driverAuth = driverAuthenticationService.getByUserid(userid);
		return "uploadAttachment";
	}
	/**
	 * uploadSaveAttachment
	 * @throws Exception 
	 * 
	 * */
	public String uploadSaveAttachment() throws Exception{
		String userid = driverAuth.getUserid();
		DriverAuthentication driverAuthNew = driverAuthenticationService.getByUserid(userid);
		//System.out.println(driverAuth);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
        	String yyyyMMdd = sdf.format(new Date());
    		String savePath = getSavePath()
    		+ "upload"+System.getProperty("file.separator")+"image" + System.getProperty("file.separator") 
    		+ yyyyMMdd + System.getProperty("file.separator");
    		String newFileName = UUID.randomUUID().toString() + "." + driverAttachmentFileName.substring(driverAttachmentFileName.indexOf(".")+1).toLowerCase();
    		File saveDirFile = new File(savePath);
    		if (!saveDirFile.exists()) 
    		{
    			saveDirFile.setWritable(true, false); //设置文件夹权限，避免在Linux下不能写入文件
    			saveDirFile.mkdirs();
    		}
    		File saveFile = new File(savePath + newFileName);
    		FileUtils.copyFile(driverAttachment, saveFile);
    		driverAuthNew.setAttached("upload"+System.getProperty("file.separator")+"image" + System.getProperty("file.separator") + yyyyMMdd + System.getProperty("file.separator")+newFileName);
    		driverAuthenticationService.update(driverAuthNew);
            return forward("/driverAuth_list");
		} catch (Exception e) {
			System.out.println(e);
			return "uploadAttachment";
		}
	
//		System.out.println(driverAuth.getUserid());
//		System.out.println(driverAttachment);
//		System.out.println(driverAttachmentContentType);
//		System.out.println(driverAttachmentFileName);
		
	}

	public PageBean<DriverAuthentication> getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean<DriverAuthentication> pageBean) {
		this.pageBean = pageBean;
	}

	public DriverAuthentication getDriverAuth() {
		return driverAuth;
	}

	public void setDriverAuth(DriverAuthentication driverAuth) {
		this.driverAuth = driverAuth;
	}

	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "车主认证";
	}

	public DriverAuthenticationService getDriverAuthenticationService() {
		return driverAuthenticationService;
	}

	public void setDriverAuthenticationService(
			DriverAuthenticationService driverAuthenticationService) {
		this.driverAuthenticationService = driverAuthenticationService;
	}

	public String getDriverAttachmentFileName() {
		return driverAttachmentFileName;
	}

	public void setDriverAttachmentFileName(String driverAttachmentFileName) {
		this.driverAttachmentFileName = driverAttachmentFileName;
	}

	public String getDriverAttachmentContentType() {
		return driverAttachmentContentType;
	}

	public void setDriverAttachmentContentType(String driverAttachmentContentType) {
		this.driverAttachmentContentType = driverAttachmentContentType;
	}
	
	/**
     * 得到项目根目录
     * @return
     */
    private String getSavePath() throws Exception{
        return ServletActionContext.getServletContext().getRealPath("/");
    }
    /**
	 * 关闭输入输出流
	 * @param fos
	 * @param fis
	 */
	private void close(FileOutputStream fos, FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
                fis=null;
            } catch (IOException e) {
                System.out.println();
                MyPrintUtil.getDefaultInstance().out("FileInputStream关闭失败");
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
                fis=null;
            } catch (IOException e) {
                MyPrintUtil.getDefaultInstance().out("FileOutputStream关闭失败");
                e.printStackTrace();
            }
        }
    }

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	
}
