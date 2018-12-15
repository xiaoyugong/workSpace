package DBT.dbt;

import java.util.List;
import java.util.ResourceBundle;

import org.apache.sqoop.client.SqoopClient;
import org.apache.sqoop.model.MConfig;
import org.apache.sqoop.model.MDriverConfig;
import org.apache.sqoop.model.MFromConfig;
import org.apache.sqoop.model.MInput;
import org.apache.sqoop.model.MJob;
import org.apache.sqoop.model.MLink;
import org.apache.sqoop.model.MLinkConfig;
import org.apache.sqoop.model.MSubmission;
import org.apache.sqoop.model.MToConfig;
import org.apache.sqoop.submission.counter.Counter;
import org.apache.sqoop.submission.counter.CounterGroup;
import org.apache.sqoop.submission.counter.Counters;
import org.apache.sqoop.validation.Status;

/**
 * 用于在hdfs和mysql之间相互导入数据
 * @author gxy
 * 2015-12-3
 */
public class Test {
	
	private static SqoopClient client = new SqoopClient("http://172.18.200.135:12000/sqoop/");
	
	private long createSqlLink(){
		
		MLink sqlLink = client.createLink(4);
		sqlLink.setName("MySql");
		sqlLink.setCreationUser("gxy");
		
		MLinkConfig mlConf = sqlLink.getConnectorLinkConfig();
		mlConf.getStringInput("linkConfig.connectionString").setValue("jdbc:mysql://172.18.200.135/sqoop");
		mlConf.getStringInput("linkConfig.jdbcDriver").setValue("com.mysql.jdbc.Driver");
		mlConf.getStringInput("linkConfig.username").setValue("root");
		mlConf.getStringInput("linkConfig.password").setValue("123");
		
		Status status = client.saveLink(sqlLink);
		if(status.canProceed()){
			System.out.println("Created MySqlLink with Link Id : " + sqlLink.getPersistenceId());
			return sqlLink.getPersistenceId();
		} else {
			System.out.println("Something went wrong creating the link");
			return 0;
		}
	}
	
	private long createHdfsLink(){

		MLink hdfsLink = client.createLink(3);
		hdfsLink.setName("hdfs");
		hdfsLink.setCreationUser("gxy");
		
		MLinkConfig mlConf = hdfsLink.getConnectorLinkConfig();
		mlConf.getStringInput("linkConfig.uri").setValue("hdfs://hadoop135:8020");
		
		Status status = client.saveLink(hdfsLink);
		if(status.canProceed()){
			System.out.println("Created HdfsLink with Link Id : " + hdfsLink.getPersistenceId());
			return hdfsLink.getPersistenceId();
		} else {
			System.out.println("Something went wrong creating the link");
			return 0;
		}
	}
	
	private long createJobS2H(long fromLinkId,long toLinkId){
		
		MJob S2HJob = client.createJob(fromLinkId, toLinkId);
		S2HJob.setName("mysql to hdfs");
		S2HJob.setCreationUser("gxy");
		
		MFromConfig fromConf = S2HJob.getFromJobConfig();
		fromConf.getStringInput("fromJobConfig.schemaName").setValue("sqoop");
		fromConf.getStringInput("fromJobConfig.tableName").setValue("tohdfs");
		fromConf.getStringInput("fromJobConfig.partitionColumn").setValue("id");
		
		MToConfig toConf = S2HJob.getToJobConfig();
		toConf.getStringInput("toJobConfig.outputDirectory").setValue("/outOFgxy/fromMysql");
		
		MDriverConfig driverConf = S2HJob.getDriverConfig();
		driverConf.getIntegerInput("throttlingConfig.numExtractors").setValue(3);
		
		Status status = client.saveJob(S2HJob);
		if(status.canProceed()) {
		 System.out.println("Created S2HJob with Job Id: "+ S2HJob.getPersistenceId());
		 return S2HJob.getPersistenceId();
		} else {
		 System.out.println("Something went wrong creating the job");
		 return 0;
		}
	}
	
	private long createJobH2S(long fromLinkId,long toLinkId){
		
		MJob H2SJob = client.createJob(fromLinkId, toLinkId);
		H2SJob.setName("hdfs to mysql");
		H2SJob.setCreationUser("gxy");
		
		MFromConfig fromConf = H2SJob.getFromJobConfig();
		fromConf.getStringInput("fromJobConfig.inputDirectory").setValue("/outOFgxy/tomysql.txt");
		
		MToConfig toConf = H2SJob.getToJobConfig();
		toConf.getStringInput("toJobConfig.schemaName").setValue("sqoop");
		toConf.getStringInput("toJobConfig.tableName").setValue("fromhdfs");
		
		Status status = client.saveJob(H2SJob);
		if(status.canProceed()) {
		 System.out.println("Created H2SJob with Job Id: "+ H2SJob.getPersistenceId());
		 return H2SJob.getPersistenceId();
		} else {
		 System.out.println("Something went wrong creating the job");
		 return 0;
		}
	}
	
	private void startJob(long jobId) {

		MSubmission submission = client.startJob(jobId);

		System.out.println("Job Submission Status : " + submission.getStatus());
		System.out.println("Hadoop job id :" + submission.getExternalJobId());
		System.out.println("Job link : " + submission.getExternalLink());
		if(submission.getStatus().isRunning()&& submission.getProgress() != -1) {
			System.out.println("Progress : "
					+ String.format("%.2f %%", submission.getProgress() * 100));
		}
		
		Counters counters = submission.getCounters();
		if (counters != null) {
			System.out.println("Counters:");
			for (CounterGroup group : counters) {
				System.out.print("\t");
				System.out.println(group.getName());
				for (Counter counter : group) {
					System.out.print("\t\t");
					System.out.print(counter.getName());
					System.out.print(": ");
					System.out.println(counter.getValue());
				}
			}
		}
		if (submission.getError() != null) {
			System.out.println("Exception info : "
					+ submission.getError().getErrorDetails());
		}
		
		MSubmission submissions = client.getJobStatus(jobId);
		if(submissions.getStatus().isRunning() && submissions.getProgress() != -1) {
		  System.out.println("Progress : " + String.format("%.2f %%", submissions.getProgress() * 100));
		}
	}
	
	public void displayConnector(long connectorId){
		
		describe(client.getConnector(connectorId).getLinkConfig().getConfigs(), client.getConnectorConfigBundle(connectorId));
		// from job config for connector
		describe(client.getConnector(connectorId).getFromConfig().getConfigs(), client.getConnectorConfigBundle(connectorId));
		// to job config for the connector
		describe(client.getConnector(connectorId).getToConfig().getConfigs(), client.getConnectorConfigBundle(connectorId));
	}
	
	public void describe(List<MConfig> configs, ResourceBundle resource) {
		  for (MConfig config : configs) {
		    System.out.println(resource.getString(config.getLabelKey())+":");
		    List<MInput<?>> inputs = config.getInputs();
		    for (MInput input : inputs) {
		      System.out.println(resource.getString(input.getLabelKey()) + " : " + input.getValue());
		    }
		    System.out.println();
		  }
		}
	
	
	public static void main(String[] args) {
		
		//System.setProperty("hadoop.home.dir", "E://hadoop-2.6.0");
		
		Test test = new Test();
		//long fromLinkId = test.createSqlLink();
		//long toLinkId = test.createHdfsLink();
		//long jobId = test.createJobS2H(fromLinkId, toLinkId); 
		//long jobId = test.createJobH2S(31,30);
		test.startJob(13);
		test.displayConnector(4);
	}
}
