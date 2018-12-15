package com.parkbobo.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

@Component
public class ServiceLocator implements BeanFactoryAware{
	private static BeanFactory beanFactory = null;
	private static ServiceLocator servlocator = null;
	@Override
	public void setBeanFactory(BeanFactory factory) throws BeansException {
		// TODO Auto-generated method stub
		 this.beanFactory = factory;
		
	}
	
	public static ServiceLocator getInstance() {
        if (servlocator == null)
              servlocator = (ServiceLocator) beanFactory.getBean("serviceLocator");
        return servlocator;
    }
	 public static Object getService(String servName) {
	        return beanFactory.getBean(servName);
	    }
}
