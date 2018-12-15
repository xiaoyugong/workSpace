/**
 * @Class: FPEntityResponse.java
 * @Description:
 * @Author: Zhaoliheng Jun 5, 2015
 * @Version: V1.0
 */

package com.goldtel.dmp.interfaceentities;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.datamining.frequentpattern.FrequentPatternImpl;
import com.goldtel.dmp.hadoop.HdfsUtil;

@XmlRootElement(name = "file")
@XmlAccessorType(XmlAccessType.FIELD)
public class FPEntityResponse extends BaseInterfaceEntity
{
    private static final long serialVersionUID = 67806300100003L;

    //  选中的结果，格式如：List<key  ([item],value), ...>
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
