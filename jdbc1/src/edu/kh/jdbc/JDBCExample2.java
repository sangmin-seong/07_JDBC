package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample2 {
	public static void main(String[] args) {
		// 입력받은 급여보다 초과해서 받는 사원의
		// 사번, 이름, 급여 조회하기
		
		/* 1. JDBC 객체 참조용 변수 선언*/
		Connection conn = null;
		
		Statement stmt = null;
		
		ResultSet rs = null;
		
		try {
			/* 2. DriverManager 객체 이용, Connection 객체 생성하기 */
			/* 2-1) Oracle JDBC Driver 객체를 메모리에 로드(적재) 하기*/
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			/* 2-2) DB 연결 정보 작성*/
			String type = "jdbc:oracle:thin:@";
			String host = "localhost";
			String port = ":1521";
			String dbName = ":XE";
			String userName = "KH_SSM";
			String password = "KH1234";
			
			/* 2-3) DB 연결 정보와 DriverManager 이용해서 Connection 객체 생성 */
			conn = DriverManager.getConnection(type + host + port + dbName,
												userName, password);
			
			// System.out.println(conn);
			
			/* 3. SQL 작성*/
			Scanner sc = new Scanner(System.in);
			System.out.print("급여 입력 : ");
			int input = sc.nextInt();
			
			String sql = """
					SELECT EMP_ID,	EMP_NAME,	SALARY
					FROM EMPLOYEE
					WHERE SALARY > """ + input;
			
			/* 4. Statement 객체 생성 */
			stmt = conn.createStatement();
			
			/* 5. Statement 객체를 이용해서 SQL 수행 후 결과 반환 받기 */
			rs = stmt.executeQuery(sql);
			
			/* 6. 조회 결과가 담겨있는 ResultSet을
			 * 커서(Cursor)를 이용해 1행씩 접근해
			 * 각 행에 작성된 컬럼 값 얻어오기
			 * */
			while(rs.next()) {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_Name");
				int    salary = rs.getInt("SALARY");
				
				System.out.printf("%s / %s / %d원 \n",
						empId, empName, salary);
			}
		}catch (Exception e) {
			// 최상위 예외인 Exception을 이용해서 모든 예외를 처리
			// -> 다형성 업캐스팅 적용
			e.printStackTrace();
		}finally {
			try{
				/* 7. 사용 완료된 JDBC 객체 자원 반환(close) */
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
			

	}
}
