package com.igetui.util;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.parkbobo.utils.CutHtml;

/**
 * 个推
 * @author LH
 *
 */
public class PushMessageUtil {
	private static PushMessageUtil pushMessageUtil = null; 
	private static final String APPID = "4QyEeEFZRq95oVGxe5EFR8";
	private static final String APPKEY = "rmQHSpVgzQAvFMCBSI0Zg5";
	private static final String MASTERSECRET = "oU5CVplTDeAFazMOfhftJ1";
	private static final String API = "http://sdk.open.api.igexin.com/apiex.htm";
	
	private PushMessageUtil(){}
	public synchronized static PushMessageUtil getDefaultInstance()
	{
		if(pushMessageUtil == null)
		{
			pushMessageUtil = new PushMessageUtil();
		}
		return pushMessageUtil;
	}
	/**
	 * 当前用户是否在线
	 * @param clientid
	 */
	public boolean getUserStatus(String clientid)
	{
		try
		{
			IGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
			IQueryResult result = push.getClientIdStatus(APPID, clientid);
			if(result.getResponse().get("result").equals("Online"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 透传
	 */
	public boolean pushTransmission(String clientid, String title, String content,Long notifyid)
	{
		try
		{
			content="{\"title\":\""+title+"\",\"content\":\""+CutHtml.getDefaultInstance().splitAndFilterString(content, 200, "....")+"\",\"notifyid\":\""+notifyid+"\"}";
			IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
			ListMessage message = new ListMessage();
			TransmissionTemplate template = new TransmissionTemplate();
			template.setAppId(APPID);
			template.setAppkey(APPKEY);
			template.setPushInfo("actionKey", 1, title, "sound");
			template.setTransmissionContent(content);
			template.setTransmissionType(2);
			message.setOffline(false); //用户当前不在线时，是否离线存储。可选
			message.setOfflineExpireTime(24 * 3600 *1000);
			message.setData(template);
			List<Target> targets = new ArrayList<Target>();
			Target target = new Target();
			target.setAppId(APPID);
			target.setClientId(clientid);
			targets.add(target);
			String contentId = push.getContentId(message);
			IPushResult ret = push.pushMessageToList(contentId, targets);
			if(ret.getResponse().get("result").equals("ok"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Exception e) 
		{
			return false;
		}
	}
	
	/**
	 * 透传
	 */
	public  void  pushTransmission(List<String> clientids,String title, String content)
	{
		try {
//			content="{\"title\":\""+title+"\",\"content\":\""+CutHtml.getDefaultInstance().subStringHTML(content, 200, "....")+"\",\"notifyid\":\""+clientid+"\"}";
			//推送主类
			IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
			ListMessage message = new ListMessage();
			TransmissionTemplate template = new TransmissionTemplate();
			template.setAppId(APPID);
			template.setAppkey(APPKEY);
//			template.setPushInfo("actionKey", 1, CutHtml.getDefaultInstance().splitString(content, 200), "sound");
			template.setPushInfo("actionKey", 1, title, "22");
			template.setTransmissionContent(content);
			//收到消是否立即启动应用，1为立即启动，2则广播等待客户端自启动
			template.setTransmissionType(2);
			message.setOffline(true); //用户当前不在线时，是否离线存储。可选
			message.setOfflineExpireTime(24 * 3600 *1000);//离线有效时间，单位为毫秒，可选
			message.setData(template);
			List<Target> targets = new ArrayList<Target>();
			for (String clientid : clientids) {
				Target target = new Target();
				target.setAppId(APPID);
				target.setClientId(clientid);
				targets.add(target);
			}
			String contentId = push.getContentId(message);
			IPushResult ret = push.pushMessageToList(contentId, targets);
			System.out.println("-------"+ret.getResponse().toString());
		} catch (Exception e) {
			System.out.println("-------");
			//e.printStackTrace();
		}
	}
	/**
	 * 点击通知打开网页
	 */
	public void pushLink(List<String> clientids, String title, String url)
	{
		try {
			//推送主类
			IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
			ListMessage message = new ListMessage();
			LinkTemplate template = new LinkTemplate();
			template.setAppId(APPID);
			template.setAppkey(APPKEY);
			template.setTitle(CutHtml.getDefaultInstance().splitString(title, 40));
			template.setText(CutHtml.getDefaultInstance().splitString(title, 600));
			template.setIsRing(true);
			template.setIsVibrate(true);
			template.setIsClearable(true);
			template.setUrl(url);
			message.setOffline(false); //用户当前不在线时，是否离线存储。可选
//			message.setOfflineExpireTime(72 * 3600 * 1000);//离线有效时间，单位为毫秒，可选
			message.setData(template);
			List<Target> targets = new ArrayList<Target>();
			for (String clientid : clientids) {
				Target target = new Target();
				target.setAppId(APPID);
				target.setClientId(clientid);
				targets.add(target);
			}
			String contentId = push.getContentId(message);
			IPushResult ret = push.pushMessageToList(contentId, targets);
			System.out.println(ret.getResponse().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *  点击通知启动应用
	 */
	public void pushNotification(List<String> clientids, String title, String content)
	{
		try {
			IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
			ListMessage message = new ListMessage();
			NotificationTemplate template = new NotificationTemplate();
			template.setAppId(APPID);
			template.setAppkey(APPKEY);
			template.setTitle(CutHtml.getDefaultInstance().splitString(title, 40));
			template.setText(CutHtml.getDefaultInstance().splitString(content, 600));
			template.setIsRing(true);
			template.setIsVibrate(true);
			template.setIsClearable(true);
			template.setTransmissionContent(content);
			message.setOffline(true); //用户当前不在线时，是否离线存储。可选
			message.setOfflineExpireTime(72 * 3600 * 1000);//离线有效时间，单位为毫秒，可选
			message.setData(template);
			List<Target> targets = new ArrayList<Target>();
			for (String clientid : clientids) {
				Target target = new Target();
				target.setAppId(APPID);
				target.setClientId(clientid);
				targets.add(target);
			}
			String contentId = push.getContentId(message);
			IPushResult ret = push.pushMessageToList(contentId, targets);
			System.out.println(ret.getResponse().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
////		IGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
////		
////		IQueryResult result = push.getClientIdStatus(APPID, "69cd357f0627fbaf18b1f36acf488fa0");
////		System.out.println(result.getResponse().get("result"));
////		不在线{result=Offline}
////		在线{result=Online}
		List<String> clientids = new ArrayList<String>();
		clientids.add("50e896377947f2d59f27a4c27f91f96b");
		PushMessageUtil.getDefaultInstance().pushTransmission(clientids, "订单通知", "{\"title\":\"预定通知\",\"content\":\"你在【阳关春天】发布的车位已被车牌号为川Axxx车主预定！\",\"notifyid\":\"5\"}");
	}
}
