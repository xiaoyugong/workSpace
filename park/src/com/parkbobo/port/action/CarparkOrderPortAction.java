package com.parkbobo.port.action;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.opengis.metadata.citation.Telephone;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.manager.action.BaseAction;
import com.parkbobo.model.BerthOrder;
import com.parkbobo.model.CarparkDevice;
import com.parkbobo.model.CarparkOrder;
import com.parkbobo.port.hander.ActionHandler;
import com.parkbobo.port.hander.OptTypeEnum;
import com.parkbobo.service.BerthOrderService;
import com.parkbobo.service.CarparkDeviceService;
import com.parkbobo.service.CarparkOrderService;

/**
 * 作者:卢鸶
 * 时间:2015-2-3
 * 描述:停车场订单接口
 */
@Controller("carparkOrderPortAction")
@Scope("prototype")
public class CarparkOrderPortAction extends PortBaseAction{
	
	private String orderid;
	private String enterTime;
	/**
	 * 
	 * 网络订单列表 
	 * @throws IOException 
	 * @throws IOException
	 */
	public String loadOrderPort() throws IOException{
		String json = "{}";
		try {
				String hql="from BerthOrder where isDel=0  and berthorderid>"+Integer.parseInt(orderid)+" and berthShare.carparkid="+parkid;
				List<BerthOrder> list = berthOrderService.getByHql(hql);
				json = ActionHandler.getOrderJson(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  responseErrorData("04");
		}
		return responseSuccessData(json); 
	}

	

	/**
	 * 网络订单车主入场
	 * @return
	 * @throws IOException 
	 */
	public String authEnterPort() throws IOException{
		try {
			BerthOrder berthOrder = berthOrderService.getById(Long.parseLong(orderid));
			if(berthOrder!=null){
				
			if(berthOrder.getStatus()==1){
				return responseErrorData("05");
			}
			if(berthOrder.getIsEnter()==1){
				return responseErrorData("11");
			}
			berthOrder.setIsEnter((short)1);
			berthOrder.setEntertime(new Date().getTime());
			berthOrderService.update(berthOrder);
			log("orderid:"+orderid+",parkid"+parkid,OptTypeEnum.AUTHENTER.getKey());
			return responseSuccessData();
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return responseErrorData("06");
	}
	/**
	 * 网络订单车主出场
	 * @return 订单详情
	 * @throws IOException 
	 */
	public String authLeavePort() throws IOException{
		try {
			//停车场只能获取自己的订单
			List<BerthOrder> orders = berthOrderService.getByHql("from BerthOrder where berthorderid="+orderid+" and berthShare.carparkid="+parkid);
			BerthOrder berthOrder = orders.get(0);
			if(berthOrder!=null){
				if(berthOrder.getIsLeave()!=1){
					berthOrder.setIsLeave((short)1);
					berthOrder.setLeavetime(new Date().getTime());
					berthOrder.setClosetime(new Date().getTime() );
					berthOrder.setStatus((short)1);
					log("orderid:"+orderid+",parkid"+parkid,OptTypeEnum.AUTHLEAVE.getKey());
				}
				berthOrder= berthOrderService.closeOrder(berthOrder);
				String jSONString = ActionHandler.berthOrderDetails(berthOrder);
				return responseSuccessData(jSONString);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return responseErrorData("06");
	}

	
	/**
	 * 临停卡订单列表
	 * @return
	 * @throws IOException
	 */
	public String loadTempOrderPort() throws IOException{
		String json = "{}";
		try {
				String hql="from CarparkOrder where isDel=0 and initiativeOrder=0 and isLeave!=1 and carparkid="+parkid;
				List<CarparkOrder> list = carparkOrderService.getOrder(hql);
				json = ActionHandler.getTempOrdersDetails(list);
				System.out.println(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  responseErrorData("04");
		}
		return responseSuccessData(json); 
	}
	
	/**
	 * 临停订单入场时间更新
	 * @return
	 * @throws IOException
	 */
	public String updateTempEnterDatePort() throws IOException{
		try{
			String[] orders = enterTime.split("~");
			for (int i = 0; i < orders.length; i++) {
				System.out.println(orders[i]);
				String[] enter_time = orders[i].split("-");
				if(enter_time.length==2){
					String orderid = enter_time[0];
					String time = enter_time[1];
					CarparkOrder order = carparkOrderService.get(orderid);
					if(order!=null&&order.getIsEnter()!=1){
						order.setEntertime(Long.parseLong(time));
						order.setIsEnter((short)1);
						carparkOrderService.update(order);
						
					}
				}
			}
			return responseSuccessData();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return responseErrorData("08");
	}
	
	/**
	 * 临时订单出场
	 * @return 订单详情
	 * @throws IOException 
	 */
	public String tempOrderDetailsPort() throws IOException{
		try {
			
			CarparkOrder carparkOrder = carparkOrderService.get(orderid);
			if(carparkOrder.getEntertime()==null){
				return responseErrorData("10");
			}
			if(carparkOrder.getIsLeave()==1){
				carparkOrder.setIsLeave((short)1);
				carparkOrder.setLeavetime(new Date().getTime());
				carparkOrder.setInitiativeOrder((short)1);
			}
			//计算付费详情
			carparkOrder = carparkOrderService.updateConsumeDetail(carparkOrder);
			String jsonString = ActionHandler.getTempOrderJson(carparkOrder);
			log("orderid:"+orderid+",parkid"+parkid,OptTypeEnum.AUTHLEAVE.getKey());
			return responseSuccessData(jsonString);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return responseErrorData("09");
	}
	public String getOrderid() {
		return orderid;
	}



	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}
	
	


	

}
