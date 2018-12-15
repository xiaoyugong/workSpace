package com.parkbobo.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.json.JSONWriter;
import org.springframework.stereotype.Service;

import com.parkbobo.dao.CarparkOrderDao;
import com.parkbobo.model.CarparkOrder;

@Service("carparkOrderService")
public class CarparkOrderService {

	@Resource(name="carparkOrderDaoImpl")
	private CarparkOrderDao carparkOrderDao;

	public List getOrder(String hql) {
		// TODO Auto-generated method stub
		
		return carparkOrderDao.getByHQL(hql);
	}

	public CarparkOrder get(String orderid) {
		// TODO Auto-generated method stub
		return carparkOrderDao.getById(orderid);
	}

	public void update(CarparkOrder order) {
		// TODO Auto-generated method stub
		
		carparkOrderDao.update(order);
	}
	
	
	/**
	 * 计算消费明细
	 * @param order
	 * @return
	 */
	public  CarparkOrder updateConsumeDetail(CarparkOrder order){
		//初始值=首停价格
		long price = order.getBeforePrice();
		/*计算停车总时间start*/
		//停车时间，不足一分钟算做1分,
		Long stopTotalMillisecond =order.getLeavetime()-order.getEntertime();//
		Integer stopTime = (int) (stopTotalMillisecond/(60*1000));//单位分
		if((order.getEntertime()-order.getLeavetime())%(60*1000)!=0){
			stopTime =stopTime+1;
		}
		order.setStopTotalMillisecond(stopTotalMillisecond);
		//如果超过首停分钟则减去首停分钟因为前面初始值已经加了首停价格
		if(stopTime>order.getBeforeMins()){
			stopTime-=order.getBeforeMins();//停车总时间除首停分钟/分
		}
		/*end*/
//		Integer cqmins = 0;
		
//		/*超期停车费start  结束订单后10分钟类未驶出停车场则视为超期*/
//		if((order.getLeavetime()-order.getPayTime())>10*1000*60){
//			cqmins = (int)(order.getLeavetime()-order.getPayTime())/(60*1000);
//		}
//		int cqAfterMinsc=(int)(cqmins%order.getAfterMins()==0?cqmins/order.getAfterMins():(cqmins%order.getAfterMins()+1));
//		order.setStopOvertimeMoney(cqAfterMinsc*order.getAfterPrice());
//		/*end*/
		
		/*停车总付费start*/
		if(stopTime>order.getBeforeMins()){
			Integer aftermin = stopTime/order.getAfterMins();
			price+=(stopTime%order.getAfterMins()==0?aftermin:aftermin+1)*order.getAfterPrice();
		}
		order.setStopTotalMoney(price);
		/*end*/
		if(order.getPayTime()==null){
			order.setStopOvertimeMoney(price);
			return order;
		}
		//十分钟内未出场收取超期费
		if((order.getLeavetime()-order.getPayTime())>10*60*1000)
			order.setStopOvertimeMoney(price-order.getStopPayMoney());
		else {
			order.setStopOvertimeMoney(0l);
		}
		carparkOrderDao.update(order);
		return order;
	}

	public void add(CarparkOrder order) {
		// TODO Auto-generated method stub
		carparkOrderDao.add(order);
	}
	

}
