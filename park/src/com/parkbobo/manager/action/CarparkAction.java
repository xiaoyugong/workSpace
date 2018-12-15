package com.parkbobo.manager.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.postgresql.jdbc2.optional.SimpleDataSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.Carpark;
import com.parkbobo.model.CarparkCategory;
import com.parkbobo.model.CarparkSystemPrice;
import com.parkbobo.model.Notify;
import com.parkbobo.model.Users;
import com.parkbobo.service.CarparkCategoryService;
import com.parkbobo.service.CarparkService;
import com.parkbobo.service.NotifyService;
import com.parkbobo.service.UsersService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.ExcelUtils;
import com.parkbobo.utils.MyPrintUtil;
import com.parkbobo.utils.PageBean;
import com.parkbobo.utils.StringUtils;

@Controller("carparkAction")
@Scope("prototype")
public class CarparkAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7099146514213494425L;
	private List<CarparkCategory> carparkCategories;
	private Carpark carpark;
	private PageBean<Carpark> carparkPage;
	private Long id;
	private File upload;
	private String uploadFileName;
	@Resource(name="carparkService")
	private CarparkService carparkService;
	@Resource(name="carparkCategoryService")
	private CarparkCategoryService carparkCategoryService;
	private static final String imgFilePathString="/carpark/";
	private static final String sqlFilePathString="/sql/";
	private static final String sqlPathString="D:"+File.separator+"sql"+File.separator;
	@Resource(name = "notifyService")
	private NotifyService notifyService;
	private Notify notify;
	@Resource(name = "usersService")
	private UsersService usersService;
	private String cusername;
	private String shareStartTime;
	private String shareEndTime;
	private String weekday;
	/**
	 * 停车场添加
	 */
	public String add()
	{
		if(getMethod().equals("add"))
		{
			carpark.setPosttime(System.currentTimeMillis());
			carparkService.add(carpark);
			this.saveLog("添加停车场【"+carpark.getName()+"】");
			return forward("/carpark_list");
		}
		else
		{
			carparkCategories = carparkCategoryService.getByHql("from CarparkCategory as c where c.isDel = 0 order by c.orderid");
			return "add";
		}
	}
	
	
	public String importsql() {
		
		return "importsql";
	}
	
	
	public String exesql() throws IOException {
		try {
			String sql = FileUtils.readFileToString(upload);
			carparkService.exesql(sql);
			response.getWriter().print("导入成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.getWriter().print("导入失败");
			e.printStackTrace();
		}
		return null;
	}
	public InputStream getInputStream() throws FileNotFoundException, UnsupportedEncodingException{
		File file = carparkService.outsql(getIds(),sqlPathString);
		FileInputStream inputStream = new FileInputStream(file);
		downloadFileName = file.getName();
		return inputStream;
		
	}
	public String outsql() {
		return "download";
	}
	
	/**
	 * 停车场编辑
	 * @return
	 * @throws Exception 
	 */
	public String edit() throws Exception
	{
		if(getMethod().equals("edit"))
		{
			Carpark oldcarpark = carparkService.getById(carpark.getCarparkid());
			if(upload!=null)
				carpark.setPicarr(uploadImg());
			
			carparkService.add(carpark);
			this.saveLog("修改停车场【"+carpark.getName()+"】;ID:"+carpark.getCarparkid());
			return forward("/carpark_list");
		}
		else
		{
			carpark = carparkService.getById(id);
			carparkCategories = carparkCategoryService.getByHql("from CarparkCategory as c where c.isDel = 0 order by c.orderid");
			return "edit";
		}
	}
	
	
	
	/**
	 * 系统定价编辑
	 * @return
	 * @throws Exception 
	 */
	public String systemPriceEdit() throws Exception
	{
		if(getMethod().equals("save"))
		{
			
			SimpleDateFormat time = new SimpleDateFormat("HH:mm");
			Date startTime = time.parse(shareStartTime);
			Date endTime = time.parse(shareEndTime);
			Carpark oldcarpark = carparkService.getById(carpark.getCarparkid());
			CarparkSystemPrice newCarparkSystemPrice = carpark.getCarparkSystemPrice();
			newCarparkSystemPrice.setShareStartTime(startTime);
			newCarparkSystemPrice.setShareEndTime(endTime);
			if(weekday!=null && !"".equals(weekday)){
				String c = weekday.replace(" ", "");
				if(!c.startsWith(",")){
					c = ",".concat(c);
				}
				if(!c.endsWith(",")){
					c = c.concat(",");
				}
				newCarparkSystemPrice.setShareRepeatDate(c);
			}
			newCarparkSystemPrice.setCarparkid(carpark.getCarparkid());
			oldcarpark.setCarparkSystemPrice(newCarparkSystemPrice);
			carparkService.add(oldcarpark);
			this.saveLog("修改系统定价【"+carpark.getName()+"】;ID:"+carpark.getCarparkid());
			return forward("/carpark_list");
		}else{
			carpark = carparkService.getById(id);
			carparkCategories = carparkCategoryService.getByHql("from CarparkCategory as c where c.isDel = 0 order by c.orderid");
			return "systemPriceEdit";
		}
	}
	
	public String uploadImg() throws Exception{
		String path ="";
		String newFileName = "";
		if(upload!=null){
			newFileName = StringUtils.getNewFileName(this.uploadFileName);
			 path = Configuration.getInstance().getValue("parkimg")
					+imgFilePathString;
			File file =  new File(path);
			 if(!file.isDirectory()){
				 file.mkdirs();
			 }
			FileUtils.copyFile(upload, new File(path+"/"+newFileName));
			return "images"+imgFilePathString+newFileName;
		}
		return null;
	}
	
	/**
	 * 停车场列表
	 * @return
	 */
	public String list()
	{
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			carparkCategories = carparkCategoryService.getByHql("from CarparkCategory as c where c.isDel = 0 order by c.orderid");
			String hql = "from Carpark as c where 1=1";
			if(carpark != null)
			{
				if(carpark.getName() != null && !carpark.getName().equals(""))
				{
					hql += " and c.name like '%" + carpark.getName().trim() + "%'";
				}
				if(carpark.getCarparkCategory() != null && carpark.getCarparkCategory().getCategoryid() != null)
				{
					hql += " and c.carparkCategory.categoryid = " + carpark.getCarparkCategory().getCategoryid();
				}
				if(carpark.getMaptype() != null)
				{
					hql += " and c.maptype = " + carpark.getMaptype();
				}
			}
			hql += " order by c.carparkid desc";
			carparkPage = carparkService.loadPage(hql, getPageSize(), getPage());
			return "list";
		}else if(area!=null && !"".equals(area)){
			carparkCategories = carparkCategoryService.getByHql("from CarparkCategory as c where c.isDel = 0 order by c.orderid");
			String hql = "from Carpark as c where c.city like '%"+area+"%'";
			if(carpark != null)
			{
				if(carpark.getName() != null && !carpark.getName().equals(""))
				{
					hql += " and c.name like '%" + carpark.getName().trim() + "%'";
				}
				if(carpark.getCarparkCategory() != null && carpark.getCarparkCategory().getCategoryid() != null)
				{
					hql += " and c.carparkCategory.categoryid = " + carpark.getCarparkCategory().getCategoryid();
				}
				if(carpark.getMaptype() != null)
				{
					hql += " and c.maptype = " + carpark.getMaptype();
				}
			}
			hql += " order by c.carparkid desc";
			carparkPage = carparkService.loadPage(hql, getPageSize(), getPage());
			return "list";
		}else{
			return "list";
		}
	}
	
	/**
	 * 用户新增列表
	 * @return
	 */
	public String userList()
	{
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		//area = "广州";
		if(area!=null && "总部".equals(area)){
			carparkCategories = carparkCategoryService.getByHql("from CarparkCategory as c where c.isDel = 0 order by c.orderid");
			String hql = "from Carpark as c where 1=1 and maptype=3";
			if(carpark != null)
			{
				if(carpark.getName() != null && !carpark.getName().equals(""))
				{
					hql += " and c.name like '%" + carpark.getName().trim() + "%'";
				}
				if(carpark.getCarparkCategory() != null && carpark.getCarparkCategory().getCategoryid() != null)
				{
					hql += " and c.carparkCategory.categoryid = " + carpark.getCarparkCategory().getCategoryid();
				}
			}
			hql += " order by c.carparkid desc";
			carparkPage = carparkService.loadPage(hql, getPageSize(), getPage());
			return "userList";
		}else if(area!=null && !"".equals(area)){
			carparkCategories = carparkCategoryService.getByHql("from CarparkCategory as c where c.isDel = 0 order by c.orderid");
			String hql = "from Carpark as c where c.city like '%"+area+"%' and maptype=3";
			if(carpark != null)
			{
				if(carpark.getName() != null && !carpark.getName().equals(""))
				{
					hql += " and c.name like '%" + carpark.getName().trim() + "%'";
				}
				if(carpark.getCarparkCategory() != null && carpark.getCarparkCategory().getCategoryid() != null)
				{
					hql += " and c.carparkCategory.categoryid = " + carpark.getCarparkCategory().getCategoryid();
				}
			}
			hql += " order by c.carparkid desc";
			carparkPage = carparkService.loadPage(hql, getPageSize(), getPage());
			return "userList";
		}else{
			return "userList";
		}
		
	}
	/**
	 * 用户新增编辑
	 * @return
	 * @throws Exception
	 */
	public String userEdit() throws Exception
	{
		if(getMethod().equals("edit"))
		{
			Carpark oldcarpark = carparkService.getById(carpark.getCarparkid());
			if(upload!=null)
				carpark.setPicarr(uploadImg());
			
			carparkService.add(carpark);
			this.saveLog("修改停车场【"+carpark.getName()+"】;ID:"+carpark.getCarparkid());
			return forward("/carpark_list");
		}
		else
		{
			carpark = carparkService.getById(id);
			carparkCategories = carparkCategoryService.getByHql("from CarparkCategory as c where c.isDel = 0 order by c.orderid");
			return "userEdit";
		}
	}
	/**
	 * 停车场删除
	 * @return
	 */
	public String delete()
	{
		carparkService.bulkDelete(getIds());
		this.saveLog("删除停车场，id为:"+getIds());
		return forward("/carpark_list");
	}
	
	/**
	 * 得到停车场json数据
	 * @return
	 * @throws IOException
	 */
	public String getCarparkJson() throws IOException{
		
		String hql = "from Carpark as c where c.name like '%" + carpark.getName() + "%'";
		hql += " order by c.posttime desc,c.carparkid desc";
		carparkPage = carparkService.loadPage(hql, getPageSize(), getPage());
		StringBuffer sbBuffer = new StringBuffer("{\"status\":\"true\",\"data\":[");
		for(Carpark carpark: carparkPage.getList()){
			sbBuffer.append("{\"id\":"+carpark.getCarparkid()+",\"name\":\""+carpark.getName()+"\",\"type\":\""+(carpark.getCarparkCategory()==null?"":carpark.getCarparkCategory().getName())+"\"},");
		}
		sbBuffer.append("]}");
		sbBuffer = sbBuffer.deleteCharAt(sbBuffer.length()-3);
		response.setContentType("text/html;charset=utf-8");
		if(carparkPage.getList().size()>0)
			response.getWriter().print(sbBuffer);
		else {
			response.getWriter().print("{\"status\":\"false\"}");
		}
		return null;
	}
	
	
	/**
	 * 停车场批量导入
	 * @return
	 */
	public String importExcel()
	{
		if(upload != null)
		{
			FileOutputStream fos = null;
	        FileInputStream fis = null;
	        try {
	        	String savePath = Configuration.getInstance().getValue("attachedPath")
	        			+ "exceltemp" + System.getProperty("file.separator") ; //避免在windows和Linux下文件路径分隔斜杠不同，统一采用System.getProperty("file.separator")写法
	        	String newFileName = UUID.randomUUID().toString() + "." + getUploadFileName().substring(getUploadFileName().indexOf(".")+1).toLowerCase();
	    		File saveDirFile = new File(savePath);
	    		if (!saveDirFile.exists()) 
	    		{
	    			saveDirFile.setWritable(true, false); //设置文件夹权限，避免在Linux下不能写入文件
	    			saveDirFile.mkdirs();
	    		}
				fos = new FileOutputStream(savePath + newFileName);
				fis = new FileInputStream(getUpload());
				byte[] buffer = new byte[1024];
	            int len = 0;
	            while ((len = fis.read(buffer)) != -1) {
	                fos.write(buffer, 0, len);
	            }
	            List<List<String>> list =ExcelUtils.getInstance().read(new File(savePath + newFileName));
	            carparkService.bulkAdd(list);
	        } 
	        catch (Exception e) {
				e.printStackTrace();
			} 
	        finally {
	            close(fos, fis);
	        }
		}
		return "importExcel";
	}
	
	public String toNotify(){
		
		return "toNotify";
	}
	
	public String sendNotify() {
		Users user = usersService.getByHql("from Users where username='"+carpark.getUsername().trim()+"'").get(0);
		List<Users> usersList = new ArrayList<Users>();
		usersList.add(user);
		notifyService.add(notify,usersList,0,1);
		return forward("/carpark_list");
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
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CarparkService getCarparkService() {
		return carparkService;
	}
	public void setCarparkService(CarparkService carparkService) {
		this.carparkService = carparkService;
	}
	public PageBean<Carpark> getCarparkPage() {
		return carparkPage;
	}
	public void setCarparkPage(PageBean<Carpark> carparkPage) {
		this.carparkPage = carparkPage;
	}


	public Carpark getCarpark() {
		return carpark;
	}


	public void setCarpark(Carpark carpark) {
		this.carpark = carpark;
	}


	public List<CarparkCategory> getCarparkCategories() {
		return carparkCategories;
	}


	public void setCarparkCategories(List<CarparkCategory> carparkCategories) {
		this.carparkCategories = carparkCategories;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}



	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "停车场管理";
	}



	public Notify getNotify() {
		return notify;
	}



	public void setNotify(Notify notify) {
		this.notify = notify;
	}



	public String getCusername() {
		return cusername;
	}



	public void setCusername(String cusername) {
		this.cusername = cusername;
	}


	public String getShareStartTime() {
		return shareStartTime;
	}


	public void setShareStartTime(String shareStartTime) {
		this.shareStartTime = shareStartTime;
	}


	public String getShareEndTime() {
		return shareEndTime;
	}


	public void setShareEndTime(String shareEndTime) {
		this.shareEndTime = shareEndTime;
	}


	public String getWeekday() {
		return weekday;
	}


	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	
	
	
}
