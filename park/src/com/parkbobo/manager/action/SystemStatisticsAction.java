package com.parkbobo.manager.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.manager.model.Manager;
import com.parkbobo.manager.service.SystemStatisticsViewService;
import com.parkbobo.utils.SystemCarStatistics;
import com.parkbobo.utils.SystemStatistics;

@Controller("systemStatisticsAction")
@Scope("prototype")
public class SystemStatisticsAction extends BaseAction{
	private SystemStatistics statistics;
	private String inputCarparkname;
	private List<SystemCarStatistics> carList;
	/**
	 * 系统统计
	 */
	private static final long serialVersionUID = -7758818528736469123L;
	@Resource(name="systemStatisticsViewService")
	private SystemStatisticsViewService statisticsViewService;

	public String list(){
		Manager manager = (Manager) request.getSession().getAttribute("loginManager");
		String area = manager.getArea();
		if(area!=null && "总部".equals(area)){
			statistics = statisticsViewService.getList(statistics);
			carList = statisticsViewService.getCarList(statistics);			
		}else if(area!=null && !"".equals(area)){
			if(statistics==null){
				statistics = new SystemStatistics();
			}
			statistics.setArea(area);
			statistics = statisticsViewService.getList(statistics);
			carList = statisticsViewService.getCarList(statistics);	
		}
		return "list";
	}
	
	public String carparkname() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String carparkName = statisticsViewService.getCarparkName(inputCarparkname);
		out.print(carparkName);
		out.flush();
		out.close();
		return NONE;
	}
	
	@Override
	public String logModel() {
		return null;
	}
	public void setStatistics(SystemStatistics statistics) {
		this.statistics = statistics;
	}

	public SystemStatistics getStatistics() {
		return statistics;
	}

	public String getInputCarparkname() {
		return inputCarparkname;
	}

	public void setInputCarparkname(String inputCarparkname) {
		this.inputCarparkname = inputCarparkname;
	}

	public void setCarList(List<SystemCarStatistics> carList) {
		this.carList = carList;
	}

	public List<SystemCarStatistics> getCarList() {
		return carList;
	}
	
}
