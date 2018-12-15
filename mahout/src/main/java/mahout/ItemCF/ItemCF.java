package mahout.ItemCF;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.cf.taste.hadoop.item.RecommenderJob;
public class ItemCF {
	private static final String HDFS = "hdfs://172.18.200.135:8020";

    public static void main(String[] args) throws Exception {

        String inPath = HDFS + "/input/item";
        String outPath = HDFS + "/outOFgxy/outputCF";
        String tmpPath = HDFS + "/tmp/" + System.currentTimeMillis();

        String[] arg=new String[]{
                "-i",inPath,
                "-o",outPath,  
                "-n","3","-b","false","-s","SIMILARITY_EUCLIDEAN_DISTANCE",  
                "--maxPrefsPerUser","7","--minPrefsPerUser","2",  
                "--maxPrefsInItemSimilarity","7",  
                "--tempDir",tmpPath};  

        Configuration conf=new Configuration();
        RecommenderJob job=new RecommenderJob();
        ToolRunner.run(conf, job, arg);
    }
}

