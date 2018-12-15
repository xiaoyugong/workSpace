package com.weixin.util;

import java.util.ArrayList;
import java.util.List;


import com.weixin.menu.BaseMenu;
import com.weixin.menu.ClickMenu;
import com.weixin.menu.ViewMenu;

public class MenuHandler {

	public static List initMenu(){
		List list = new ArrayList<BaseMenu>();
		BaseMenu service = new BaseMenu("停车场服务");
		service.getSubMenus().add(new ClickMenu("停车场攻略","tccgl"));
		BaseMenu app = new BaseMenu("App下载");
		app.getSubMenus().add(new ViewMenu("IOS下载","http://itunes.apple.com/us/app/po-po-ting-che/id931373579?l=zh&ls=1&it=8"));
		app.getSubMenus().add(new ViewMenu("安卓下载","http://m.app.so.com/detail/index?pname=com.you007.weibo&id=2274017"));
		BaseMenu person = new BaseMenu("个人中心");
		person.getSubMenus().add(new ClickMenu("我的账户","wdzh"));
		person.getSubMenus().add(new ViewMenu("账户充值","http://www.baidu.com"));
		person.getSubMenus().add(new ClickMenu("操作指南","czzn"));
		person.getSubMenus().add(new ClickMenu("常见问题","cjwt"));
		list.add(service);
		list.add(app);
		list.add(person);
		
		return list;
	}
	
	
	
}
