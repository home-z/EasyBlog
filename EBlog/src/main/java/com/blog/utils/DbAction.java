package com.blog.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * jdbc方式操作数据库。数据库连接信息存放在db.properties文件中
 * 
 * @author tim
 *
 */
public class DbAction {

	private static String dbDriverClass = null;// 数据库驱动
	private static String dbUrl = null;// 数据库地址
	private static String userName = null;// 用户名
	private static String userPasswd = null;// 密码

	/**
	 * 静态代码块中（只加载一次）
	 */
	static {
		try {
			// 读取db.properties文件
			Properties props = new Properties();
			/**
			 * . 代表java命令运行的目录 在java项目下，.java命令的运行目录从项目的根目录开始
			 * 在web项目下，.java命令的而运行目录从tomcat/bin目录开始 所以不能使用点.
			 */
			// FileInputStream in = new FileInputStream("./src/db.properties");

			/**
			 * 使用类路径的读取方式 / : 斜杠表示classpath的根目录 在java项目下，classpath的根目录从bin目录开始
			 * 在web项目下，classpath的根目录从WEB-INF/classes目录开始
			 */
			InputStream in = DbAction.class.getResourceAsStream("/db.properties");

			props.load(in);// 加载文件
			// 读取信息
			dbDriverClass = props.getProperty("dbDriverClass");
			dbUrl = props.getProperty("dbUrl");
			userName = props.getProperty("userName");
			userPasswd = props.getProperty("userPasswd");

			Class.forName(dbDriverClass);// 注册驱动程序
		} catch (ClassNotFoundException e) {
			System.out.println("加载驱程程序注册出错！");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("加载数据库配置文件出错！");
		}
	}

	/**
	 * 得到数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			if (null == conn) {
				conn = DriverManager.getConnection(dbUrl, userName, userPasswd);
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * 释放【ResultSet】资源
	 * 
	 * @param rs
	 */
	public static void free(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException err) {
			err.printStackTrace();
		}
	}

	/**
	 * 释放【Statement】资源
	 * 
	 * @param st
	 */
	public static void free(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException err) {
			err.printStackTrace();
		}
	}

	/**
	 * 释放【Connection】资源
	 * 
	 * @param conn
	 */
	public static void free(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException err) {
			err.printStackTrace();
		}
	}

	/**
	 * 释放所有数据资源
	 * 
	 * @param rs
	 * @param st
	 * @param conn
	 */
	public static void free(ResultSet rs, Statement st, Connection conn) {
		free(rs);
		free(st);
		free(conn);
	}

	/*
	 * 执行sql查询语句，返回ResultSet数据集
	 */
	public static ResultSet getQuery(String strSql) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
		} catch (SQLException err) {
			err.printStackTrace();
			free(rs, stmt, conn);
		}

		return rs;
	}

	/**
	 * 增删改【Add、Delete、Update】
	 * 
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public static int executeUpdate(String sql, String[] parameters) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < parameters.length; i++) {
				pstmt.setString(i + 1, parameters[i]);
			}
			result = pstmt.executeUpdate();
		} catch (SQLException err) {
			err.printStackTrace();
			free(null, pstmt, conn);
		} finally {
			free(null, pstmt, conn);
		}

		return result;
	}
}
