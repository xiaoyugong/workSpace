package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.GlobalEntity;

import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import util.spring.SpringUtil;

@Component("globalUtil")
@Scope("prototype")
public class GlobalUtil {
	public  static Map<String,String> globalMap;
	
	static{
		initial();
	}
	
	public GlobalUtil(){
		System.out.println("initial GlobalUtil");
	}
	
	public static String getValue(String key){
		if(globalMap==null||globalMap.size()<=0){
			initial();
		}
		return globalMap.get(key);
	}

	
	@SuppressWarnings("unchecked")
	private static void initial(){
		globalMap=new HashMap<String,String>();
		HibernateTemplate hibernateTemplate=(HibernateTemplate) SpringUtil.getBean("hibernateTemplate");
		List<GlobalEntity> globalEntities= (List<GlobalEntity>) hibernateTemplate.find("from GlobalEntity");
		for(GlobalEntity globalEntity:globalEntities){
			globalMap.put(globalEntity.getKey(), globalEntity.getValue());
		}
	} 
}
