package com.parkbobo.port.hander;

import java.util.List;

import net.sf.json.JsonConfig;

import com.parkbobo.model.BerthOrder;
import com.parkbobo.model.CarparkOrder;

public class ActionHandler {

	/**
	 * 车位订单
	 * @param berthOrder
	 * @return
	 */
	public static String getOrderJson(List<BerthOrder> carparkOrders) {
		StringBuffer jsonBuffer  = new StringBuffer();
		if(carparkOrders.size()==0){
			 jsonBuffer = new StringBuffer("{\"status\":\"true\",\"data\":[]}");
			 return jsonBuffer.toString();
		}
		 jsonBuffer = new StringBuffer("{\"status\":\"true\",\"data\":[");
		for(BerthOrder carparkOrder:carparkOrders){
			jsonBuffer.append("{" +
					"\"orderid\":\""+carparkOrder.getBerthorderid()+"\","+
					"\"carNum\":\""+removeNull(carparkOrder.getCarNumber())+"\","+
					"\"telephone\":\""+removeNull(carparkOrder.getTelephone())+"\","+
					"\"posttime\":\""+removeNull(carparkOrder.getPosttime())+"\","+
					"\"isEnter\":\""+(carparkOrder.getIsEnter()==1?"是":"否")+"\""+
					"},");
		}
		if(carparkOrders.size()>0)
			jsonBuffer = jsonBuffer.deleteCharAt(jsonBuffer.length()-1);
		jsonBuffer.append("]}");
		return jsonBuffer.toString();
	}
	
	
	/**
	 * @param berthOrder
	 * @return 网络订单详情
	 */
	public static String berthOrderDetails(BerthOrder berthOrder){
		String json = "{\"status\":\"true\","+
				"\"totalMin\":\""+removeNull(berthOrder.getStopTotalMillisecond()/(1000*60))+"\","+
				"\"overtimeMin\":\""+removeNull((berthOrder.getLeavetime()-berthOrder.getEndMillisecond())/(1000*60))+"\","+
				"\"totalCharge\":\""+removeNull(berthOrder.getStopTotalMoney())+"\","+
				"\"onlineCharge\":\""+removeNull(berthOrder.getStopPayMoney())+"\","+
			"\"payArrearage\",\""+removeNull(berthOrder.getStopOvertimeMoney())+"\"}";

		return json;
	}

	public static Object removeNull(Object o){
		if(o == null) {
			return "";
		}
		return o;
	} 
	
	/**
	 * 临停卡订单列表
	 * @param carparkOrders
	 * @return
	 */
	public static String getTempOrdersDetails(List<CarparkOrder> carparkOrders) {
		StringBuffer jsonBuffer = new StringBuffer();
		if(carparkOrders.size()==0){
			jsonBuffer = new StringBuffer("{\"status\":\"true\",\"data\":[]}");
			return jsonBuffer.toString(); 
		}
		 jsonBuffer = new StringBuffer("{\"status\":\"true\",\"data\":[");
		for(CarparkOrder carparkOrder:carparkOrders){
			jsonBuffer.append("{" +
					"\"orderid\":\""+carparkOrder.getOrderid()+"\","+
					"\"carNum\":\""+removeNull(carparkOrder.getCarNumber())+"\","+
					"\"telephone\":\""+removeNull(carparkOrder.getTelephone())+"\","+
					"\"posttime\":\""+removeNull(carparkOrder.getPosttime())+"\","+
					"\"cardNum\":\""+removeNull(carparkOrder.getCarNumber())+"\","+
					"\"entertime\":\""+removeNull(carparkOrder.getEntertime())+"\","+
					"\"orderStatus\":\""+(carparkOrder.getPayTime()==null?0:1)+"\""+
					
					"},");
		}
		if(carparkOrders.size()>0)
			jsonBuffer = jsonBuffer.deleteCharAt(jsonBuffer.length()-1);
		jsonBuffer.append("]}");
		return jsonBuffer.toString();
	}


	/**
	 * 
	 * @param carparkOrder
	 * @return 临时订单消费详情
	 */
	public static String getTempOrderJson(CarparkOrder carparkOrder) {
		// TODO Auto-generated method stub
		StringBuffer jsonBuffer = new StringBuffer("{\"status\":\"true\",\"data\":[");
			jsonBuffer.append("{" +
					"\"totalTime\":\""+removeNull(carparkOrder.getStopTotalMillisecond()/(1000*60))+"\","+
					"\"carNum\":\""+removeNull(carparkOrder.getCarNumber())+"\","+
					"\"onlineCharge\":\""+removeNull(carparkOrder.getStopPayMoney())+"\","+
					"\"cqCharge\":\""+removeNull(carparkOrder.getStopOvertimeMoney())+"\","+
					"\"totalCharge\":\""+removeNull(carparkOrder.getStopTotalMoney())+"\","+
					"\"cardNumber\":\""+removeNull(carparkOrder.getTempCardNumber())+"\""+
					"}");
		jsonBuffer.append("]}");
		return jsonBuffer.toString();
	}
	
	
	public static String generateOrderId(){
		
		int d1 = (int) (Math.random()*100);
		int d2 = (int) (Math.random()*100);
		String orderid = String.valueOf(d1)+String.valueOf(d2)+System.currentTimeMillis();
		return orderid;
	}
	
	
}
