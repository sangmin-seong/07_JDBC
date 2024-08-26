package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample5 {
	public static void main(String[] args) {
		// 아이디, 비밀번호, 이름을 입력받아
		// TB_USER 테이블에 삽입하기
		
		
		Connection conn = null;
		
		/* java.sql.PreparedStatement(준비된 Statement)
		 * - SQL 중간에 ?(placeholder)를 작성하여
		 * 	 ? 자리에 java 값을 대입할 준비가 되어있는 Statement
		 * 
		 * 장점 1 : SQL 작성이 간단해짐
		 * 장점 2 : ?에 값 대입 시 자료형에 맞는 형태의
		 * 			리터럴 표기법으로 대입됨
		 * 			ex) String 대입 -> '값' (자동으로 홑따옴표 추가)
		 * 				int    대입 ->  값
		 * 장점 3 : 성능, 속도에서 우위를 가짐
		 * 
		 * ** PreparedStatement는 Statement를 상속받은 자식이다 ** 
		 * */
		PreparedStatement pstmt = null;
		
		// SELECT가 아니기 때문에 ResultSet이 필요 없다!
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "KH_SSM";
			String password = "KH1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("아이디 입력 : ");
			String id = sc.nextLine();
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.nextLine();

			System.out.print("이름 입력 : ");
			String name = sc.nextLine();
			
			
			String sql = """
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					""";
			/* AutoCommit 끄기 !
			 * 
			 * Why?
			 * -> 개발자가 마음대로 트랜잭션을 제어하기 위해
			 * */
			
			conn.setAutoCommit(false);
			
			/* 4. PreparedStatement 객체 생성 */
			// -> 객체 생성과 동시에 SQL이 담겨지게 됨
			//   - WHY? 미리 ?(placeholder)에 값을 받을 준비를
			//     		해야되기 때문에.....
			pstmt = conn.prepareStatement(sql);
			
			/* 5. ?(placeholder)에 알맞은 값 대입 */

			// pstmt.set자료형(?순서, 대입할 값);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			// -> 여기까지 실행하면 SQL 작성완료 상태!!!!
			
			
			/* 6. SQL(INSERT) 수행 후 결과(int) 반환 받기 */
			// executeQuery()  : SELECT수행, ResultSet 반환
			// executeUpdate() : DML 수행, 결과 행의 개수(int) 반환 
			// -> 보통 DML 실패 시 0, 성공 시 0 초과된 값이 반환된다.
			
			int result = pstmt.executeUpdate();
			
			
			/* 7. result 결과 값에 따른 결과 처리 + 트랜잭션 제어 처리 */
			if( result > 0 ) { //INSERT 성공	
				System.out.println(id + "님이 추가 되었습니다.");
				conn.commit(); // COMMIT 수행 -> DB에 INSERT 반영
				
			}else {
				System.out.println("추가 실패 되었습니다.");
				conn.rollback(); // 실패시 ROLLBACK
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				/* 8. 사용한 JDBC 객체 자원 반환*/
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
