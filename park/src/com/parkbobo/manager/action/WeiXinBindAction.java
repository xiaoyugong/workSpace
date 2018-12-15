package com.parkbobo.manager.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.model.Users;
import com.parkbobo.service.UsersService;
import com.parkbobo.utils.Configuration;
import com.parkbobo.utils.MD5;

@Controller("weiXinBindAction")
@Scope("prototype")
public class WeiXinBindAction extends BaseAction{

	/**
	 * 
	 */
	@Resource(name="usersService")
	private UsersService usersService;
	private Users user;
	private String type;
	
	public String result() {
		
		return "result";
	}
	
	public String bind() {
		if(type!=null&&type.equals("to")){
			return "bind";
		}
		Users u = usersService.findByUsername(user.getUsername());
		if(MD5.getDefaultInstance().MD5Encode(user.getPassword() + u.getUsername() + Configuration.getInstance().getValue("key")).equals(u.getPassword())){
			u.setOpenId(this.user.getOpenId());
			usersService.updateUsers(u);
			return forward("/weiXinBind_result");
		}
			
		request.setAttribute("msg", "用户名或密码错误!");
		return "bind";
	}
	
	
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return "";
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	
	
}
