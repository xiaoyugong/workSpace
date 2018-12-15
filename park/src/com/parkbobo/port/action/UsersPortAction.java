package com.parkbobo.port.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;


import com.mysql.jdbc.BalanceStrategy;
import com.opensymphony.xwork2.ActionContext;
import com.parkbobo.manager.action.BaseAction;
import com.parkbobo.manager.model.Manager;
import com.parkbobo.model.Notify;
import com.parkbobo.model.UserBalance;
import com.parkbobo.model.UserCredit;
import com.parkbobo.model.UserPoint;
import com.parkbobo.model.Users;
import com.parkbobo.port.hander.OptTypeEnum;
import com.parkbobo.service.NotifyService;
import com.parkbobo.service.UserBalanceService;
import com.parkbobo.service.UserCreditService;
import com.parkbobo.service.UserPointService;
import com.parkbobo.service.UsersService;
import com.parkbobo.utils.DateUtils;
import com.parkbobo.utils.PageBean;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.RETURN;

@Controller("usersPortAction")
@Scope("prototype")
public class UsersPortAction extends PortBaseAction {
	//终端用户名
	private String username;
	//手机号
	private String telephone;
	
	/**
	 * 车主绑定
	 * @return
	 * @throws IOException
	 */
	public String bindOwnerPort() throws IOException {
		try {
				String hqlString = "from Users where username='"+telephone+"'" +
						" and upper(carNumber)='"+this.carNum.toUpperCase()+"'";
				
				List<Users> users=usersService.getByHql(hqlString);
				if(users.size()>0){
					Users user = users.get(0);
					user.setOwner(username);
					usersService.updateUsers(user);
					log("【"+user.getUsername()+"-"+username+","+carNum+"】",OptTypeEnum.AUTHBIND.getKey());
					return responseSuccessData();
				}
		} catch (IOException e) {
			e.printStackTrace();
			return  responseErrorData("03");
		}
		return responseErrorData("02");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	
	
}
