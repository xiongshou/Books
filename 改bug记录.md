# 记录一下改bug历程

## 总结

* 一堆报错，有几行是关于sql的
* 然后：新建一个JDBC用来访问数据库，发现失败、
* 然后：新建的JDBC代码中用输出语句+注释，找见通过DriverManager获取数据库连接有问题
* 然后：处理这个问题，期间发现mac的mysql端口号不是3306，还有一个语法问题，还有jar包的版本问题。
* 以上操作使得JDBC可以连接数据库
* 按这个思路成功把项目跑起来了

## 过程

### Tomact问题

在ljc好同志的帮助下，解决的众多bug。记录一下。首先在本地服务器tomcat跑遇到问题。
刚开始的报错：MySQL CommunicationsException这些的，反正很多。大概就是关于数据库连接问题之类的。

![image-20210202172010520](https://tva1.sinaimg.cn/large/008eGmZEly1gn9b9on9a5j30g209c45t.jpg)

## 新建一个JDBC，找bug

先建立一个JDBC，简单测试一下连接数据库有没有问题。

* 新建一个数据库
* 简简单单建立一张表
* 然后写JDBC，查询一下这个表

```java
package cn.itcast.jdbc.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
public class Example01 {
	public static void main(String[] args) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			// 1. 注册数据库的驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 2.通过DriverManager获取数据库连接
			String url = "jdbc:mysql://localhost:3306/jdbc";
			String username = "root";
			String password = "itcast";
			conn = DriverManager.getConnection (url, username, 
		     			password);
			// 3.通过Connection对象获取Statement对象
			 stmt = conn.createStatement();
			// 4.使用Statement执行SQL语句。
			String sql = "select * from users";
			rs = stmt.executeQuery(sql);
			// 5. 操作ResultSet结果集
			System.out.println("id | name   | password | email  | birthday");
			while (rs.next()) {
				int id = rs.getInt("id"); // 通过列名获取指定字段的值
				String name = rs.getString("name");
				String psw = rs.getString("password");
				String email = rs.getString("email");
				Date birthday = rs.getDate("birthday");
				System.out.println(id + " | " + name + " | " + psw + " | " + email
							+ " | " + birthday);
			}
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

```

果然报错，报错内容是关于url的一些，百度之后，要把

```java
// 1. 注册数据库的驱动
			Class.forName("com.mysql.jdbc.Driver");
```

换成

```java
// 1. 注册数据库的驱动
			Class.forName("com.mysql.cj.jdbc.Driver");
```

重新跑一下，仍然报错，不过第一行的报错换了（刚才操作有用

之后的报错信息大概是一堆sql的报错

![CE5D4F213935813D2596F4E1B3DB7136](https://tva1.sinaimg.cn/large/008eGmZEly1gn9bty1petj30ya0k2qan.jpg)

## 更换jar包

然后ljc好哥哥，提醒我说说jar包版本问题，就很关键。

然后下载了合适的jar包弄好之后，报错信息大量减少（JDBC测试代码，不上项目代码）

![image-20210202174307236](https://tva1.sinaimg.cn/large/008eGmZEly1gn9bxhv8hej312c0bswnq.jpg)

### 注释法定位bug

使用输出语句+“写注释”把bug定位在这部分。

![image-20210202174745816](https://tva1.sinaimg.cn/large/008eGmZEly1gn9c2bl8gbj30uk0u0x1e.jpg)

把bug定位在

>  通过DriverManager获取数据库连接

### mac更换端口

然后，考虑是不是数据库表没弄好。mysql命令行查看，发现没有问题。

之后，百度说可能是端口号问题。看了下代码端口号3306没问题呀。

然后，莫名其妙翻出一片博客，说mac的mysql端口号修改什么什么的。考虑可能是端口号问题。

之后mysql命令查看mysql端口。居然是0!!!!

![image-20210202180148176](https://tva1.sinaimg.cn/large/008eGmZEly1gn9cgxrb7kj30mk0cc79d.jpg)

然后用vim到一个指定文件把端口号改一下（百度可以找见，需要把文件转换为xml）

端口号改好之后，还是报错。然后发现少写了一个\

之后顺利，没有bug。新建的JDBC可以连接到数据库。

## 以上操作，仅仅解决了新建的JDBC访问不了数据库的问题

按照上面的思路，把项目改改，就可以访问数据库了。

最后，感谢ljc好哥哥！！