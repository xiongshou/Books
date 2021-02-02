package javabean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class text {
	public static void main(String[] args) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			// 1. 注册数据库的驱动
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 2.通过DriverManager获取数据库连接
			String url = "jdbc:mysql://localhost:3306/library";
			String username = "root";
			String password = "123";
			conn = DriverManager.getConnection (url, username, 
		     			password);
			// 3.通过Connection对象获取Statement对象
			 stmt = conn.createStatement();
			// 4.使用Statement执行SQL语句。
			String sql = "select * from admin";
			rs = stmt.executeQuery(sql);
			// 5. 操作ResultSet结果集
			System.out.println("id | name   | password | email  | birthday");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally{
			// 6.回收数据库资源
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs = null;
			}
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				stmt = null;
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				conn = null;
			}
		}		
	}
}
