package com.wy.dao;

import java.sql.*;
import java.util.*;

import com.wy.tool.JDBConnection;
import com.wy.domain.GoodsForm;

//����Ʒ��Ĳ���
public class GoodsDao {
  private Connection connection = null; //�������ӵĶ���
  private PreparedStatement ps = null; //����Ԥ׼���Ķ���
  private JDBConnection jdbc = null; //�������ݿ����Ӷ���
  public GoodsDao() {
    jdbc = new JDBConnection();
    connection = jdbc.connection; //���ù��췽��ȡ�����ݿ�����
  }

//������Ʒ��ID�޸Ĺ�����Ʒ������
  public void updateGoodsNumber(int number, Integer id) {
    try {
      ps = connection.prepareStatement("update tb_goods set number=number+? where id=?");
      ps.setInt(1, number);
      ps.setInt(2, id.intValue());
      ps.executeUpdate();
      ps.close();
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }

  }

//�����ؼۼ۸�ķ���
  public void managerPrice(GoodsForm form) {
    try {
      ps = connection.prepareStatement("update tb_goods set freePrice=?,mark=? where id=?");
      ps.setFloat(1, form.getFreePrice().floatValue());
      ps.setInt(2, form.getMark().intValue());
      ps.setInt(3, form.getId().intValue());
      ps.executeUpdate();
      ps.close();
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
  }

  //����Ʒ���Ƿ��ؼ�Ϊ������ѯ��Ϣ
  public List selectMark(Integer mark) {
    List list = new ArrayList();
    GoodsForm goods = null;
    try {
      ps = connection.prepareStatement("select * from tb_goods where mark=? order by id DESC");
      ps.setInt(1, mark.intValue());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        goods = new GoodsForm();
        goods.setId(Integer.valueOf(rs.getString(1)));
        goods.setBig(Integer.valueOf(rs.getString(2)));
        goods.setSmall(Integer.valueOf(rs.getString(3)));
        goods.setName(rs.getString(4));
        goods.setFrom(rs.getString(5));
        goods.setIntroduce(rs.getString(6));
        goods.setCreaTime(rs.getString(7));
        goods.setNowPrice(Float.valueOf(rs.getString(8)));
        goods.setFreePrice(Float.valueOf(rs.getString(9)));
        goods.setNumber(Integer.valueOf(rs.getString(10)));
        goods.setPriture(rs.getString(11));
        goods.setMark(Integer.valueOf(rs.getString(12)));
        list.add(goods);
      }
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
    return list;
  }

//��Ӳ���
  public void insertGoods(GoodsForm form) {
    try {
      ps = connection.prepareStatement("insert into tb_goods values (null,?,?,?,?,?,now(),?,?,?,?,?)");
      ps.setInt(1, form.getBig().intValue());
      ps.setInt(2, form.getSmall().intValue());
      ps.setString(3, form.getName());
      ps.setString(4, form.getFrom());
      ps.setString(5, form.getIntroduce());
      ps.setFloat(6, form.getNowPrice().floatValue());
      ps.setFloat(7, form.getFreePrice().floatValue());
      ps.setInt(8, 0);
      ps.setString(9, form.getPriture());
      ps.setInt(10, 0);
      ps.executeUpdate();
      ps.close();
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
  }

//����Ʒ���Ϊ����ɾ����Ϣ
  public void deleteGoods(Integer id) {
    try {
      ps = connection.prepareStatement("delete from tb_goods where id=?");
      ps.setInt(1, id.intValue());
      ps.executeUpdate();
      ps.close();
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
  }

//����Ʒ�ı��Ϊ������ѯ��Ϣ
  public GoodsForm selectOneGoods(Integer id) {
    GoodsForm goods = new GoodsForm();
    try {
      ps = connection.prepareStatement("select * from tb_goods where id=? order by id DESC");
      ps.setInt(1, id.intValue());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {   
        goods.setId(Integer.valueOf(rs.getString(1)));
        goods.setBig(Integer.valueOf(rs.getString(2)));
        goods.setSmall(Integer.valueOf(rs.getString(3)));
        goods.setName(rs.getString(4));
        goods.setFrom(rs.getString(5));
        goods.setIntroduce(rs.getString(6));
        goods.setCreaTime(rs.getString(7));
        goods.setNowPrice(Float.valueOf(rs.getString(8)));
        goods.setFreePrice(Float.valueOf(rs.getString(9)));
        goods.setNumber(Integer.valueOf(rs.getString(10)));
        goods.setPriture(rs.getString(11));
        goods.setMark(Integer.valueOf(rs.getString(12)));
      }
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
    return goods;
  }

  //����Ʒ��С���ı��Ϊ������ѯ��Ϣ
  public List selectSmall(Integer small) {
    List list = new ArrayList();
    GoodsForm goods = null;
    try {
      ps = connection.prepareStatement("select * from tb_goods where smallId=? order by id DESC");
      ps.setInt(1, small.intValue());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        goods = new GoodsForm();
        goods.setId(Integer.valueOf(rs.getString(1)));
        goods.setBig(Integer.valueOf(rs.getString(2)));
        goods.setSmall(Integer.valueOf(rs.getString(3)));
        goods.setName(rs.getString(4));
        goods.setFrom(rs.getString(5));
        goods.setIntroduce(rs.getString(6));
        goods.setCreaTime(rs.getString(7));
        goods.setNowPrice(Float.valueOf(rs.getString(8)));
        goods.setFreePrice(Float.valueOf(rs.getString(9)));
        goods.setNumber(Integer.valueOf(rs.getString(10)));
        goods.setPriture(rs.getString(11));
        goods.setMark(Integer.valueOf(rs.getString(12)));
        list.add(goods);
      }
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
    return list;
  }

  //����Ʒ�Ĵ����ı��Ϊ������ѯ��Ϣ
  public List selectBig(Integer big) {
    List list = new ArrayList();
    GoodsForm goods = null;
    try {
      ps = connection.prepareStatement("select * from tb_goods where bigId=? order by id DESC");
      ps.setInt(1, big.intValue());
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        goods = new GoodsForm();
        goods.setId(Integer.valueOf(rs.getString(1)));
        goods.setBig(Integer.valueOf(rs.getString(2)));
        goods.setSmall(Integer.valueOf(rs.getString(3)));
        goods.setName(rs.getString(4));
        goods.setFrom(rs.getString(5));
        goods.setIntroduce(rs.getString(6));
        goods.setCreaTime(rs.getString(7));
        goods.setNowPrice(Float.valueOf(rs.getString(8)));
        goods.setFreePrice(Float.valueOf(rs.getString(9)));
        goods.setNumber(Integer.valueOf(rs.getString(10)));
        goods.setPriture(rs.getString(11));
        goods.setMark(Integer.valueOf(rs.getString(12)));
        list.add(goods);
      }
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
    return list;
  }

//ȫ����ѯ
  public List selectGoods() {
    List list = new ArrayList();
    GoodsForm goods = null;
    try {
      ps = connection.prepareStatement("select * from tb_goods order by id DESC");
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        goods = new GoodsForm();
        goods.setId(Integer.valueOf(rs.getString(1)));
        goods.setBig(Integer.valueOf(rs.getString(2)));
        goods.setSmall(Integer.valueOf(rs.getString(3)));
        goods.setName(rs.getString(4));
        goods.setFrom(rs.getString(5));
        goods.setIntroduce(rs.getString(6));
        goods.setCreaTime(rs.getString(7));
        goods.setNowPrice(Float.valueOf(rs.getString(8)));
        goods.setFreePrice(Float.valueOf(rs.getString(9)));
        goods.setNumber(Integer.valueOf(rs.getString(10)));
        goods.setPriture(rs.getString(11));
        goods.setMark(Integer.valueOf(rs.getString(12)));
        list.add(goods);
      }
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
    return list;
  }

  //ȫ����ѯ
  public List selectGoodsNumber() {
    List list = new ArrayList();
    GoodsForm goods = null;
    try {
      ps = connection.prepareStatement("select * from tb_goods order by number DESC");
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        goods = new GoodsForm();
        goods.setId(Integer.valueOf(rs.getString(1)));
        goods.setBig(Integer.valueOf(rs.getString(2)));
        goods.setSmall(Integer.valueOf(rs.getString(3)));
        goods.setName(rs.getString(4));
        goods.setFrom(rs.getString(5));
        goods.setIntroduce(rs.getString(6));
        goods.setCreaTime(rs.getString(7));
        goods.setNowPrice(Float.valueOf(rs.getString(8)));
        goods.setFreePrice(Float.valueOf(rs.getString(9)));
        goods.setNumber(Integer.valueOf(rs.getString(10)));
        goods.setPriture(rs.getString(11));
        goods.setMark(Integer.valueOf(rs.getString(12)));
        list.add(goods);
      }
    }
    catch (SQLException ex) {
    	ex.printStackTrace();
    }
    return list;
  }


}
