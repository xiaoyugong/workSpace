package util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtil {
	private static ClassPathXmlApplicationContext cxt=new ClassPathXmlApplicationContext("applicationContext.xml");
	
	public static Object getBean(String beanName){
		try {
			return cxt.getBean(beanName);
		} catch (BeansException e) {
			e.printStackTrace();
		}finally{
		//	close();
		}
		return null;
	}
	
	public static void close(){
		if(cxt!=null){
			cxt.destroy();
		}
	}
	
	private static ApplicationContext CONTEXT;

	/*需要单独执行main方法且需要spring支持时，先调用此方法*/
	public static synchronized void springWithoutWeb() {
		if (CONTEXT != null){
			return;
		}
		CONTEXT = new ClassPathXmlApplicationContext("classpath*:**/*applicationContext*.xml");
	}
}
	