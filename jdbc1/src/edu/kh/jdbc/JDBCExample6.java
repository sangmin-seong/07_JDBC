package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample6 {
	public static void main(String[] args) {
		// 아이디, 비밀번호, 이름을 입력 받아
		// 아이디, 비밀번호가 일치하는 사용자(TB_USER)의
		// 이름을 수정
		
		Connection conn = null;
		
		PreparedStatement pstmt = null;
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "KH_SSM";
			String password = "KH1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			conn.setAutoCommit(false);
			
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("아이디 입력 : ");
			String id = sc.nextLine();
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.nextLine();
			
			System.out.print("변경할 이름 입력 : ");
			String name = sc.nextLine();
			
			String sql ="""
					UPDATE TB_USER
					SET USER_NAME = ?
					WHERE USER_ID = ?
					AND   USER_PW = ?
					""";
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, pw);
			
			int result = pstmt.executeUpdate();
			
			if(result > 0 ) {
				System.out.println(id + "님의 사용자 이름을 " + name +"으로 변경하였습니다.");
				conn.commit();
			}else {
				System.out.println("아이디 또는 비밀번호가 불일치합니다.");
				conn.rollback();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
				
	}
}
