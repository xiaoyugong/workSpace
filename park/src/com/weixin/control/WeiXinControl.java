package com.weixin.control;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;

import org.springframework.stereotype.Component;

import com.parkbobo.model.Users;
import com.parkbobo.service.UsersService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.MD5;
import com.parkbobo.utils.WebUtils;
import com.weixin.msg.BaseMsg;
import com.weixin.msg.EventMsg;
import com.weixin.msg.TextMsg;
import com.weixin.returnMsg.NewsArticle;
import com.weixin.returnMsg.ReturnBaseMsg;
import com.weixin.returnMsg.ReturnNewsMsg;
import com.weixin.returnMsg.ReturnTextMsg;
import com.weixin.util.XMLHandler;

@Component("weiXinControl")
public class WeiXinControl {

	@Resource(name="usersService")
	private UsersService usersService;
	
	public ReturnBaseMsg control(BaseMsg msg) {
		
		//事件推送的处理
		if (msg.getMsgType().equals("event")) {
			EventMsg event = (EventMsg) msg;
			System.out.println("推送的事件为:"+event.getEvent());
			return handlerEvent(event);
			
			
			
		}
		//文本信息的处理
		if (msg.getMsgType().equals("text")) {
			String msgval=((TextMsg)msg).getContent();
			//处理攻略指令
			if(msgval.toLowerCase().startsWith("gl")){
				return returnNewsMsgCall(msg,"停车场攻略","暂时没有找到“"+msgval.substring(2)+"”的停车攻略!","www.parkbobo.com");
			}
			return returnNewsMsgCall(msg,"目前不能处理","你输入的内容目前不能处理!","www.parkbobo.com");
		}
		return null;
	}
	
	/**
	 * 处理事件推送
	 * @param eventMsg
	 * @return
	 */
	public ReturnBaseMsg  handlerEvent(EventMsg eventMsg){
		String e = eventMsg.getEvent();
		
		if(e.equals("subscribe")){
			return returnTextMsgCall(eventMsg,"感谢您关注泊泊停车!");
		}
		if(e.equals("unsubscribe")){
			System.out.println("用户取消关注泊泊停车");
		}
		if(e.equals("SCAN")){
			System.out.println("扫描的参数："+eventMsg.getEventKey());
				}
		if(e.equals("LOCATION")){
			
		}
		if(e.equals("CLICK")){
			//未登录拦截
			if(!filter(eventMsg)){
				String content="尊敬的用户,你还未绑定泊泊停车!点击此处前去绑定";
				System.out.println(content);
				return returnNewsMsgCall(eventMsg,"未绑定泊泊停车",content,Configuration.getInstance().getValue("deployUrl")
						+"/weiXinBind_bind?type=to&user.openId="+eventMsg.getFromUserName()+"");
			};
			return eventMenuCall(eventMsg);
		}
		if(e.equals("VIEW")){
			
		}

		return null;
		
	}
	
	public  BaseMsg getMsg(InputStream in){
		
		try {
			Map<String, String> mapp = XMLHandler.parseXML(in);
			String msgtype = mapp.get("MsgType");
			Class class1 = Class.forName("com.weixin.msg."+msgtype.substring(0, 1).toUpperCase()+msgtype.substring(1)+"Msg");
			BaseMsg baseMsg = (BaseMsg) class1.newInstance();
			XMLHandler.getMsg(mapp, baseMsg);
			System.out.println(baseMsg);
			return baseMsg;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 菜单执行
	 * @param event
	 */
	public ReturnBaseMsg eventMenuCall(EventMsg event) {
		String key = event.getEventKey();
		
		//停车场攻略
		if(key.equals("tccgl")){
			String content = "请输入gl+所在城市\n例如:gl成都市";
			return returnNewsMsgCall(event,"停车场攻略",content.toString(),"www.parkbobo.com");
		}
		//我的账户
		if(key.equals("wdzh")){
			Users user = usersService.getUserByOpenId(event.getFromUserName());
			StringBuffer content=new StringBuffer();
			content.append("用户名:"+user.getUsername()+"\n");
			content.append("账户余额:"+user.getBalance()/100+"元\n");
			content.append("账户信誉:"+user.getCredit()+"\n");
			content.append("账户积分:"+user.getPoint()+"\n");
			return returnNewsMsgCall(event,"我的账户",content.toString(),"www.parkbobo.com");
					
		}
		//操作指南
		if(key.equals("czzn")){
			
			String content="我有个中国通的美国朋友，今天换了个QQ签名" +
					"“you don‘t know love far high”。我猜可能他失恋了，" +
					"觉得对方对爱的含义理解不够深。问他到底什么意思？" +
					"神回复：“法海你不懂爱。”";
			return returnTextMsgCall(event,content);
		}
		//常见问题
		if(key.equals("cjwt")){
			String content = "我是救护医生，今天一个病人对我说他只有6个月好活了" +
					"，我想说点鼓励的话，但是最后说的是：" +
					"“六个月，很快就过去了，坚强点！”";
			
			return returnTextMsgCall(event,content);
			
		}
		return null;
	}
	//未绑定的菜单事件过滤
	public boolean filter(EventMsg event) {
		if(event.getMsgType().equals("event")){
			if(event.getEventKey()!=null&&event.getEventKey().equals("wdzh")){
				Users user = usersService.getUserByOpenId(event.getFromUserName());
				if(user==null){
					return false;
				}
			};

		}
		return true;
	}
	
	/**
	 * 对用户输入的处理
	 * 返回textmsg
	 * @return
	 */
	public ReturnBaseMsg returnTextMsgCall(BaseMsg frommsg,String content) {
		ReturnTextMsg msg = new ReturnTextMsg();
		msg.setFromUserName(frommsg.getToUserName());
		msg.setToUserName(frommsg.getFromUserName());
		msg.setMsgId(frommsg.getMsgId());
		msg.setContent(content);
		return msg;
	}
	/**
	 * @param frommsg  
	 * @param title 标题
	 * @param content 内容
	 * @param url
	 * @return
	 */
	public ReturnBaseMsg returnNewsMsgCall(BaseMsg frommsg,String title,String content,String url) {
		ReturnNewsMsg msg= new ReturnNewsMsg();
		msg.setFromUserName(frommsg.getToUserName());
		msg.setToUserName(frommsg.getFromUserName());
		msg.setMsgId(frommsg.getMsgId());
		msg.setArticleCount(1+"");
		NewsArticle article = new NewsArticle();
		article.setTitle(title);
		article.setDescription(content);
		article.setUrl(url);
		msg.getArticles().add(article);
		return msg;
	}
}
