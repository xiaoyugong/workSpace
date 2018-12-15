/**
 * FP算法数据使用接口参数
 * @Class: FPEntityQuery.java
 * @Description:
 * @Author: Zhaoliheng Jun 5, 2015
 * @Version: V1.0
 */

package com.goldtel.dmp.interfaceentities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "file")
@XmlAccessorType(XmlAccessType.FIELD)
public class FPEntityQuery extends BaseInterfaceEntity
{
    private static final long serialVersionUID = 67806300100005L;

    public static final int ALL = 0;        //  返回全部关联信息，此时numRelation不起作用
    public static final int MAX = 1;        //  从关联度最大的开始返回
    public static final int MIN = 2;        //  从关联度最小的开始返回

    protected int numRelation = -1;         //  每条记录返回的关联组个数
    protected String feature = null;        //  记录特征（选取记录的Key的包含了指定字符串的记录）
    protected int type = 0;                 //  返回记录方式， ALL|MAX|MIN
    protected int numRecorders = -1;        //  返回记录条数

    public void setNumRelation(Integer numRelation)
    {
        this.numRelation = numRelation;
    }
    public Integer getNumRelation()
    {
        return this.numRelation;
    }

    public void setFeature(String feature)
    {
        this.feature = feature;
    }
    public String getFeature()
    {
        return this.feature;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }
    public Integer getType()
    {
        return this.type;
    }
    
    public void setNumRecorders(Integer numRecorders)
    {
        this.numRecorders = numRecorders;
    }
    public Integer getNumRecorders()
    {
        return this.numRecorders;
    }
}
