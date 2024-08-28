package edu.kh.jdbc.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.user;

// DAO (Data Access Object) :
// 데이터가 저장된 곳에 접근하는 용도의 객체
// -> DB에 접근하여 Java에서 원하는 결과를 얻기 위해
//	  SQL을 수행하고 결과 반환받는 역할


public class userDao {
	
	// 필드
	// - DB 접근 관련한 JDBC 객체 참조형 변수를 미리 선언	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	Scanner sc = new Scanner(System.in);
	
	/** 전달 받은 Connection을 이용해 DB에 접근하여
	 * 전달 받은 아이디와 일치하는 user 정보 조회하기
	 * @param conn  : Service에서 생성한 Connection 객체
	 * @param input : View에서 입력 받은 아이디
	 * @return
	 */
	public user selectId(Connection conn, String input) {
		
		user user = null; // 결과 저장용 변수
		
		try {
			// SQL 작성
			String sql = "SELECT * FROM TB_USER WHERE USER_ID = ?";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? placeholder 에 알맞은 값 대입
			pstmt.setString(1, input);
			
			// SQL 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			// 조회 결과 있을 경우
			// -> 중복되는 아이디가 없을 경우
			// 	  1행만 조회 되기 때문에
			//    while보다 if를 사용하는 것이 효과적
			if(rs.next()) {
				
				// 각 컬럼의 값 얻어오기
				int userNo      = rs.getInt("USER_NO");
				String userId   = rs.getString("USER_ID");
				String userPw   = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				
				// java.sql.Date 활용
				Date enrollDate = rs.getDate("ENROLL_DATE");
				
				
				// 조회된 컬럼값을 이용해 user 객체 생성
				user = new user(userNo, 
								userId,
								userPw,
								userName,
								enrollDate.toString());
				
			}else System.out.println("일치하는 아이디가 없습니다.");
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			// 사용한 JDBC 객체 자원 반환(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
			// Connection 객체는 Service에서 close!!!!
			
		}
		
		
		return user; // 결과 반환(생성된 user 또는 null)
	}

	
	
	/**
	 * user 등록 DAO 메서드
	 * @param conn : DB연결 정보가 담겨있는 객체
	 * @param user : 입력 받은 id, pw, name
	 * @return result : INSERT 결과 행의 개수
	 * @throws Exception : 발생하는 예외 모두 던짐
	 */
	public int insertUser(Connection conn, user user) throws Exception {
	
		// SQL 수행 중 발생하는 예외를
		// catch로 처리하지 않고, throws를 이용해서 호출부로 던져 처리
		// -> catch문이 필요 없다!!
		
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			// 2. SQL 작성
			String sql = """
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					""";
			
			// 3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ? 알맞은 값 대입
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			
			// 5. SQL(INSERT) 수행(executeUpdate()) 후 
			// 결과(삽입된 행의 개수, int) 반환 받기
			result = pstmt.executeUpdate();
			
			
		} finally {
			// 6. 사용한 JDBC 객체 자원 반환(close)
			close(pstmt);
			
			
		}
		
