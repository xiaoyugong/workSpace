/**
 * @Class: ReadClusterWritable.java
 * @Description: 解析Cluster文件，可适合于聚类算法结果
 * @Author: Zhaoliheng May 20, 2015
 * @Version: V1.0
 */
package com.goldtel.dmp.clusterwriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.utils.clustering.ClusterDumper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.Utils;

public class ReadClusterWritable
{
	private static final Logger log = LoggerFactory.getLogger(ReadClusterWritable.class);

	/**
	 * 转换ClusterWritable文件到本地文本文件
	 * @param conf			Configuration
	 * @param seqFileDir	输入的序列化文件路径
	 * @param pointsDir		聚类中心点文件路径
	 * @param outputFile	输出的本地文件，带文件名
	 * @throws Exception
	 */
	public static void readClusterFile(Configuration conf, String seqFileDir, String pointsDir, String outputFile) throws Exception
	{
		log.info("readClusterFile: Begin to convert. seqFileDir = " + seqFileDir + ", pointsDir = " + pointsDir + ", outputFile = " + outputFile);

		String tmpPath = Utils.getTempPath("ReadClusterWritable");
		String[] argmr=new String[]{
				"-i",seqFileDir,					//	输入路径，序列文件
				"-p",pointsDir,
	    		"-o", outputFile + Const.SPLIT_PATH +Const.RESULT_CLUSTER,	//	输出路径
	    		"--tempDir", tmpPath};
	    ClusterDumper clusterDumperc = new ClusterDumper();
	    ToolRunner.run(conf, clusterDumperc, argmr);
	    log.info("readClusterFile: Convert ok. seqFileDir = " + seqFileDir + ", pointsDir = " + pointsDir + ", outputFile = " + outputFile);

	    HadoopUtil.delete(conf, new Path(tmpPath));
	}

	/**
     * 转换ClusterWritable文件的聚类中心到本地文本文件
     * @param conf          Configuration
     * @param seqFileDir    输入的序列化文件路径
     * @param pointsDir     聚类中心点文件路径
     * @param outputFile    输出的本地文件，带文件名
     * @throws Exception
     */
    public static void readClusterFileCenter(Configuration conf, String seqFileDir, String pointsDir, String outputFile) throws Exception
    {
        log.info("readClusterFile: Begin to convert. seqFileDir = " + seqFileDir + ", pointsDir = " + pointsDir + ", outputFile = " + outputFile);

        String tmpPath = Utils.getTempPath("ReadClusterWritable");
        String[] argmr=new String[]{
                "-i",seqFileDir,                    //  输入路径，序列文件
                "-p",pointsDir + "-NoFile",
                "-o", outputFile + Const.SPLIT_PATH +Const.RESULT_CLUSTER_CENTER,   //  输出路径
                "--tempDir", tmpPath};
        ClusterDumper clusterDumperc = new ClusterDumper();
        ToolRunner.run(conf, clusterDumperc, argmr);
        log.info("readClusterFile: Convert ok. seqFileDir = " + seqFileDir + ", pointsDir = " + pointsDir + ", outputFile = " + outputFile);

        HadoopUtil.delete(conf, new Path(tmpPath));
    }
}
