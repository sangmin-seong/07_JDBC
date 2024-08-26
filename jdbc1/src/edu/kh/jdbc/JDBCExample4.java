package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {
	public static void main(String[] args) {
		// 부서명을 입력 받아
		// 해당 부서에 근무하는 사원의
		// 사번, 이름, 부서명, 직급명을
		// 직급코드 오름차순으로 조회
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@";
			String host = "localhost";
			String port = ":1521";
			String dbName = ":XE";
			String userName = "KH_SSM";
			String password = "KH1234";
			
			conn = DriverManager.getConnection(
					type + host + port + dbName, userName, password);
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("부서명 입력 : ");
			String title = sc.next();
			
			
			String sql = """
					SELECT EMP_ID, EMP_NAME, DEPT_TITLE, JOB_NAME
					FROM EMPLOYEE
					LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
					JOIN JOB USING (JOB_CODE)
					WHERE DEPT_TITLE = 
					"""
					+ "'" + title  + "'"
					
					+ " ORDER BY DEPT_CODE ASC";
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
			String empId    = rs.getString("EMP_ID");
			String empName  = rs.getString("EMP_Name");
			String deptTitle = rs.getString("DEPT_TITLE");
			String jobName  = rs.getString("JOB_NAME");
			
			System.out.printf("사번 : %s / 이름 : %s / 부서명 : %s / 직급명 : %s \n",
					empId, empName, deptTitle, jobName);
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
