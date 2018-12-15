package com.parkbobo.groundlock.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.manager.action.BaseAction;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.HttpClientUtils;
import com.parkbobo.utils.MD5;
import com.parkbobo.utils.Param;
@Controller("groundlockControlAction")
@Scope("prototype")
public class GroundlockControlAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3438137969986890811L;
	private final static String  UP_URL                         = "groundLock_serviceControlUp"; //升锁
	private final static String  DOWN_URL						= "groundLock_serviceControlDown"; //降锁
	private final static String  GETBLUETOOTHLIST_URL			= "groundLock_sendGetBluetoothList"; //获取蓝牙列表
	private final static String  GETSERVICEIPLIST_URL			= "groundLock_sendGetServiceIPList"; //获取服务器IP列表
	private final static String  GETWEAKUPPHONENUMBERLIST_URL	= "groundLock_sendGetWeakupPhoneNumberList"; //获取唤醒地锁电话列表
	private final static String  GETPROTECTENERGY_URL			= "groundLock_sendProtectEnergy"; //获取地锁保护电量
	private final static String  GETBLUETOOTHPASSWORD_URL		= "groundLock_sendGetBluetoothPassword"; //获取蓝牙配对口令
	private final static String  GETSTATUS_URL					= "groundLock_sendGetStatus"; //获取地锁当前状态
	private final static String  GETENERGY_URL					= "groundLock_sendGetEnergy"; //获取剩余电量
	private final static String  GETTIME_URL					= "groundLock_sendGetTime"; //获取地锁时间
	private final static String  GETPHONENUMBER_URL				= "groundLock_sendGetPhoneNumber"; //获取地锁电话号码
	private final static String  GETINFOS_URL					= "groundLock_sendGetInfos"; //获取地锁固定信息
	private final static String  GETSIGNAL_URL					= "groundLock_sendGetSignal"; //获取SIM卡信号强度
	private final static String  SMSWEAKUP_URL					= "groundLock_smsWeakup"; //短信唤醒地锁
	
	private final static String  FORECEUP_URL					= "groundLock_serviceControlForeceUp"; //强制摇杆升锁
	private final static String  FORECEDOWN_URL					= "groundLock_serviceControlForeceDown"; //强制摇杆降锁
	private final static String  GETAUTOWEAKUPTIME_URL			= "groundLock_sendGetAutoWeakupTime"; //获取自动唤醒分钟数
	
	private final static String  CHANGESERVICEIPLIST_URL		= "groundLock_changeServiceIPList"; //修改地锁服务器IP列表
	private final static String  CHANGEPROTECTENERGY_URL		= "groundLock_changeProtectEnergy"; //修改地锁保护电量
	private final static String  CHANGEBLUETOOTHPASSWORD_URL	= "groundLock_changeBluetoothPassword"; //修改蓝牙配对口令
	private final static String  CHANGEBLUETOOTHLIST_URL		= "groundLock_changeBluetoothList"; //修改可影响地锁升降蓝牙列表
	private final static String  CHANGEWEAKUPPHONENUMBERLIST_URL= "groundLock_changeWeakupPhoneNumberList"; //修改可唤醒地锁电话列表
	private final static String  CHANGECHECKTIME_URL			= "groundLock_changeCheckTime"; //修改地锁时间
	private final static String  SENDSLEEP_URL					= "groundLock_sendSleep"; //休眠地锁
	private final static String  CHANGEAUTOWEAKUPTIME_URL		= "groundLock_changeAutoWeakupTime"; //修改地锁自动唤醒时间
	private final static String  CHANGEGROUNDPHONENUMBER_URL	= "groundLock_changeGroundPhoneNumber"; //修改地锁电话号码
	
	private String groundlockid;
	private String[] ipArr;
	private Integer protectEnergy;
	private String bluetoothPassword;
	private String[] bluetoothMacArr;
	private String[] validMaskArr;
	private String[] typeMaskArr;
	private String[] phoneNumberArr;
	private Integer autoWeakupTime;
	private String telephone;
	/**
	 * 强制摇杆升锁
	 * @return
	 * @throws Exception 
	 */
	public String foreceUp() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + FORECEUP_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 强制摇杆降锁
	 * @return
	 * @throws Exception 
	 */
	public String foreceDown() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + FORECEDOWN_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 
	 * 升锁
	 * @return
	 * @throws Exception 
	 */
	public String up() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + UP_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 降锁
	 * @return
	 * @throws Exception 
	 */
	public String down() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + DOWN_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 获取蓝牙列表
	 * @return
	 * @throws Exception 
	 */
	public String sendGetBluetoothList() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + GETBLUETOOTHLIST_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 获取地锁服务器IP列表
	 * @return
	 * @throws Exception 
	 */
	public String sendGetServiceIPList() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + GETSERVICEIPLIST_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 获取唤醒地锁电话列表
	 * @return
	 * @throws Exception 
	 */
	public String sendGetWeakupPhoneNumberList() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + GETWEAKUPPHONENUMBERLIST_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 获取地锁保护电量
	 * @return
	 * @throws Exception 
	 */
	public String sendProtectEnergy() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + GETPROTECTENERGY_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 获取自动唤醒分钟数
	 * @return
	 * @throws Exception
	 */
	public String sendGetAutoWeakupTime() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + GETAUTOWEAKUPTIME_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 获取地锁蓝牙配对口令
	 * @return
	 * @throws Exception 
	 */
	public String sendGetBluetoothPassword() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + GETBLUETOOTHPASSWORD_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 获取地锁当前状态
	 * @return
	 * @throws Exception 
	 */
	public String sendGetStatus() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + GETSTATUS_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 获取地锁剩余电量百分比
	 * @return
	 * @throws Exception 
	 */
	public String sendGetEnergy() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + GETENERGY_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 获取地锁时间
	 * @return
	 * @throws Exception 
	 */
	public String sendGetTime() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + GETTIME_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 获取地锁电话号码
	 * @return
	 * @throws Exception 
	 */
	public String sendGetPhoneNumber() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + GETPHONENUMBER_URL, getParams());
		output(out);
		return NONE;
	}
	
	/**
	 * 获取地锁固定信息
	 * @return
	 * @throws Exception 
	 */
	public String sendGetInfos() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + GETINFOS_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 获取SIM卡信号强度
	 * @return
	 * @throws Exception 
	 */
	public String sendGetSignal() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + GETSIGNAL_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 *  短信唤醒地锁
	 * @return
	 * @throws Exception 
	 */
	public String smsWeakup() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + SMSWEAKUP_URL, getParams());
		output(out);
		return NONE;
	}
	
	/**
	 * 修改地锁服务器IP列表
	 * @return
	 * @throws Exception 
	 */
	public String changeServiceIPList() throws Exception
	{
		List<Param> params = getParams();
		for(String str : ipArr)
		{
			params.add(new Param("ipArr", str));
		}
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + CHANGESERVICEIPLIST_URL, params);
		output(out);
		return NONE;
	}
	/**
	 * 修改地锁保护电量百分比
	 * @return
	 * @throws Exception 
	 */
	public String changeProtectEnergy() throws Exception
	{
		List<Param> params = getParams();
		params.add(new Param("protectEnergy", protectEnergy + ""));
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + CHANGEPROTECTENERGY_URL, params);
		output(out);
		return NONE;
	}
	/**
	 * 修改地锁蓝牙口令
	 * @return
	 * @throws Exception 
	 */
	public String changeBluetoothPassword() throws Exception
	{
		List<Param> params = getParams();
		params.add(new Param("bluetoothPassword", bluetoothPassword ));
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + CHANGEBLUETOOTHPASSWORD_URL, params);
		output(out);
		return NONE;
	}
	/**
	 * 修改可影响地锁升降蓝牙列表
	 * @return
	 * @throws Exception 
	 */
	public String changeBluetoothList() throws Exception
	{
		List<Param> params = getParams();
		for(int i =  0; i < bluetoothMacArr.length; i++)
		{
			params.add(new Param("bluetoothMacArr", bluetoothMacArr[i]));
			params.add(new Param("validMaskArr", validMaskArr[i]));
			params.add(new Param("typeMaskArr", typeMaskArr[i]));
			
		}
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + CHANGEBLUETOOTHLIST_URL, params);
		output(out);
		return NONE;
	}
	/**
	 * 修改地锁SIM卡卡号
	 * @return
	 * @throws Exception
	 */
	public String changeGroundPhoneNumber() throws Exception
	{
		List<Param> params = getParams();
		params.add(new Param("telephone", telephone));
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + CHANGEGROUNDPHONENUMBER_URL, params);
		output(out);
		return NONE;
	}
	/**
	 * 修改可唤醒地锁电话列表
	 * @return
	 * @throws Exception 
	 */
	public String changeWeakupPhoneNumberList() throws Exception
	{
		List<Param> params = getParams();
		for(String str: phoneNumberArr)
		{
			params.add(new Param("phoneNumberArr", str));
		}
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + CHANGEWEAKUPPHONENUMBERLIST_URL, params);
		output(out);
		return NONE;
	}
	/**
	 * 修改自动唤醒分钟数
	 * @return
	 * @throws Exception
	 */
	public String changeAutoWeakupTime() throws Exception
	{
		List<Param> params = getParams();
		params.add(new Param("autoWeakupTime", autoWeakupTime+""));
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + CHANGEAUTOWEAKUPTIME_URL, params);
		output(out);
		return NONE;
	}
	/**
	 * 发送修改地锁时间校验
	 * @return
	 * @throws Exception 
	 */
	public String changeCheckTime() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + CHANGECHECKTIME_URL, getParams());
		output(out);
		return NONE;
	}
	/**
	 * 发送休眠地锁
	 * @return
	 * @throws Exception 
	 */
	public String sendSleep() throws Exception
	{
		String out = HttpClientUtils.getInstance()
			.getResponseBodyAsString(Configuration.getInstance().getValue("groundlock_url") + SENDSLEEP_URL, getParams());
		output(out);
		return NONE;
	}
	
	
	private String getSecurityKey()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		long now = new Date().getTime();
		String securityKey = MD5.getDefaultInstance().MD5Encode(Configuration.getInstance().getValue("key") + sdf.format(now)) ;
		return securityKey;
	}
	private List<Param>getParams()
	{
		List<Param> params = new ArrayList<Param>();
		params.add(new Param("groundlockid", groundlockid));
		params.add(new Param("data", "20149026"));
		params.add(new Param("skey", "b8922cd1fef6e1c32c54d22d4d81ced9"));
		params.add(new Param("securityKey", getSecurityKey()));
		return params;
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
	public String getGroundlockid() {
		return groundlockid;
	}
	public void setGroundlockid(String groundlockid) {
		this.groundlockid = groundlockid;
	}
	public String[] getIpArr() {
		return ipArr;
	}
	public void setIpArr(String[] ipArr) {
		this.ipArr = ipArr;
	}
	public Integer getProtectEnergy() {
		return protectEnergy;
	}
	public void setProtectEnergy(Integer protectEnergy) {
		this.protectEnergy = protectEnergy;
	}
	public String getBluetoothPassword() {
		return bluetoothPassword;
	}
	public void setBluetoothPassword(String bluetoothPassword) {
		this.bluetoothPassword = bluetoothPassword;
	}
	public String[] getBluetoothMacArr() {
		return bluetoothMacArr;
	}
	public void setBluetoothMacArr(String[] bluetoothMacArr) {
		this.bluetoothMacArr = bluetoothMacArr;
	}
	public String[] getValidMaskArr() {
		return validMaskArr;
	}
	public void setValidMaskArr(String[] validMaskArr) {
		this.validMaskArr = validMaskArr;
	}
	public String[] getTypeMaskArr() {
		return typeMaskArr;
	}
	public void setTypeMaskArr(String[] typeMaskArr) {
		this.typeMaskArr = typeMaskArr;
	}
	public String[] getPhoneNumberArr() {
		return phoneNumberArr;
	}
	public void setPhoneNumberArr(String[] phoneNumberArr) {
		this.phoneNumberArr = phoneNumberArr;
	}
	public int getAutoWeakupTime() {
		return autoWeakupTime;
	}
	public void setAutoWeakupTime(int autoWeakupTime) {
		this.autoWeakupTime = autoWeakupTime;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setAutoWeakupTime(Integer autoWeakupTime) {
		this.autoWeakupTime = autoWeakupTime;
	}
}
