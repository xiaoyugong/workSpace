/**
 * @Class: DIBEntityResponse.java
 * @Description:
 * @Author: Zhaoliheng Jun 5, 2015
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
public class DIBEntityResponse extends BaseInterfaceEntity
{
    private static final long serialVersionUID = 67806300100002L;

    //  选中的结果，返回List<Key  [item:value,...]>
    protected List<String> recommand = new LinkedList<String>();

    public void setRecommand(List<String> recommand)
    {
        this.recommand = recommand;
    }
    public List<String> getRecommand()
    {
        return this.recommand;
    }
}
