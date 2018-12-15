package com.weixin.control;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.parkbobo.service.WeiXinConfigService;
import com.weixin.menu.BaseMenu;
import com.weixin.menu.ClickMenu;
import com.weixin.menu.ViewMenu;
import com.weixin.model.Material;
import com.weixin.model.News;
import com.weixin.msg.BaseMsg;
import com.weixin.msg.TextMsg;
import com.weixin.msg.WeiXinUser;
import com.weixin.util.HttpUtil;
import com.weixin.util.JSONHandler;
import com.weixin.util.MenuHandler;
import com.weixin.util.XMLHandler;

@Component("weiXinAPI")
public class WeiXinAPI {

	public final static String APPID="wx9c00d8ef6549ea12";
	public final static String APPSECRET="19794d7f6575bb2fc3f39b04409b13a3";
	public final static String TOKEN="6992005Ych";
	public final static String MATERIAL_IMAGE="image";
	public final static String MATERIAL_THUMB="thumb";
	public final static String MATERIAL_VOICE="voice";
	public final static String MATERIAL_VIDEO="video";
	//图文:ReDRf14I1MEz21gcPLJDE9bCxUoIwxFAv8Jzi9UxyYI
	@Resource(name="weiXinConfigService")
	private WeiXinConfigService weiXinConfigService;
	
	public boolean checkSignature(String timestamp, String nonce,String signature) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		String[] data = new String[]{TOKEN,timestamp,nonce};
		Arrays.sort(data);
		String total = "";
		for(String str : data)
		{
			total = total + str;
		}
		MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
		sha1.update(total.getBytes());
		byte[] codeBytes = sha1.digest();
		String codeString = new BigInteger(1, codeBytes).toString(16);
		if(signature.equals(codeString)){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
//		createMenu();
////		HttpUtil.requestGet("https://api.weixin.qq.com/cgi-bin/user/get?access_token="+access_token+"&next_openid=");
////		HttpUtil.requestGet("https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="+access_token+"");
//		HttpUtil.requestGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET+"");
//		upload(new File("d://banner_bg.jpg"),MATERIAL_THUMB);
//		sendNewsToAll(null);
	}
	
	
	public WeiXinUser getUserByOpenId(String openId){
		
		String userString = HttpUtil.requestGet("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+weiXinConfigService.get(1).getAccess_token()+"&openid="+openId+"&lang=zh_CN");
		return JSONHandler.parseJsonToWeiXinUser(userString);
	}
	
	
	
	/**
	 * 上传图文消息
	 * @param news
	 * @return  资源ID
	 * ReDRf14I1MEz21gcPLJDE6SMnj2Ed8fJUb5dzc2guug
	 */
	public  String uploadNews(News news) {
		String url="https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token="+weiXinConfigService.get(1).getAccess_token();
		String json = JSONHandler.createJSONtoUpdateNews(news);
		System.out.println(json);
		String reString = HttpUtil.requestPost(url,json);
		String media_id =(String)(JSONObject.fromObject(reString).get("media_id"));
			return media_id;
	}
	
	
	public  String createMenu(){
		String url = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+weiXinConfigService.get(1).getAccess_token()+"";
		 String menujson = JSONHandler.parseMenusToJson(MenuHandler.initMenu());
		return HttpUtil.requestPost(url,menujson);
	}
	
	//上传临时素材
	/**
	 * @param file
	 * @param type  媒体文件类型，分别有图片（image）
	 * 、语音（voice）、视频（video）和缩略图（thumb）
	 * @return
	 */
	public   Material upload(File file,String type) {
		Material material = null;
		String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+weiXinConfigService.get(1).getAccess_token()+"&type="+type;
		 String str = HttpUtil.requestPost(url, file);
		 JSONObject ob = JSONObject.fromObject(str);
		 String media = (String) ob.get(type+"_media_id");
		 if(media!=null){
			 material = new Material();
			 material.setType(type);
			 material.setMedia_id(media);
		 }
		 return material;
	}
	
	/**
	 * 永久素材（站不支持视频）
	 * @param file
	 * @param type
	 * @return
	 */
	public   Material addMaterial(File file,String type) {
		Material material = null;
		String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="+weiXinConfigService.get(1).getAccess_token()+"&type="+type;
		 String str = HttpUtil.requestPost(url, file);
		 JSONObject ob = JSONObject.fromObject(str);
		 String media = (String) ob.get("media_id");
		 if(media!=null){
			 material = new Material();
			 material.setType(type);
			 material.setMedia_id(media);
		 }
		 return material;
	}
	
	public   boolean deleteMaterial(String media) {
		Material material = null;
		String url = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token="+weiXinConfigService.get(1).getAccess_token()+"";
		 String str = HttpUtil.requestPost(url, "{\"media_id\":\""+media+"\"}");
		 JSONObject ob = JSONObject.fromObject(str);
		 Integer code = (Integer) ob.get("errcode");
		 if(code==0){
			 return true;
		 }
		 return false;
	}
	
	public  boolean sendNewsToAll(News news) {
//		String urlString="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=nMpclA_HBh5LUWEmCDYoklTofrq99TgDt6ShB82L2aQB3vcAd_tPP29eNVtda0gPj1hesh4YrNfhy6SNmmT8ZlbTWfcYOJiFtZ8ljZ2NFqY";
		String urlString="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="+weiXinConfigService.get(1).getAccess_token();
		String jsonParm = JSONHandler.createSendNewsTojson(news);
		String reString  =HttpUtil.requestPost(urlString, jsonParm);
		JSONObject jsonObject = JSONObject.fromObject(reString);	
		return jsonObject.get("errcode")==null?false:true;
	}
	
}
