package com.tjl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC {
	public static void main(String[] args) {
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("驱动加载成功！");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/data", "root", "11052744Chj");
			System.out.println("数据库连接成功！");
			//创建执行环境
			Statement statement = connection.createStatement();
			//查询得到结果集
			ResultSet result = statement.executeQuery("SELECT * FROM `user`");
			while(result.next())
			{
				System.out.print(result.getString("idNumber") +"  " );
				System.out.print(result.getString("name") +"  " );
				System.out.println(result.getString("password"));
			}
			
		} catch (Exception e) {
			System.out.println("驱动加载失败！");
			System.out.println("数据库连接失败！");
			e.printStackTrace();
		}
	}
}
