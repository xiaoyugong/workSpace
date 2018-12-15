/**
 * @Class: RFQueryResponse.java
 * @Description:
 * @Author: Zhaoliheng Jun 10, 2015
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
public class RFQueryResponse extends BaseInterfaceEntity
{
    private static final long serialVersionUID = 67806300100010L;

    //  选中的结果，格式为：List<类别名称:新数据1    新数据2    ...>，数据之间以Tab分开
    protected List<String> recommand = new LinkedList<String>();

    public void setRecommand(List<String> lst)
    {
        recommand = lst;
    }
    public List<String> getRecommand()
    {
        return recommand;
    }
} 
