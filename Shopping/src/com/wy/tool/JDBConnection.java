package com.wy.tool;

import java.sql.*;

public class JDBConnection {
	private String dbDriver = "com.mysql.jdbc.Driver"; 

	private String url = "jdbc:mysql://localhost:3306/db_shopping?useUnicode=true&characterEncoding=utf8"; // URL

	public Connection connection = null;

	public JDBConnection() {
		try {
			Class.forName(dbDriver).newInstance(); 
			connection = DriverManager.getConnection(url, "root", "root"); 
		} catch (Exception ex) {
			System.out.println("error");
		}
	}
}
