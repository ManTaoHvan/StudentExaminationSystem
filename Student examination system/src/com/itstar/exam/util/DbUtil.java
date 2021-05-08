package com.itstar.exam.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//数据库工具类
public class DbUtil {
	
//静态加载变量	
public static final String URL="jdbc:mysql://localhost:3306/test?charcteEncoding=utf-8";
public static final String USER_NAME="root";//账户
public static final String PASSWORD="a";//密码

//静态加载数据库驱动
static{
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("加载数据库驱动失败");
		e.printStackTrace();
	}
}
//获取数据库连接对象
public static Connection getCon(){
	Connection con=null;
	try {
		con=DriverManager.getConnection(URL, USER_NAME, PASSWORD);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("获取数据库连接对象失败");
		e.printStackTrace();
	}
	return con;
}
//查询工具方法，可以执行所有的查询操作
public static void executeQuery(String sql, Object[] params,ResultSetHandler handler) {
	Connection con = DbUtil.getCon();
	PreparedStatement sta = null;
	ResultSet res = null;
	try {
	sta = con.prepareStatement(sql);
	for (int i = 0; i < params.length; i++) {
	sta.setObject((i + 1), params[i]);
	}
	res = sta.executeQuery();
	// 调用传递进来的结果集处理器处理结果
	handler.handle(res);
	} catch (SQLException e) {
	e.printStackTrace();
	} finally {
	closeResultSet(res);
	closeSta(sta);
	closeCon(con);
	}
}
//更新工具方法，可以执行所有的更新操作
public static void executeUpdate(String sql, Object[] params) {
	Connection con = DbUtil.getCon();
	PreparedStatement sta = null;
	try {
	sta = con.prepareStatement(sql);
	for (int i = 0; i < params.length; i++) {
	sta.setObject((i + 1), params[i]);
	}
	sta.executeUpdate();
	} catch (SQLException e) {
	e.printStackTrace();
	} finally {
	closeSta(sta);
	closeCon(con);
	}
}

//关闭数据库连接对象
public static void closeCon(Connection con){
	if(con != null){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("关闭数据库连接对象失败");
			e.printStackTrace();
		}
	}
}
//关闭预处理对象
public static void closeSta(Statement sta){
	if(sta != null){
		try {
			sta.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("关闭预处理对象失败");
			e.printStackTrace();
		}
	}
}
//关闭结果集对象
public static void closeResultSet(ResultSet res){
	if(res != null){
		try {
			res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("关闭结果集对象失败");
			e.printStackTrace();
		}
	}
}

 


}
