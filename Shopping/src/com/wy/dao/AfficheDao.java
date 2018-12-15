package com.wy.dao;

import java.sql.*;
import java.util.*;

import com.wy.tool.JDBConnection;
import com.wy.domain.AfficheForm;

//�Թ�����Ϣ�Ĳ���
public class AfficheDao {
  private Connection connection = null; //�������ӵĶ���
  private PreparedStatement ps = null; //����Ԥ׼���Ķ���
  private JDBConnection jdbc = null; //�������ݿ����Ӷ���
  public AfficheDao() {
    jdbc = new JDBConnection();
    connection = jdbc.connection; //���ù��췽��ȡ�����ݿ�����
  }

  //ɾ���ķ���
  public void deleteAffiche(Integer id) {
    try {
      ps = connection.prepareStatement("delete from tb_affiche where id=?");
      ps.setInt(1, id.intValue());
      ps.executeUpdate();
      ps.close();
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
  }

  //�޸ĵķ���
  public void updateAffiche(AfficheForm form) {
    try {
      ps = connection.prepareStatement("update tb_affiche set name=?,content=? where id=?");
      ps.setString(1, form.getName());
      ps.setString(2, form.getContent());
      ps.setInt(3, form.getId().intValue());
      ps.executeUpdate();
      ps.close();
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
  }

//��ӵķ���
  public void insertAffiche(AfficheForm form) {
    try {
      ps = connection.prepareStatement("insert into tb_affiche values (null,?,?,now())");
      ps.setString(1, form.getName());
      ps.setString(2, form.getContent());
      ps.executeUpdate();
      ps.close();
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
  }

//�����ݿ���ˮ��Ϊ������ѯ��Ϣ
  public AfficheForm selectOneAffiche(Integer id) {
    AfficheForm affiche = null;
    try {
      ps = connection.prepareStatement("select * from tb_affiche where id=?");
      ps.setInt(1, id.intValue());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        affiche = new AfficheForm();
        affiche.setId(Integer.valueOf(rs.getString(1)));
        affiche.setName(rs.getString(2));
        affiche.setContent(rs.getString(3));
        affiche.setIssueTime(rs.getString(4));
      }
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
    return affiche;
  }

//ȫ����ѯ�ķ���
  public List selectAffiche() {
    List list = new ArrayList();
    AfficheForm affiche = null;
    try {
      ps = connection.prepareStatement("select * from tb_affiche order by id DESC");
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        affiche = new AfficheForm();
        affiche.setId(Integer.valueOf(rs.getString(1)));
        affiche.setName(rs.getString(2));
        affiche.setContent(rs.getString(3));
        affiche.setIssueTime(rs.getString(4));
        list.add(affiche);
      }
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
    return list;

  }

}
