package dao.impl;

import java.util.List;

import javax.annotation.Resource;

import model.Knowledge;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import dao.KnowledgeDao;

@Component("knowledgeDao")
public class KnowledgeDaoImpl implements KnowledgeDao {
	
	private HibernateTemplate hibernateTemplate;
	
	public boolean batchInsertKnowledge(List<Knowledge> knowledgeList) {
		HibernateTemplate ht = getHibernateTemplate();
		SessionFactory sf=ht.getSessionFactory();
		Session session =sf.openSession();
        Transaction tx = session.beginTransaction();
        
        try{
            for(int i=0;i<knowledgeList.size();i++){
                session.save((Knowledge)knowledgeList.get(i));
                
                if(i%50==0){
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        }catch(HibernateException   e){
            tx.rollback();
            e.printStackTrace();
            return false;
        }finally{
            session.close();
        }
		return true;
	}

	public boolean insertKnowledge(Knowledge knowledge) {
		Integer id=(Integer)hibernateTemplate.save(knowledge);
		if(id!=null&&id>0){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<Knowledge> getKnowledge(int blogId) {
		List<Knowledge> knowledgeList=(List<Knowledge>) hibernateTemplate.find("from Knowledge k where k.blogId='"+blogId+"'");
		return knowledgeList;
	}

	/**
	 * @return the hibernateTemplate
	 */
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	/**
	 * @param hibernateTemplate the hibernateTemplate to set
	 */
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public boolean clearKnowledge() {
		try{
		//	String sql="truncate table knowledge";
			String sql="delete from Knowledge";
			hibernateTemplate.bulkUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

}