		// 결과 저장용 변수에 저장된 값 반환
		return result;
	}



	public List<user> selectAll(Connection conn) throws Exception{
		
		List<user> userList = new ArrayList<user>();
		
		try {
			// 2. SQL 작성
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME, TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					ORDER BY USER_NO ASC
					""";
			
			// 3. Statement 생성
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. ? 알맞은 값 대입
			
			// 5. SQL(SELECT) 수행 후
			//    결과(Result Set) 반환 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과를 커서를 이용해서 1행씩 접근하여 컬럼값 얻어오기
			
			/* 몇 행이 조회될지 모른다 -> while
			 * 무조건 1행이 조회 된다  -> if
			 */
			
			
			while(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				
				String enrollDate = rs.getString("ENROLL_DATE");
				// - java.sql.Date 타입으로 값을 저장하지 않는 이유!
				//  -> TO_CHAR()를 이용해서 문자열로 변환했기 때문!
				
				// ** 조회된 값을 userList에 추가 **
				// -> user 객체를 생성해 조회된 값을 담고
				// 	  userList에 추가하기
				user user = new user(userNo, userId, userPw, userName, enrollDate);
				
				userList.add(user);
				
				// Result Set을 List에 옮겨 담는 이유

				// 1. List 사용이 편해서
				// -> 호환되는 곳이 많음
				
				// 2. 사용된 ResultSet은 DAO에 close 되기 때문
			}
			
			
		}finally {
			// 7. 사용한 JDBC 객체 자원 반환(close)
			close(rs);
			close(pstmt);
		}
		
		
		return userList;
	}


	/**
	 * 3. 이름으로 user 조회
	 * @param conn
	 * @return userList
	 */
	public List<user> selectName(Connection conn, String keyword) throws Exception{

		List<user> searchList = new ArrayList<user>();
		
		try {
			
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME, TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NAME LIKE '%' || ? || '%'
					ORDER BY USER_NO ASC
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				int userNo = rs.getInt("USER_NO");
				String userID = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				
				user user = new user(userNo, userID, userPw, userName, enrollDate);
				
				searchList.add(user);
			}
			
		}finally {
			close(rs);
			close(pstmt);
			
		}
		
		
		
		
		return searchList;
	}



	public List<user> selectNum(Connection conn, int num) throws Exception{

		// 1. 저장할 객체 생성
		List<user> numList = new ArrayList<user>();
		
		
		try {
			// 2. sql 작성
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME, TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NO = ?
					ORDER BY USER_NO ASC
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int userNo = rs.getInt("USER_NO");
				String userID = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				
				user user = new user(userNo, userID, userPw, userName, enrollDate);
				
				numList.add(user);
				
			}
		}finally {
			close(rs);
			close(pstmt);
			
		}
		
		return numList;
	}



	public int deleteNum(Connection conn, int num) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					DELETE
					FROM TB_USER
					WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		
		
		
		return result;
	}



	public int updateUser(Connection conn, String userId, String userPw) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					SELECT USER_NO
					FROM TB_USER
					WHERE USER_ID = ?
					AND USER_PW = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("\n** 수정할 이름을 입력하세요 : \n");
				String name = sc.nextLine();
				
				
				String SQL = """
						UPDATE TB_USER
						SET USER_NAME = ?
						WHERE USER_ID = ?
						""";
				
				pstmt = conn.prepareStatement(SQL);
				
				pstmt.setString(1, name);
				pstmt.setString(2, userId);
				
				result = pstmt.executeUpdate();
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}



	public int insert(Connection conn, String userId) throws Exception{

		int result = 0;
		
		try {
			
			String sql = """
					SELECT COUNT(*)
					FROM TB_USER
					WHERE USER_ID = ?				
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				
				System.out.print("설정할 PW : ");
				String userPw = sc.next();
				
				System.out.print("설정할 Name : ");
				String userName = sc.next();
				
				String SQL = """
						INSERT INTO TB_USER
						VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
						""";
				pstmt = conn.prepareStatement(SQL);
				
				pstmt.setString(1, userId);
				pstmt.setString(2, userPw);
				pstmt.setString(3, userName);
				
				result = pstmt.executeUpdate();
			}
		}finally {
			
		}
		
		
		return result;
	}



	/** 아이디 중복 확인
	 * @param conn
	 * @param userId
	 * @return count
	 * @throws Exception
	 */
	public int idCheck(Connection conn, String userId) throws Exception {
		
		int count = 0; // 결과 저장용 변수
		
		try {
			String sql = """
				SELECT COUNT(*)
				FROM TB_USER
				WHERE USER_ID = ?
			""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // COUNT(*) 그룹함수 결과 1행만 조회
				
				count = rs.getInt(1); // 조회된 컬럼 순서를 이용해	
									  // 컬럼 값 얻어오기
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return count;
	}



	

	
}
