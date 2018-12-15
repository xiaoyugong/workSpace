package ljh.handge.data.util;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperConnector {

//	private static String ZK_HOSTS = "Dataflow-1:2181,Dataflow-2:2181,Dataflow-3:2181,Dataflow-4:2181,Dataflow-5:2181";
	private static String ZK_HOSTS = "172.20.31.4:2181,172.20.31.5:2181,172.20.31.6:2181";
	private static int SESSION_TIME_OUT = 10000;

	public static ZooKeeper getZkConnector(){
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(ZK_HOSTS, 
					SESSION_TIME_OUT, new Watcher() { 
				// 监控所有被触发的事件
				public void process(WatchedEvent event) { 
				} 
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zk;
	}
	public static void rmr(ZooKeeper zk, String path){
		//获取路径下的节点
		try {
			if(zk.exists(path, false) == null){
//				System.out.println("Group is not exists. Pass");
			} else {
				List<String> children = zk.getChildren(path, false);
				for (String pathCd : children) {
					//获取父节点下面的子节点路径
					String newPath = "";
					//递归调用,判断是否是根节点
					if (path.equals("/")) {
						newPath = "/" + pathCd;
					} else {
						newPath = path + "/" + pathCd;
					}
					rmr(zk, newPath);
				}
				//删除节点,并过滤zookeeper节点和 /节点
				if (path != null && !path.trim().startsWith("/zookeeper") && !path.trim().equals("/")) {
					zk.delete(path, -1);
				}
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
