package com.parkbobo.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * @author lusi
 * long类型时间转换标签
 *
 */
public class LongConvertDateTag extends TagSupport {
	
	private Object datelong;
	
	
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		 JspWriter out = this.pageContext.getOut();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateString = "";
		if(datelong != null)
		{
			dateString =  sdf.format(new Date(Long.parseLong(datelong.toString())));
		}
		
		try {
			out.print(dateString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}


	public Object getDatelong() {
		return datelong;
	}


	public void setDatelong(Object datelong) {
		
	     try {
	    	 if(datelong!=null)
			this.datelong = (Object) ExpressionEvaluatorManager.evaluate("datelong", datelong.toString(), Object.class, this, pageContext);
	    	 else{
	    		 this.datelong=datelong;
	    	 }
	     } catch (JspException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	
	
	
	
}
