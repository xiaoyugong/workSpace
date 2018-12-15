package dao;

import java.util.List;
import java.util.Map;

public interface BlogsDao {

	/**
	 *获取blog信息
	 * @return
	 */
	public Map<String,List<String>> getBlogs(String[] blogIds);
}
