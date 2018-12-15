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

import com.parkbobo.groundlock.model.WeakupPhones;
import com.parkbobo.groundlock.service.WeakupPhonesService;
import com.parkbobo.manager.action.BaseAction;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.HttpClientUtils;
import com.parkbobo.utils.MD5;
import com.parkbobo.utils.Param;
@Controller("weakupPhonesAction")
@Scope("prototype")
public class WeakupPhonesAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3113257282476725449L;

	private final  static String  CHANGEWEAKUPPHONENUMBERLIST_URL= "groundLock_changeWeakupPhoneNumberList"; //修改可唤醒地锁电话列表

	private List<WeakupPhones> weakupPhonesList;
	private String groundlockid;
	private String telephoneOne;
	private String telephoneTwo;
	private String telephoneThree;
	private String err;
	
	@Resource
	private WeakupPhonesService weakupPhonesService;
	/**
	 * 授权电话列表
	 * @return
	 */
	public String list()
	{
		weakupPhonesList = this.weakupPhonesService.getByGroundlockid(groundlockid);
		if(weakupPhonesList.size()==1)
		{
			telephoneOne = weakupPhonesList.get(0).getTelephone();
		}
		else if(weakupPhonesList.size()==2)
		{
			telephoneOne = weakupPhonesList.get(0).getTelephone();
			telephoneTwo = weakupPhonesList.get(1).getTelephone();
		}
		else if(weakupPhonesList.size()==3)
		{
			telephoneOne = weakupPhonesList.get(0).getTelephone();
			telephoneTwo = weakupPhonesList.get(1).getTelephone();
			telephoneThree = weakupPhonesList.get(2).getTelephone();
		}
		return "list";
	}
	/**
	 * 修改授权电话号码
	 * @return
	 * @throws Exception 
	 */
	public String add() 
	{
		List<Param> params = getParams();
		if(!StringUtils.isEmpty(telephoneOne))
		{
			params.add(new Param("phoneNumberArr", telephoneOne));
		}
		if(!StringUtils.isEmpty(telephoneTwo))
		{
			params.add(new Param("phoneNumberArr", telephoneTwo));
		}
		if(!StringUtils.isEmpty(telephoneThree))
		{
			params.add(new Param("phoneNumberArr", telephoneThree));
		}
		try {
			String out = HttpClientUtils.getInstance().getResponseBodyAsString(
					Configuration.getInstance().getValue("groundlock_url")
							+ CHANGEWEAKUPPHONENUMBERLIST_URL, params);
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
		return forward("/weakupPhones_list?groundlockid=" + groundlockid + "&err=" + err);
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

	public List<WeakupPhones> getWeakupPhonesList() {
		return weakupPhonesList;
	}

	public void setWeakupPhonesList(List<WeakupPhones> weakupPhonesList) {
		this.weakupPhonesList = weakupPhonesList;
	}
	public String getTelephoneOne() {
		return telephoneOne;
	}
	public void setTelephoneOne(String telephoneOne) {
		this.telephoneOne = telephoneOne;
	}
	public String getTelephoneTwo() {
		return telephoneTwo;
	}
	public void setTelephoneTwo(String telephoneTwo) {
		this.telephoneTwo = telephoneTwo;
	}
	public String getTelephoneThree() {
		return telephoneThree;
	}
	public void setTelephoneThree(String telephoneThree) {
		this.telephoneThree = telephoneThree;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}

}
