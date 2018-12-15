package com.weixin.util;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.context.support.StaticApplicationContext;


import com.weixin.menu.BaseMenu;
import com.weixin.menu.ClickMenu;
import com.weixin.menu.LocationSelectMenu;
import com.weixin.menu.ViewMenu;
import com.weixin.model.News;
import com.weixin.msg.WeiXinUser;

public class JSONHandler {

	/*
	 * String string = "{"+
   "\"subscribe\": 1, "+
   "\"openid\": \"o6_bmjrPTlm6_2sgVt7hMZOPfL2M\", "+
   "\"nickname\": \"Band\", "+
   "\"sex\": 1, "+
   "\"language\": \"zh_CN\", "+
   "\"city\": \"广州\", "+
   "\"province\": \"广东\", "+
   "\"country\": \"中国\", "+
   "\"headimgurl\":\"http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0\","+ 
   "\"subscribe_time\":1382694957,"+
   "\"unionid\": \"o6_bmasdasdsad6_2sgVt7hMZOPfL\"}";
		System.out.println(string);
	 * 
	 */
	public static WeiXinUser parseJsonToWeiXinUser(String json) {
		
		JSONObject  jsonObject = JSONObject.fromObject(json);
		WeiXinUser user = new WeiXinUser();
		 user = (WeiXinUser) jsonObject.toBean(jsonObject,user.getClass());
		
		return user;
	}
	/**
	 * {
     "button":[
     {	
          "type":"click",
          "name":"今日歌曲",
          "key":"V1001_TODAY_MUSIC"
      },
      {
           "name":"菜单",
           "sub_button":[
           {	
               "type":"view",
               "name":"搜索",
               "url":"http://www.soso.com/"
            },
            {
               "type":"view",
               "name":"视频",
               "url":"http://v.qq.com/"
            },
            {
               "type":"click",
               "name":"赞一下我们",
               "key":"V1001_GOOD"
            }]
       }]
 }
	 * @param list
	 * @return
	 */
	
	public static String parseMenusToJson(List<BaseMenu> list) {
		
		StringBuffer sb = new StringBuffer("{\"button\":[");
		for(BaseMenu menu:list){
			sb.append("{\"name\":\""+menu.getName()+"\",\"sub_button\":[");
				for(BaseMenu subMenu:menu.getSubMenus()){
					sb.append("{\"name\":\""+subMenu.getName()+"\",");
					sb.append("\"type\":\""+subMenu.getType()+"\",");
					if(subMenu.getType().equals("view")){
						ViewMenu m = (ViewMenu) subMenu;
						sb.append("\"url\":\""+m.getUrl()+"\",");
					}
					if(subMenu.getType().equals("click")){
						ClickMenu m = (ClickMenu) subMenu;
						sb.append("\"key\":\""+m.getKey()+"\",");
					}
					if(subMenu.getType().equals("location_select")){
						LocationSelectMenu m = (LocationSelectMenu) subMenu;
						sb.append("\"key\":\""+m.getKey()+"\",");
					}
					sb = sb.deleteCharAt(sb.length()-1);
					sb.append("},");
				}
				sb = sb.deleteCharAt(sb.length()-1);
			sb.append("]},");
			
		}
		sb = sb.deleteCharAt(sb.length()-1);
		sb.append("]}");
		return sb.toString();
	}
	public static String createJSONtoUpdateNews(News news) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		
		sb.append("{ \"articles\": [");
		sb.append("	{\"thumb_media_id\":\""+StringUtil.removeNull(news.getThumb_media_id())+"\",");
		sb.append("\"author\":\""+StringUtil.removeNull(news.getAuthor())+"\",");
		sb.append("\"title\":\""+StringUtil.removeNull(news.getTitle())+"\",");
		sb.append("\"content_source_url\":\""+StringUtil.removeNull(news.getContent_source_url())+"\",");
		sb.append("\"content\":\""+StringUtil.removeNull(news.getContent())+"\",");
		sb.append("\"digest\":\""+StringUtil.removeNull(news.getDigest())+"\",");
		sb.append("\"show_cover_pic\":\""+news.getShow_cover_pic()+"\"");
		sb.append("}]}");
		return sb.toString();
	}
	
	
	public static String createSendNewsTojson(News news) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("{");
		
		sb.append("\"filter\":{");
		sb.append("\"is_to_all\":true");
		sb.append("\"group_id\":\"\"");
		sb.append("},");
		
//		sb.append("\"touser\":[");
//sb.append("\"oJoyMjmobjC3pJbwf5Jw6kUtW6Jo\"");
//				sb.append("],");
				
		sb.append("\"mpnews\":{");
		sb.append("\"media_id\":\""+news.getMedia_id()+"\"");
		sb.append("},");
		sb.append("\"msgtype\":\"mpnews\"");
		sb.append("}");
		System.out.println(sb.toString());
		return sb.toString();
		
	}
}
