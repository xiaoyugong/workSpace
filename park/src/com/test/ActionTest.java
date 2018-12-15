package com.test;


import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.parkbobo.model.BerthOrder;
import com.parkbobo.model.CarparkNavigationPoint;
import com.parkbobo.model.CarparkOrder;
import com.parkbobo.port.hander.ActionHandler;
import com.parkbobo.service.BerthOrderService;
import com.parkbobo.service.CarparkOrderService;
import com.parkbobo.service.UsersService;
import com.sun.jndi.toolkit.url.Uri;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class ActionTest {

	
	ApplicationContext aContext = null;
	@Before
	public void setUp() throws Exception {
		aContext=	new ClassPathXmlApplicationContext("beans.xml");
	}
	
	@Test
	public void test() {
//		CarparkOrderService carparkOrderService = aContext.getBean(CarparkOrderService.class);
//		String hql="from CarparkOrder where isDel=0 and initiativeOrder=0";
//		List<CarparkOrder> list = carparkOrderService.loadOrder(hql);
		UsersService service = aContext.getBean(UsersService.class);
		service.getUserById("1qq");
	}
	
	@Test
	public void testCarParkPoint(){
		CarparkNavigationPoint carparkNavigationPoint = new CarparkNavigationPoint();
		carparkNavigationPoint.setCarparkid(Long.valueOf(2));
		carparkNavigationPoint.setDescription("哦哦");
		carparkNavigationPoint.setDistance(1);
		carparkNavigationPoint.setStartFloorid(Long.valueOf(1));
		carparkNavigationPoint.setEndFloorid(Long.valueOf(1));
		carparkNavigationPoint.setMemo("妮妮");
		carparkNavigationPoint.setRoadType(2);
	}
	
	@Test
	public void testcarparkOrder(){
		CarparkOrderService carparkOrderService = aContext.getBean(CarparkOrderService.class);
		CarparkOrder order = new CarparkOrder();
		Long ruchang = getTimeLong("2015-01-02 11:13:00");
		Long paytime = getTimeLong("2015-01-02 11:15:00");
		Long leavetime=getTimeLong("2015-01-02 11:26:00");
		order.setOrderid(ActionHandler.generateOrderId());
		order.setBeforeMins(30);
		order.setBeforePrice(500l);
		order.setAfterMins(5);
		order.setAfterPrice(100l);
		order.setEntertime(ruchang);
		order.setPayTime(paytime);
		order.setLeavetime(leavetime);
		order.setStopPayMoney(200l);
		order.setCarNumber("a123456");
		order.setCarparkname("阳光春天");
		order.setInitiativeOrder((short)0);
		order.setCarparkid(1l);
		order.setUserid("e17183b8f95f4b16bdd47fba9c382743");
		order.setTelephone("13228107007");
		order.setTempCardNumber("11112222334");
		order.setPosttime(System.currentTimeMillis());
		
		carparkOrderService.add(order);
		
	

	}
	public static Long getTimeLong(String s) {
	Timestamp timestamp = Timestamp.valueOf(s);
	Long rcLong = timestamp.getTime();
	return rcLong;
}
	@Test
	public void testBerthOrder() throws ParseException, IOException {
		// TODO Auto-generated method stub
//		BerthOrderService berthOrderService = aContext.getBean(BerthOrderService.class);
//		BerthOrder  order = berthOrderService.getById(283l);
//		berthOrderService.closeOrder(order);
//		System.out.println(order);
			// TODO Auto-generated catch block
	}
	
	
	@Test
	public void testAnt() {
		// TODO Auto-generated method stub
		Method[] method = ActionTest.class.getDeclaredMethods();
		for(Method m:method){
			Quanxian quanxian = m.getAnnotation(Quanxian.class);
			if(quanxian!=null){
				
			System.out.println(quanxian.name());
			System.out.println(quanxian.model());
			}
			
		}
	}
	
	@Quanxian(model="person",name="update")
	public void name() {
		System.out.println("12345");
	}
}
