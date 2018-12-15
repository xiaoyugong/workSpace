/**
 * @Class: DMEntityCondition.java
 * @Description:
 * @Author: Zhaoliheng Jun 9, 2015
 * @Version: V1.0
 */

package com.goldtel.dmp.interfaceentities;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "file")
@XmlAccessorType(XmlAccessType.FIELD)
public class DMEntityCondition extends BaseInterfaceEntity
{
    private static final long serialVersionUID = 67806300100006L;

    //  算法参数，可多个算法共用的参数
    //  相似度算法，默认为欧氏距离相似度算法，支持Canopy, K-Means
    public static final String CANOPY_SIMILARY = "-dm";
    //  dm的值，欧氏距离相似度算法，支持Canopy, K-Means
    public static final String CANOPY_SIMI_EUC = "org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure";

    //  Canopy算法参数，以下参数都是可选参数
    public static final String CANOPY_T1 = "-t1";   //  阈值
    public static final String CANOPY_T2 = "-t2";   //  阈值
    public static final String CANOPY_T3 = "-t3";   //  阈值
    public static final String CANOPY_T4 = "-t4";   //  阈值
    public static final String CANOPY_XM = "-xm";   //  计算方式，单机或集群
    public static final String CANOPY_OUTLITER = "--outilerThreshold";   //  异常值阈值

    //  K-Means算法参数，以下参数都是可选参数
    public static final String KMEANS_CD = "-cd";   //  判断推出循环的阈值
    public static final String KMEANS_X = "-x";   //  最大迭代次数
    protected String kMeansCenter = "";   //  聚类中心数据集

    //  Bayes算法参数，以下参数都是可选参数
    public static final String BAYES_RP = "-rp";   //  使用MR模式时，抽取出来作为测试数据的百分比

    protected List<String> dmPara = new LinkedList<String>();   //  算法参数，格式：List<参数名:参数值>
    protected List<String> dmParas = new LinkedList<String>();  //  算法参数，格式：List<参数名:参数值>，如果算法包含了两个挖掘算法，这个是第二个算法的参数
    protected List<String> plPara = new LinkedList<String>();   //  平台设置参数，暂时不用

    public void setDmPara(List<String> dmPara)
    {
        this.dmPara = dmPara;
    }
    public List<String> getDmPara()
    {
        return this.dmPara;
    }

    public void setDmParas(List<String> dmParas)
    {
        this.dmParas = dmParas;
    }
    public List<String> getDmParas()
    {
        return this.dmParas;
    }

    public void setPlPara(List<String> plPara)
    {
        this.plPara = plPara;
    }
    public List<String> getPlPara()
    {
        return this.plPara;
    }

    public void setKMeansCenter(String center)
    {
        this.kMeansCenter = center;
    }
    public String getKMeansCenter()
    {
        return this.kMeansCenter;
    }
}
