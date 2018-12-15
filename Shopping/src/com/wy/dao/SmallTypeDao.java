package com.wy.dao;

import com.wy.tool.JDBConnection;
import java.sql.*;
import java.util.*;
import com.wy.domain.SmallTypeForm;

//����ƷС�����Ϣ�Ĳ���
public class SmallTypeDao {
	private Connection connection = null; // �������ӵĶ���

	private PreparedStatement ps = null; // ����Ԥ׼���Ķ���

	private JDBConnection jdbc = null; // �������ݿ����Ӷ���

	public SmallTypeDao() {
		jdbc = new JDBConnection();
		connection = jdbc.connection; // ���ù��췽��ȡ�����ݿ�����
	}

	// ��С�����ؼ�Ϊ������ѯ��Ϣ
	public List selectOneBigId(Integer bigId) {
		List list = new ArrayList();
		SmallTypeForm small = null;
		try {
			this.ps = connection
					.prepareStatement("select * from tb_smallType where bigId=?");
			ps.setString(1, bigId.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				small = new SmallTypeForm();
				small.setId(Integer.valueOf(rs.getString(1)));
				small.setBigId(Integer.valueOf(rs.getString(2)));
				small.setSmallName(rs.getString(3));
				small.setCreaTime(rs.getString(4));
				list.add(small);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return list;
	}

	// �����ݿ���ˮ��Ϊ������ѯС��������
	public String selectName(Integer id) {
		String name = null;
		try {
			this.ps = connection
					.prepareStatement("select * from tb_smallType where id=?");
			ps.setString(1, id.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				name = rs.getString("smallName");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return name;
	}

	// ɾ������
	public boolean deleteSmall(Integer id) {
		try {
			ps = connection.prepareStatement("delete from tb_smallType where id=?");
			ps.setString(1, id.toString());
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	// �޸Ĳ���
	public void updateSmall(SmallTypeForm form) {
		try {
			ps = connection.prepareStatement("update tb_smallType set bigId=?,smallName=? where id=?");
			ps.setString(1, form.getBigId().toString());
			ps.setString(2, form.getSmallName());
			ps.setString(3, form.getId().toString());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// ��Ӳ���
	public void insertSmall(SmallTypeForm form) {
		try {
			ps = connection.prepareStatement("insert into tb_smallType values (null,?,?,now())");
			ps.setString(1, form.getBigId().toString());
			ps.setString(2, form.getSmallName());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// �����ݿ���ˮ��Ϊ������ѯ��Ϣ
	public SmallTypeForm selectOneBig(Integer id) {
		SmallTypeForm small = null;
		try {
			this.ps = connection
					.prepareStatement("select * from tb_smallType where id=?");
			ps.setString(1, id.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				small = new SmallTypeForm();
				small.setId(Integer.valueOf(rs.getString(1)));
				small.setBigId(Integer.valueOf(rs.getString(2)));
				small.setSmallName(rs.getString(3));
				small.setCreaTime(rs.getString(4));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return small;
	}

	// ȫ����ѯ�Ĳ���
	public List selectSmall() {
		List list = new ArrayList();
		SmallTypeForm small = null;
		try {
			this.ps = connection.prepareStatement("select * from tb_smallType order by id DESC");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				small = new SmallTypeForm();
				small.setId(Integer.valueOf(rs.getString(1)));
				small.setBigId(Integer.valueOf(rs.getString(2)));
				small.setSmallName(rs.getString(3));
				small.setCreaTime(rs.getString(4));
				list.add(small);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return list;
	}
}
