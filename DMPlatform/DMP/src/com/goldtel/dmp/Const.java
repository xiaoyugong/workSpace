package com.goldtel.dmp;

/**
 * 存放公共变量
 * @author root
 *
 */
public class Const
{
	//	调用的挖掘算法名称
	public static final String DMNAME_DISTRIBUTEDITEMBASED = "dib";	//	Distributed Item Based算法
	public static final String DMNAME_FREQUENTpATTERN = "fp";	//	Distributed Item Based算法
	public static final String DMNAME_CANNOPY = "canopy";	//	Cannopy算法
	public static final String DMNAME_KMEANS = "kmeans";	//	K-Means算法
	public static final String DMNAME_RANDOMFORESTS = "rf";	//	K-Means算法
	public static final String DMNAME_BAYESIAN = "bayes";	//	K-Means算法

	public static final int iTIMEOUT = 0;//7200;		//	消息超时时长(s)

	public static final String HDFS_IP = "172.18.200.135";		//	集群IP
	public static final String HDFS = "hdfs://172.18.200.135:8020";		//	集群地址
	public static final String HDFS_MYCLU = "hdfs://mycluster";		//	集群地址
	public static final String INPATH = "/input";		//	输入数据地址
	public static final String OUTPATH = "/output";		//	输出数据地址
	public static final String TEMPPATH = "/temp";		//	临时地址
	public static final String SPLIT_PATH = "/";		//	地址分隔符
	public static final String SPLIT_ANALYZE_LG = "{";      //  挖掘算法结果文件分隔符
    public static final String SPLIT_ANALYZE_RG = "}";      //  挖掘算法结果文件分隔符
	public static final String SPLIT_ANALYZE_L = "[";      //  挖掘算法结果文件分隔符
	public static final String SPLIT_ANALYZE_R = "]";      //  挖掘算法结果文件分隔符
	public static final String SPLIT_ANALYZE_LS = "(";      //  挖掘算法结果文件分隔符
    public static final String SPLIT_ANALYZE_RS = ")";      //  挖掘算法结果文件分隔符
	public static final String SPLIT_ANALYZE_S = ",";      //  挖掘算法结果文件分隔符
	public static final String RESULT_KV_SPLIT = "	";     //  挖掘算法结果使用时返回的Key, Value的分隔符
	public static final String RESULT_PATH = "dmresult";   //  挖掘算法结果存放路径，仅部分有需求的算法用
	public static final String SPLIT_DM_COND = ":";       //  数据挖掘参数分隔符
	public static final String NAME_CLUSTER_PRE = "VL-";       //  数据中心名称前缀
	public static final String NAME_MODEL_ID = "MODEL_ID";       //  分类算法模型名称

	public static final String RESULT_CLUSTER = "result.txt";       //  聚类算法解析结果
	public static final String RESULT_CLUSTER_CENTER = "result-center.txt";       //  聚类算法解析结果，只包含聚类中心

	public static final String LOCAL_OUTPATH = "/usr/Mahout/output";		//	本地输出数据地址

	public static final String RESULT_ERROR = "错误: ";	//	挖掘结果错误提示
	public static final String ERROR_NOMETHOD = "错误, 没有该算法: ";	//	挖掘结果错误提示
	public static final String RESULT_CLASSIFY_OK = " 分类已完成";  //  挖掘结果错误提示
}
