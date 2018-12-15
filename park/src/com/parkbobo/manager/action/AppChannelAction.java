package com.parkbobo.manager.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.model.AppChannel;
import com.parkbobo.service.AppChannelService;
import com.parkbobo.utils.PageBean;

@Controller("appChannelAction")
@Scope("prototype")
public class AppChannelAction extends BaseAction {
	private static final long serialVersionUID = -7994082125734349733L;
	private AppChannel appChannel;
	private Long id;
	private PageBean<AppChannel> appChannelPage;
	
	@Resource(name="appChannelService")
	private AppChannelService appChannelService;

	/**
	 * 渠道添加
	 * @return
	 */
	public String add()
	{
		if(getMethod().equals("add"))
		{
			appChannelService.add(appChannel);
			return forward("/appChannel_list");
		}
		else
		{
			return "add";
		}
	}
	/**
	 * 渠道编辑
	 * @return
	 */
	public String edit()
	{
		if(getMethod().equals("edit"))
		{
			appChannelService.add(appChannel);
			return forward("/appChannel_list");
		}
		else
		{
			appChannel = appChannelService.getById(id);
			return "edit";
		}
	}
	/**
	 * 渠道列表
	 * @return
	 */
	public String list()
	{
		String hql = "from AppChannel as a where 1=1 ";
		if(appChannel != null)
		{
			if(appChannel.getName() != null && !appChannel.getName().equals(""))
			{
				hql += " and a.name like '%" + appChannel.getName().trim() + "%'";
			}
		}
		hql += " order by a.orderid";
		appChannelPage = appChannelService.loadPage(hql, getPageSize(), getPage());
		return "list";
	}
	/**
	 * 渠道删除
	 * @return
	 */
	public String delete()
	{
		appChannelService.bulkDelete(getIds());
		return forward("/appChannel_list");
	}
	
	public AppChannel getAppChannel() {
		return appChannel;
	}

	public void setAppChannel(AppChannel appChannel) {
		this.appChannel = appChannel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PageBean<AppChannel> getAppChannelPage() {
		return appChannelPage;
	}

	public void setAppChannelPage(PageBean<AppChannel> appChannelPage) {
		this.appChannelPage = appChannelPage;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "app渠道";
	}
}
