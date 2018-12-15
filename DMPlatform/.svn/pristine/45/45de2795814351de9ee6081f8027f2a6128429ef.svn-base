/**
 * @Class: ClusterEntityResponse.java
 * @Description:
 * @Author: Zhaoliheng Jun 10, 2015
 * @Version: V1.0
 */

package com.goldtel.dmp.interfaceentities;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.goldtel.dmp.Const;
import com.goldtel.dmp.datamining.frequentpattern.FrequentPatternImpl;
import com.goldtel.dmp.hadoop.HdfsUtil;

@XmlRootElement(name = "file")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClusterEntityResponse extends BaseInterfaceEntity
{
    private static final long serialVersionUID = 67806300100008L;

    /*
     * 选中的结果，如果是查询聚类中心，格式为：List<中心点名称:这个聚类中心下记录个数:聚类中心属性:半径>
     * 如果是查询聚类中心内的记录，格式为：List<距离:属性个数:属性1:属性2:...>
     */
    protected List<String> recommand = new LinkedList<String>();

    public void setRecommand(List<String> recommand)
    {
        this.recommand = recommand;
    }
    public List<String> getRecommand()
    {
        return this.recommand;
    }
    
    /**
     * 注入查询结果
     * @param query
     * @throws IOException
     */
    public void putResult(ClusterEntityQuery query, String calName) throws IOException
    {
        setDataSet(query.getDataSet());
        String strInPath = Const.HDFS_MYCLU + Const.OUTPATH + Const.SPLIT_PATH + calName + Const.SPLIT_PATH + 
                           getDataSet() + Const.SPLIT_PATH + Const.RESULT_PATH + Const.SPLIT_PATH;
        
        if (query.getBIsOnlyCenterPoints())
        {
            strInPath += Const.RESULT_CLUSTER_CENTER;
        }
        else
        {
            strInPath += Const.RESULT_CLUSTER;
        }

        HdfsUtil hu = new HdfsUtil();
        hu.readHdfsFileCluster(strInPath, query, recommand);
    }
    
    @Override
    public String toString()
    {
        if (recommand.size() == 0)
        {
            return "";
        }
        StringBuffer str = new StringBuffer("[");
        for (int i = 0; i < recommand.size(); i++)
        {
            str.append("{");
            str.append(recommand.get(i));
            str.append("}");
        }
        /*StringBuffer str = new StringBuffer("\"recommand\":[\"");
        for (int i = 0; i < recommand.size(); i++)
        {
            str.append(recommand.get(i));
            str.append("\",\"");
        }
        if (str.toString().endsWith("\",\""))
        {
            str.deleteCharAt(str.length() - 1);
            str.deleteCharAt(str.length() - 1);
        }*/
        str.append("]");
        return str.toString();
    }
}
