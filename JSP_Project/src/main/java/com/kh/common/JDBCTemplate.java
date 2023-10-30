package com.kh.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	// 1. Connection 객체 생성 후 반환하는 메소드
	public static Connection getConnection() {
		Properties prop = new Properties(); // Map계열 collection (key-value)

		// driver.properties 파일을 읽어서 해당 정보를 사용할것
		// 이때 해당 파일의 물리적인 경로를 읽어와서 스트림에 넣어야한다.
		/// D:/server-workspace2/JSP_Project/src/main/webapp/WEB-INF/classes/db/driver/driver.properties
		String filePath = JDBCTemplate.class.getResource("/db/driver/driver.properties").getPath();

		// 커넥션 객체 준비
		Connection conn = null;
		try {
			// properties 객체에 해당 driver.properties파일 정보 읽어오기
			prop.load(new FileInputStream(filePath));

			// 1)jdbc driver 등록
			Class.forName(prop.getProperty("driver"));

			// 2)connection 객체 생성하기
			// conn = DriverManager.getConnection("jdbc:oraclle~~~","계정명","비밀번호");
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
					prop.getProperty("password"));

			// 3)auto commit 해제하기
			conn.setAutoCommit(false);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	// static 키워드로 작성할것 / null인지 닫혀있는지 확인후 작업할것 각 메소드는 반환형이 없다.
	// 2. Connection 객체 commit 요청 메소드
	public static void commit(Connection conn) {

		try {
			if (conn != null && !conn.isClosed()) {
				conn.commit();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 3. Connection 객체 rollback 요청 메소드
	public static void rollback(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.rollback();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 4. 자원반납 메소드 (Connection,ResultSet,Statement)
	public static void close(Connection conn) {

		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//ResultSet
	public static void close(ResultSet rset) {

		try {
			if (rset != null && !rset.isClosed()) {
				rset.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//Statement(다형성이 적용되니 PreparedStatement는 따로 작성하지 않아도 처리 가능)
	public static void close(Statement stmt) {
		try {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
