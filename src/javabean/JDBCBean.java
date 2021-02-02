package javabean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCBean {
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/library?&useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
	private static String username = "root";
	private static String password = "123";
	private Connection conn = null;
	private Statement stmt = null;

	public JDBCBean() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.createStatement();
			System.out.println("同数据库建立连接！");
		} catch (Exception ex) {
			System.out.println("无法同数据库建立连接！");
		}
	}

	public int executeUpdate(String s) {
		int result = 0;
		try {
			System.out.println(s + "------" + stmt + "-----");
			result = stmt.executeUpdate(s);
		} catch (Exception e) {
			System.out.println("执行更新错误！");
		}
		return result;
	}

	public ResultSet executeQuery(String s) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(s);
		} catch (Exception e) {
			System.out.println("执行查询错误！ " + e.getMessage());
		}
		return rs;
	}

	public void close() {
		try {
			stmt.close();
			conn.close();
		} catch (Exception e) {

		}
	}
	
	
	// bug
//	public static void main(String[] args) throws SQLException {
//		Statement stmt = null;
//		ResultSet rs = null;
//		Connection conn = null;
//		try {
//			// 1. 注册数据库的驱动
//			Class.forName("com.mysql.jdbc.Driver");
//			// 2.通过DriverManager获取数据库连接
//			String url = "jdbc:mysql://localhost:3306/jdbc";
//			String username = "root";
//			String password = "123";
//			conn = DriverManager.getConnection (url, username, 
//		     			password);
//			// 3.通过Connection对象获取Statement对象
//			 stmt = conn.createStatement();
//			// 4.使用Statement执行SQL语句。
//			String sql = "select * from users";
//			rs = stmt.executeQuery(sql);
//			// 5. 操作ResultSet结果集
//			System.out.println("id | name   | password | email  | birthday");
//			while (rs.next()) {
//				int id = rs.getInt("id"); // 通过列名获取指定字段的值
//				String name = rs.getString("name");
//				String psw = rs.getString("passward");
//				String email = rs.getString("email");
//				Date birthday = rs.getDate("birthday");
//				System.out.println(id + " | " + name + " | " + psw + " | " + email
//							+ " | " + birthday);
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} finally{
//			// 6.回收数据库资源
//			if(rs!=null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				rs = null;
//			}
//			if(stmt!=null) {
//				try {
//					stmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				stmt = null;
//			}
//			if(conn!=null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				conn = null;
//			}
//		}		
//	}
	
}

