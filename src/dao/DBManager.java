package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//DB 연결
//Resource Release
public class DBManager {
	static String url = "jdbc:mysql://localhost:3306/studycafe";
	static String user = "root";
	static String pwd = "1111";

	public static Connection getConnection() {
		Connection con = null;

		try {
			con = DriverManager.getConnection(url, user, pwd);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}

	public static void releaseConnection(PreparedStatement pstmt, Connection con) {
		// 리소스 정리 작업
		try {
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void releaseConnection(ResultSet rs, PreparedStatement pstmt, Connection con) {
		// 리소스 정리 작업
		try {
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}