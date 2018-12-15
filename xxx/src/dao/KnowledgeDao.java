package dao;

import java.util.List;

import model.Knowledge;

public interface KnowledgeDao {
	/**
	 * 批量插入知识到知识库
	 * @param knowledgeList
	 * @return
	 */
	public boolean batchInsertKnowledge(List<Knowledge> knowledgeList);
	/**
	 * 插入单个知识到知识库
	 * @param knowledge
	 * @return
	 */
	public boolean insertKnowledge(Knowledge knowledge);
	/**
	 * 获得用户推荐博客列表
	 * @param username
	 * @return
	 */
	public List<Knowledge> getKnowledge(int blogId);
	/**
	 * 清空表
	 * @return
	 */
	public boolean clearKnowledge();
}
