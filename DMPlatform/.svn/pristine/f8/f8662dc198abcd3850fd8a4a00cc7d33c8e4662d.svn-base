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
public class RFEntityCondition extends BaseInterfaceEntity
{
    private static final long serialVersionUID = 67806300100009L;

    //  随机森林算法生成描述文件参数名
    //  输入数据描述参数名，如参数值为"I","9","N","L"时，表示I-第一列(数据集ID)可忽略，9 N-后面9个属性列都是数值，L-最后一个属性是每条记录的分类结果，可选
    public static final String RF_D = "-d";
    //  随机森林算法建立随机森林模型参数名
    public static final String RF_SL = "-sl";   //  建树过程中随机选取的属性个数，可选
    public static final String RF_MS = "-ms";   //  树是否产生分支的阈值，如果小于这个值就产生叶子而不产生分支，可选
    public static final String RF_T = "-t";   //  生成的诀策树个数，必选

    protected String[] desc = null;     //  生成描述文件的参数，格式 参数名:参数值1:参数值2:..., ...
    protected String[] build = null;    //  建立随机森林模型的参数，格式 参数名:参数值, ...

    public void setDesc(String[] desc)
    {
        this.desc = desc;
    }
    public String[] getDesc()
    {
        return this.desc;
    }
    
    public void setBuild(String[] build)
    {
        this.build = build;
    }
    public String[] getBuild()
    {
        return this.build;
    }
}
