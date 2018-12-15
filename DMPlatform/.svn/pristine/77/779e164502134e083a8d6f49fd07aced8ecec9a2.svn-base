/**
 * 协同分布算法数据使用接口参数
 * @Class: DIBQueryEntity.java
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
public class DIBEntityQuery extends BaseInterfaceEntity
{
    private static final long serialVersionUID = 67806300100001L;

    protected int numRecommendItems = -1;        //  每条记录推荐的物品个数
    protected String userFeature = null;        //  用户特征（包含指定名字的用户）
    protected int userStart = -1;                //  起始用户号（从0开始）
    protected int userStop = -1;                //  结束用户号（包含该下标的用户）
    
    public void setNumRecommendItems(Integer numRecommendItems)
    {
        this.numRecommendItems = numRecommendItems;
    }
    public Integer getNumRecommendItems()
    {
        return this.numRecommendItems;
    }

    public void setUserFeature(String userFeature)
    {
        this.userFeature = userFeature;
    }
    public String getUserFeature()
    {
        return this.userFeature;
    }
    
    public void setUserStart(Integer userStart)
    {
        this.userStart = userStart;
    }
    public Integer getUserStart()
    {
        return this.userStart;
    }

    public void setUserStop(Integer userStop)
    {
        this.userStop = userStop;
    }
    public Integer getUserStop()
    {
        return this.userStop;
    }
}
