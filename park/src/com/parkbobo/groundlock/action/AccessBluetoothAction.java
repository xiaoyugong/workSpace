package com.parkbobo.groundlock.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.groundlock.model.AccessBluetooth;
import com.parkbobo.groundlock.service.AccessBluetoothService;
import com.parkbobo.manager.action.BaseAction;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.HttpClientUtils;
import com.parkbobo.utils.MD5;
import com.parkbobo.utils.PageBean;
import com.parkbobo.utils.Param;
@Controller("accessBluetoothAction")
@Scope("prototype")
public class AccessBluetoothAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4360727437320740836L;
	private final  static String  CHANGEBLUETOOTHLIST_URL		= "groundLock_changeBluetoothList"; //修改可影响地锁升降蓝牙列表
	private Long bluetoothid;
	private String groundlockmac;
	private String bluetoothmac;
	private PageBean<AccessBluetooth> accessBluetoothPage;
	private AccessBluetooth accessBluetooth;
	private String method;
	private String err;
	
	@Resource
	private AccessBluetoothService accessBluetoothService;
	/**
	 * 授权蓝牙列表
	 * @return
	 */
	public String list()
	{
		String hql = "from  AccessBluetooth as a where 1=1";
		if(!StringUtils.isEmpty(groundlockmac))
		{
			hql += " and a.groundlock.groundlockid = '" +  groundlockmac + "'";
		}
		hql += " order by a.groundlock.groundlockid , a.bluetoothid";
		accessBluetoothPage = this.accessBluetoothService.getPage(hql, getPageSize(), getPage());
		return "list";
	}
	/**
	 * 添加蓝牙
	 * @throws Exception 
	 */
	public String add() 
	{
		if(!StringUtils.isEmpty(method) && method.equals("add"))
		{
			if(!isMac(accessBluetooth.getBluetoothmacStr()))
			{
				err = "MAC地锁格式错误";
				return "add";
			}
			List<AccessBluetooth> accessBluetooths = this.accessBluetoothService
			.getByHql("from AccessBluetooth as a where a.groundlock.groundlockid = '"
					+  accessBluetooth.getGroundlock().getGroundlockid() + "' order by a.bluetoothid");
			List<Param> params = getParams();
			for (AccessBluetooth bluetooth : accessBluetooths) {
				params.add(new Param("bluetoothMacArr", bluetooth.getBluetoothmacStr()));
				params.add(new Param("validMaskArr", bluetooth.getBluetoothEnable()+""));
				params.add(new Param("typeMaskArr", bluetooth.getBluetoothtype()+""));
				params.add(new Param("carNumberArr", (StringUtils.isEmpty(bluetooth.getCarNumber())?'0': bluetooth.getCarNumber()).toString()));
				params.add(new Param("telephoneArr", (StringUtils.isEmpty(bluetooth.getTelephone())?'0': bluetooth.getTelephone()).toString()));
			}
			params.add(new Param("bluetoothMacArr", accessBluetooth.getBluetoothmacStr()));
			params.add(new Param("validMaskArr", accessBluetooth.getBluetoothEnable()+""));
			params.add(new Param("typeMaskArr", accessBluetooth.getBluetoothtype()+""));
			params.add(new Param("carNumberArr", (StringUtils.isEmpty(accessBluetooth.getCarNumber())?'0': accessBluetooth.getCarNumber()).toString()));
			params.add(new Param("telephoneArr", (StringUtils.isEmpty(accessBluetooth.getTelephone())?'0': accessBluetooth.getTelephone()).toString()));
			try {
				String out = HttpClientUtils.getInstance()
						.getResponseBodyAsString(
								Configuration.getInstance().getValue(
										"groundlock_url")
										+ CHANGEBLUETOOTHLIST_URL, params);
				if (out == null || out.equals("null")) {
					err = "授权蓝牙列表修改失败";
					return "add";
				}
				JSONObject jsonObject = JSONObject.fromObject(out);
				if (jsonObject.get("status").equals("true")) {
					return forward("/accessBluetooth_list?groundlockmac="
							+ accessBluetooth.getGroundlock().getGroundlockid());
				} else {
					err = "授权蓝牙列表修改失败";
					return "add";
				}
			} catch (Exception e) {
				err = "授权蓝牙列表修改失败，网络异常";
				return "add";
			}
		}
		else
		{
			return "add";
		}
	}
	/**
	 * 修改授权蓝牙
	 * @throws Exception 
	 */
	public String edit()
	{
		if(!StringUtils.isEmpty(method) && method.equals("edit"))
		{
			if(!isMac(accessBluetooth.getBluetoothmacStr()))
			{
				err = "MAC地锁格式错误";
				return "edit";
			}
			List<AccessBluetooth> accessBluetooths = this.accessBluetoothService
				.getByHql("from AccessBluetooth as a where a.groundlock.groundlockid = '"
						+  accessBluetooth.getGroundlock().getGroundlockid() + "' order by a.bluetoothid");
			List<Param> params = getParams();
			for (AccessBluetooth bluetooth : accessBluetooths) {
				if(bluetooth.getBluetoothmac().equals( accessBluetooth.getBluetoothmac()))
				{
					params.add(new Param(("bluetoothMacArr"), accessBluetooth.getBluetoothmacStr()));
					params.add(new Param(("validMaskArr"), accessBluetooth.getBluetoothEnable()+""));
					params.add(new Param(("typeMaskArr"), accessBluetooth.getBluetoothtype()+""));
					params.add(new Param(("carNumberArr"), (StringUtils.isEmpty(accessBluetooth.getCarNumber())?'0': accessBluetooth.getCarNumber()).toString()));
					params.add(new Param(("telephoneArr"), (StringUtils.isEmpty(accessBluetooth.getTelephone())?'0': accessBluetooth.getTelephone()).toString()));
				}
				else
				{
					params.add(new Param(("bluetoothMacArr"), bluetooth.getBluetoothmacStr()));
					params.add(new Param(("validMaskArr"), bluetooth.getBluetoothEnable()+""));
					params.add(new Param(("typeMaskArr"), bluetooth.getBluetoothtype()+""));
					params.add(new Param(("carNumberArr"), (StringUtils.isEmpty(bluetooth.getCarNumber())?'0': bluetooth.getCarNumber()).toString()));
					params.add(new Param(("telephoneArr"), (StringUtils.isEmpty(bluetooth.getTelephone())?'0': bluetooth.getTelephone()).toString()));
				}
			}
			try {
				String out = HttpClientUtils.getInstance()
						.getResponseBodyAsString(
								Configuration.getInstance().getValue(
										"groundlock_url")
										+ CHANGEBLUETOOTHLIST_URL, params);
				if (out == null || out.equals("null")) {
					err = "授权蓝牙列表修改失败";
					return "edit";
				}
				JSONObject jsonObject = JSONObject.fromObject(out);
				if (jsonObject.get("status").equals("true")) {
					return forward("/accessBluetooth_list?groundlockmac="
							+ accessBluetooth.getGroundlock().getGroundlockid());
				} else {
					err = "授权蓝牙列表修改失败";
					return "edit";
				}
			} catch (Exception e) {
				err = "授权蓝牙列表修改失败，网络异常";
				return "add";
			}
		}
		else
		{
			accessBluetooth = this.accessBluetoothService.getById(bluetoothid);
			return "edit";
		}
	}
	/**
	 * 删除授权蓝牙
	 * @return
	 * @throws IOException 
	 */
	public String delete() throws IOException
	{
		List<AccessBluetooth> accessBluetooths = this.accessBluetoothService
			.getByHql("from AccessBluetooth as a where a.groundlock.groundlockid = '"
				+  accessBluetooth.getGroundlock().getGroundlockid() + "' order by a.bluetoothid");
		List<Param> params = getParams();
		for (AccessBluetooth bluetooth : accessBluetooths) {
			if(!bluetooth.getBluetoothid().equals(bluetoothid))
			{
				params.add(new Param(("bluetoothMacArr"), bluetooth.getBluetoothmacStr()));
				params.add(new Param(("validMaskArr"), bluetooth.getBluetoothEnable()+""));
				params.add(new Param(("typeMaskArr"), bluetooth.getBluetoothtype()+""));
				params.add(new Param(("carNumberArr"), (StringUtils.isEmpty(bluetooth.getCarNumber())?'0': bluetooth.getCarNumber()).toString()));
				params.add(new Param(("telephoneArr"), (StringUtils.isEmpty(bluetooth.getTelephone())?'0': bluetooth.getTelephone()).toString()));
			}
		}
		try {
			String out = HttpClientUtils.getInstance()
					.getResponseBodyAsString(
							Configuration.getInstance().getValue(
									"groundlock_url")
									+ CHANGEBLUETOOTHLIST_URL, params);
			if (out == null || out.equals("null")) {
				output("{\"status\":\"false\"}");
			}
			JSONObject jsonObject = JSONObject.fromObject(out);
			if (jsonObject.get("status").equals("true")) {
				output("{\"status\":\"true\"}");
			} else {
				output("{\"status\":\"false\"}");
			}
		} catch (Exception e) {
			output("{\"status\":\"false\"}");
		}
		return NONE;
	}
	/**
	 * 判断是否是MAC地址
	 * @param macStr
	 * @return
	 */
	private boolean isMac(String macStr)
	{
		String macPattern="[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]";
		return Pattern.matches(macPattern, macStr);
	}
	/**
	 * 参数
	 * @return
	 */
	private List<Param> getParams()
	{
		List<Param> params = new ArrayList<Param>();
		params.add(new Param("groundlockid", accessBluetooth.getGroundlock().getGroundlockid()));
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
	/**
	 * 打印输出
	 * @param outStr
	 * @throws IOException
	 */
	private void output(String outStr) throws IOException
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(outStr);
		out.flush();
		out.close();
	}
	@Override
	public String logModel() {
		return null;
	}
	public PageBean<AccessBluetooth> getAccessBluetoothPage() {
		return accessBluetoothPage;
	}
	public void setAccessBluetoothPage(PageBean<AccessBluetooth> accessBluetoothPage) {
		this.accessBluetoothPage = accessBluetoothPage;
	}
	public String getGroundlockmac() {
		return groundlockmac;
	}
	public void setGroundlockmac(String groundlockmac) {
		this.groundlockmac = groundlockmac;
	}
	public String getBluetoothmac() {
		return bluetoothmac;
	}
	public void setBluetoothmac(String bluetoothmac) {
		this.bluetoothmac = bluetoothmac;
	}
	public Long getBluetoothid() {
		return bluetoothid;
	}
	public void setBluetoothid(Long bluetoothid) {
		this.bluetoothid = bluetoothid;
	}
	public AccessBluetooth getAccessBluetooth() {
		return accessBluetooth;
	}
	public void setAccessBluetooth(AccessBluetooth accessBluetooth) {
		this.accessBluetooth = accessBluetooth;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
}
