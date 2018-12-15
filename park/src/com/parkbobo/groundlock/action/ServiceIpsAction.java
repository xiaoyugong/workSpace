package com.parkbobo.groundlock.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.groundlock.model.ServiceIps;
import com.parkbobo.groundlock.service.ServiceIpsService;
import com.parkbobo.manager.action.BaseAction;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.HttpClientUtils;
import com.parkbobo.utils.MD5;
import com.parkbobo.utils.Param;
@Controller("serviceIpsAction")
@Scope("prototype")
public class ServiceIpsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1384577016218649863L;

	private final  static String  CHANGESERVICEIPLIST_URL		= "groundLock_changeServiceIPList"; //修改地锁服务器IP列表

	private List<ServiceIps> serviceIpsList;
	private String groundlockid;
	private String serviceIpsOne;
	private String serviceIpsTwo;
	private String serviceIpsThree;
	private String err;
	
	@Resource
	private ServiceIpsService serviceIpsService;
	/**
	 * 服务器IP列表
	 * @return
	 */
	public String list()
	{
		serviceIpsList = this.serviceIpsService.getByGroundlockid(groundlockid);
		if(serviceIpsList.size()==1)
		{
			serviceIpsOne = serviceIpsList.get(0).getIpaddress();
		}
		else if(serviceIpsList.size()==2)
		{
			serviceIpsOne = serviceIpsList.get(0).getIpaddress();
			serviceIpsTwo = serviceIpsList.get(1).getIpaddress();
		}
		else if(serviceIpsList.size()==3)
		{
			serviceIpsOne = serviceIpsList.get(0).getIpaddress();
			serviceIpsTwo = serviceIpsList.get(1).getIpaddress();
			serviceIpsThree = serviceIpsList.get(2).getIpaddress();
		}
		return "list";
	}
	/**
	 * 修改服务器IP列表
	 * @return
	 * @throws Exception 
	 */
	public String add()
	{
		List<Param> params = getParams();
		if(!StringUtils.isEmpty(serviceIpsOne))
		{
			params.add(new Param("ipArr", serviceIpsOne));
		}
		if(!StringUtils.isEmpty(serviceIpsTwo))
		{
			params.add(new Param("ipArr", serviceIpsTwo));
		}
		if(!StringUtils.isEmpty(serviceIpsThree))
		{
			params.add(new Param("ipArr", serviceIpsThree));
		}
		
		try {
			String out = HttpClientUtils.getInstance().getResponseBodyAsString(
					Configuration.getInstance().getValue("groundlock_url")
							+ CHANGESERVICEIPLIST_URL, params);
			if (out == null || out.equals("null")) {
				err = "0";
			}
			JSONObject jsonObject = JSONObject.fromObject(out);
			if (jsonObject.get("status").equals("true")) {
				err = "1";
			} else {
				err = "0";
			}
		} catch (Exception e) {
			err = "0";
		}
		return forward("/serviceIps_list?groundlockid=" + groundlockid + "&err=" + err);
	}
	private List<Param> getParams()
	{
		List<Param> params = new ArrayList<Param>();
		params.add(new Param("groundlockid", groundlockid));
		params.add(new Param("data", "20149026"));
		params.add(new Param("skey", "b8922cd1fef6e1c32c54d22d4d81ced9"));
		params.add(new Param("securityKey", getSecurityKey()));
		return params;
	}
	private String getSecurityKey()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		long now = new Date().getTime();
		String securityKey = MD5.getDefaultInstance().MD5Encode(Configuration.getInstance().getValue("key") + sdf.format(now)) ;
		return securityKey;
	}
	@Override
	public String logModel() {
		return null;
	}

	public String getGroundlockid() {
		return groundlockid;
	}

	public void setGroundlockid(String groundlockid) {
		this.groundlockid = groundlockid;
	}

	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	public List<ServiceIps> getServiceIpsList() {
		return serviceIpsList;
	}
	public void setServiceIpsList(List<ServiceIps> serviceIpsList) {
		this.serviceIpsList = serviceIpsList;
	}
	public String getServiceIpsOne() {
		return serviceIpsOne;
	}
	public void setServiceIpsOne(String serviceIpsOne) {
		this.serviceIpsOne = serviceIpsOne;
	}
	public String getServiceIpsTwo() {
		return serviceIpsTwo;
	}
	public void setServiceIpsTwo(String serviceIpsTwo) {
		this.serviceIpsTwo = serviceIpsTwo;
	}
	public String getServiceIpsThree() {
		return serviceIpsThree;
	}
	public void setServiceIpsThree(String serviceIpsThree) {
		this.serviceIpsThree = serviceIpsThree;
	}

}
