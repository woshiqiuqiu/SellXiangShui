package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class DBUtil {
	   //�������ݿ�
	public static Connection getCon(){
		Connection con=null;
		String url = "jdbc:mysql://localhost:3306/smalldaisy?useUnicode=true&amp;characterEncoding=UTF-8 ";
		String user = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	//�ر����ݿ�
	public static void close(ResultSet rs,PreparedStatement ps,Connection con){
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}

