package com.parkbobo.manager.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.model.AppChannel;
import com.parkbobo.model.Users;
import com.parkbobo.service.AppChannelService;
import com.parkbobo.service.UsersService;

@Controller("mainAction")
@Scope("prototype")
public class MainAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6295425057955993493L;
	
	private String appChannelPieData;
	@Resource(name="appChannelService")
	private AppChannelService appChannelService;
	@Resource(name="usersService")
	private UsersService usersService;
	public String index()
	{
		appChannelPie();
		return "index";
	}
	/**
	 * APP渠道用户统计
	 */
	private void appChannelPie()
	{
		StringBuffer tempBuf = new StringBuffer();
		List<AppChannel> appChannels = appChannelService.getByHql("from AppChannel as a order by a.orderid");
		for (AppChannel appChannel : appChannels) {
			List<Users> users = usersService.getByHql("from Users as u where u.appChannel.channelid = " + appChannel.getChannelid());
			tempBuf.append("['" + appChannel.getName() + "'," + users.size() + "],");
		}
		if(tempBuf.length() > 0)
		{
			tempBuf.delete(tempBuf.length() -1, tempBuf.length());
		}
		appChannelPieData = tempBuf.toString();
	}
	public String getAppChannelPieData() {
		return appChannelPieData;
	}
	public void setAppChannelPieData(String appChannelPieData) {
		this.appChannelPieData = appChannelPieData;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
