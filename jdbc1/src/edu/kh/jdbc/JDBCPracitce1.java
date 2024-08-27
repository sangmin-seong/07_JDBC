package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCPracitce1 {
	// EMPLOYEE	테이블에서
	// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
	// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것
			
	// - 조건 1 : 성별 (M, F)
	// - 조건 2 : 급여 범위
	// - 조건 3 : 급여 오름차순/내림차순
			
	// [실행화면]
	// 조회할 성별(M/F) : F
	// 급여 범위(최소, 최대 순서로 작성) : 3000000 4000000
	// 급여 정렬(1.ASC, 2.DESC) : 2
			
	// 사번 | 이름   | 성별 | 급여    | 직급명 | 부서명
	//--------------------------------------------------------
	// 218  | 이오리 | F    | 3890000 | 사원   | 없음
	// 203  | 송은희 | F    | 3800000 | 차장   | 해외영업2부
	// 212  | 장쯔위 | F    | 3550000 | 대리   | 기술지원부
	// 222  | 이태림 | F    | 3436240 | 대리   | 기술지원부
	// 207  | 하이유 | F    | 3200000 | 과장   | 해외영업1부
	// 210  | 윤은해 | F    | 3000000 | 사원   | 해외영업1부
	
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "KH_SSM";
			String password = "KH1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("조회할 성별(M/F) : ");
			String a = sc.nextLine().toUpperCase();
			
			System.out.print("급여 범위(최소, 최대 순서로 작성) : ");
			int min = sc.nextInt(); 
			int max = sc.nextInt();
			
			
			
			System.out.println("급여 정렬(1.ASC, 2.DESC) :");
			int sal = sc.nextInt();
			
			String asc = (sal == 2) ? " DESC" : " ASC";
			
			String sql = """
					SELECT EMP_ID, EMP_NAME, DECODE(SUBSTR(EMP_NO,8,1), 1, 'M', 2, 'F') AS GENDER, SALARY, JOB_NAME, NVL(DEPT_TITLE,'없음') AS DEPT_TITLE
					FROM EMPLOYEE
					LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
					JOIN JOB USING (JOB_CODE)
					
					WHERE DECODE(SUBSTR(EMP_NO,8,1), 1, 'M', 2, 'F') = ? 
					
					AND SALARY BETWEEN ? AND ?
					
				    ORDER BY SALARY """ + asc;
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, a);
			pstmt.setInt(2, min);
			pstmt.setInt(3, max);
			
			int count = 0;
					
			rs = pstmt.executeQuery(); 
			
			System.out.println(" 사번 | 이름   | 성별 | 급여      | 직급명  | 부서명");
			System.out.println("--------------------------------------------------------");

			while(rs.next()) {
				
				count++;
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String decode = rs.getString("GENDER");
				int    salary  = rs.getInt("SALARY");
				String jobName = rs.getString("JOB_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				
				System.out.printf(" %s  | %s | %s     | %d | %s   | %s \n",
						           empId, empName, decode, salary, jobName, deptTitle 
						);
			}
			if (count == 0) {
				System.out.println("검색 결과가 없습니다.");
			} else {
				System.out.printf("검색 결과는 %d명 입니다.%n", count);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();

				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
			
	}
	
	

	

}
