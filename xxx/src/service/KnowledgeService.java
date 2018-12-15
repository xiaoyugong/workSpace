package service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import model.Knowledge;
import util.hadoop.HadoopUtils;
import dao.KnowledgeDao;
import dao.impl.KnowledgeDaoImpl;

@Component("knowledgeService")
public class KnowledgeService {

	private KnowledgeDao knowledgeDao;
	
	public KnowledgeService()
	{
		if (null == knowledgeDao)
		{
			knowledgeDao = new KnowledgeDaoImpl();
		}
	}
	public Boolean readFpWriteToDB(){
		try {
			List<Knowledge> list= HadoopUtils.getKnowledgeFromFp();
			for(Knowledge l:list){
				System.out.println(l.getBlogId()+":"+l.getRelatedBlog()+":"+l.getRelatedVotes());
			}
			knowledgeDao.batchInsertKnowledge(list);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}	
	}

	public KnowledgeDao getKnowledgeDao() {
		return knowledgeDao;
	}
	@Resource(name="knowledgeDao")
	public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
		this.knowledgeDao = knowledgeDao;
	}
	
	
}
