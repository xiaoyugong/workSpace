package com.chuandge.dmp.classifier;

/**
 * 存放公共变量
 * @author root
 *
 */
public class ClassifyConst
{
	//	调用的挖掘算法名称
	public static final String DMNAME_DISTRIBUTEDITEMBASED = "dib";	//	Distributed Item Based算法
	public static final String DMNAME_FREQUENTpATTERN = "fp";	//	Distributed Item Based算法
	public static final String DMNAME_CANNOPY = "canopy";	//	Cannopy算法
	public static final String DMNAME_KMEANS = "kmeans";	//	K-Means算法
	public static final String DMNAME_RANDOMFORESTS = "rf";	//	K-Means算法
	public static final String DMNAME_BAYESIAN = "bayes";	//	K-Means算法

	public static final String HDFS_MYCLU = "hdfs://mycluster";		//	集群地址
	public static final String INPATH = "/input";		//	输入数据地址
	public static final String OUTPATH = "/output";		//	输出数据地址
	public static final String LIBPATH = "/opt/DMPJob";		//	包地址
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
	public static final String NAME_MODEL_ID = "MODEL_ID";       //  分类算法模型名称
}
