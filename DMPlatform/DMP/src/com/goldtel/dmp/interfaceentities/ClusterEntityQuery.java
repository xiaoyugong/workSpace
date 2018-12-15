/**
 * @Class: ClusterEntityQuery.java
 * @Description:
 * @Author: Zhaoliheng Jun 10, 2015
 * @Version: V1.0
 */

package com.goldtel.dmp.interfaceentities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "file")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClusterEntityQuery extends BaseInterfaceEntity
{
    private static final long serialVersionUID = 67806300100007L;
    
    public static final String PARA_OP = "-op"; //  查询参数聚类中心点的参数名
    public static final String PARA_CN = "-cn"; //  查询参数聚类中心点的参数名
    public static final String PARA_P = "-p"; //  查询参数是否返回记录属性的参数名
    
    protected boolean bIsOnlyCenterPoints = true;   //  是否只查询聚类中心点，默认为真
    /*
     * 如果bIsOnlyCenterPoints为false时，查询聚类中心全匹配设定字符串的类别下的所有记录
     * 如果bIsOnlyCenterPoints为true时，查询聚类中心全匹配设定字符串的聚类中心
     */
    protected String centerName = null;
    protected boolean bHasPara = false;   //  是否返回记录属性，默认为false

    public void setBIsOnlyCenterPoints(Boolean bIsOnlyCenterPoints)
    {
        this.bIsOnlyCenterPoints = bIsOnlyCenterPoints;
    }
    public Boolean getBIsOnlyCenterPoints()
    {
        return this.bIsOnlyCenterPoints;
    }
    
    public void setCenterName(String centerName)
    {
        this.centerName = centerName;
    }
    public String getCenterName()
    {
        return this.centerName;
    }
    
    public void setBHasPara(Boolean bHasPara)
    {
        this.bHasPara = bHasPara;
    }
    public Boolean getBHasPara()
    {
        return this.bHasPara;
    }
}
