package com.wy.dao;

import com.wy.tool.JDBConnection;
import java.sql.*;
import java.util.*;
import com.wy.domain.BigTypeForm;

//����Ʒ�������Ϣ�Ĳ���
public class BigTypeDao {
  private Connection connection = null; //�������ӵĶ���
  private PreparedStatement ps = null; //����Ԥ׼���Ķ���
  private JDBConnection jdbc = null; //�������ݿ����Ӷ���
  public BigTypeDao() {
    jdbc = new JDBConnection();
    connection = jdbc.connection; //���ù��췽��ȡ�����ݿ�����
  }

//�����ݿ���ˮ��Ϊ������ѯ����������
  public String selectName(Integer id) {
    String name = null;
    try {
      this.ps = connection.prepareStatement("select * from tb_bigType where id=?");
      ps.setString(1, id.toString());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        name = rs.getString("bigName");
      }
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
    return name;
  }

//ɾ������
  public boolean deleteBig(Integer id) {
    try {
      ps = connection.prepareStatement("delete from tb_bigType where id=?");
      ps.setString(1, id.toString());
      ps.executeUpdate();
      ps.close();
      return true;
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
      return false;
    }
  }


  //��Ӳ���
  public void insertBig(String name) {
    try {
      ps = connection.prepareStatement("insert into tb_bigType values (null,?,now())");
      ps.setString(1, name);
      ps.executeUpdate();
      ps.close();
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
  }

  //ȫ����ѯ�Ĳ���
  public List selectBig() {
    List list = new ArrayList();
    BigTypeForm big = null;
    try {
      this.ps = connection.prepareStatement("select * from tb_bigType order by id DESC");
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        big = new BigTypeForm();
        big.setId(Integer.valueOf(rs.getString(1)));
        big.setBigName(rs.getString(2));
        big.setCreaTime(rs.getString(3));
        list.add(big);
      }
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
    return list;
  }

}
