package com.parkbobo.manager.action;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.opengis.metadata.citation.Telephone;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.model.CarparkDevice;
import com.parkbobo.model.CarparkOrder;
import com.parkbobo.port.hander.ActionHandler;
import com.parkbobo.service.BerthOrderService;
import com.parkbobo.service.CarparkDeviceService;
import com.parkbobo.service.CarparkOrderService;

@Controller("carparkOrderAction")
@Scope("prototype")
public class CarparkOrderAction extends BaseAction{
	@Resource(name="carparkOrderService")
	private CarparkOrderService carparkOrderService;
	@Resource(name="carparkDeviceService")
	private CarparkDeviceService carparkDeviceService;
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
