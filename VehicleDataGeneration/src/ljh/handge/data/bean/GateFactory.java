package ljh.handge.data.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import ljh.handge.data.util.MysqlConnector;

public class GateFactory {
	
	public static Map<String, Gate> gates;
	public static MysqlConnector connPool;
	public static Connection conn;
	
	static {
		gates = new HashMap<String, Gate>();
	}
	
	public GateFactory(){
		connPool = MysqlConnector.getInstance();
		conn = connPool.getConnection();
	}
	
	@Override
	protected void finalize() throws Throwable {
		connPool.releaseConnection(conn);
		super.finalize();
	}
	
	//查询数据库，返回存储所有卡口信息的集合对象
	public Map<String, Gate> findAll(){
		try {
			Statement stat = conn.createStatement();
			String sql = "select * from gate";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()){
				Gate gate  = new Gate();
				String gid = rs.getString("id");
				gate.setGateId(gid);
				gate.setNextGates(rs.getString("agate"));
				gate.setDistances(rs.getString("distance"));
				gate.setLonlat(rs.getString("lonlat"));
				gate.setType(rs.getString("type"));
				gate.setSpeedlimit(rs.getString("speedlimit"));
				gates.put(gid, gate);
			}
			rs.close();
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gates;
	}

	//按字段查询（备用）
	public void findByField(String field){
		try {
			Statement stat = conn.createStatement();
			String sql = "select " + field +" from gate";
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()){
				System.out.print(rs.getString("id") + "\t");
				System.out.print(rs.getString("latlon") + "\t");
				System.out.print(rs.getString("agate") + "\t");
				System.out.println();
			}
			rs.close();
			stat.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
