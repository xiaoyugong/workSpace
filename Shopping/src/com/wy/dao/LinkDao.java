package com.wy.dao;

import java.sql.*;
import java.util.*;

import com.wy.tool.JDBConnection;
import com.wy.domain.LinkForm;

//������������վ��Ĳ���
public class LinkDao {
  private Connection connection = null; //�������ӵĶ���
  private PreparedStatement ps = null; //����Ԥ׼���Ķ���
  private JDBConnection jdbc = null; //�������ݿ����Ӷ���
  public LinkDao() {
    jdbc = new JDBConnection();
    connection = jdbc.connection; //���ù��췽��ȡ�����ݿ�����
  }

  //ɾ���ķ���
  public void deleteLink(Integer id) {
    try {
      ps = connection.prepareStatement("delete from tb_link where id=?");
      ps.setInt(1, id.intValue());
      ps.executeUpdate();
      ps.close();
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
  }



//��ӵķ���
  public void insertLink(LinkForm form) {
    try {
      ps = connection.prepareStatement("insert into tb_link values (null,?,?)");
      ps.setString(1, form.getLinkName());
      ps.setString(2, form.getLinkAddress());
      ps.executeUpdate();
      ps.close();
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
  }




//ȫ����ѯ�ķ���
  public List selectLink() {
    List list = new ArrayList();
    LinkForm link = null;
    try {
      ps = connection.prepareStatement("select * from tb_link order by id DESC");
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        link = new LinkForm();
        link.setId(Integer.valueOf(rs.getString(1)));
        link.setLinkName(rs.getString(2));
        link.setLinkAddress(rs.getString(3));
        list.add(link);
      }
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
    return list;
  }

}
