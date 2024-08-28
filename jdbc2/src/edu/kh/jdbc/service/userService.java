package edu.kh.jdbc.service;

// import static : 지정된 경로에 존재하는 static 구문을 모두 얻어와
// 				   클래스명.메서드명()이 아닌 메서드명() 만 작성해도 호출 가능하게 함
import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dao.userDao;
import edu.kh.jdbc.dto.user;


//Service : 비즈니스 로직 처리 
//- DB에 CRUD 후 결과 반환 받기
//+ DML 성공 여부에 따른 트랜잭션 제어 처리(commit/rollback)
// --> commit/rollback에는 Connection 객체가 필요하기 때문에
//    Connection 객체를 Service에서 생성 후
//    Dao에 전달하는 형식의 코드를 작성하게됨



public class userService {
	// 필드
	private userDao dao = new userDao();
	
	// 메서드
	
	/** 전달 받은 아이디와 일치하는 User 정보 반환
	 * @param  입력된 아이디
	 * @return 아이디가 일치하는 회원정보, 없으면 null
	 * */
	public user selectId(String input) {
		
		Connection conn = JDBCTemplate.getConnection();
		Scanner sc = new Scanner(System.in);
		
		// Dao 메서드 호출 후 결과 반환 받기
		user user = dao.selectId(conn, input);
		
		
		// 다 쓴 커넥션 닫기
		JDBCTemplate.close(conn);
		
		
		return user;
	}

	/**
	 * user 등록 서비스
	 * @param user : 입력 받은 id, pw, name
	 * @return 삽입 성공한 결과 행의 개수
	 * @throws Exception 
	 */
	public int insertUser(user user) throws Exception {
		
		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. 데이터 가공(할게 없으면 생략)
		
		// 3. Dao 메서드(INSERT 수행) 호출 후 결과 반환 받기
		//	  -> 결과 (삽입성공한 행의 개수, INT) 반환
		int result = dao.insertUser(conn, user);
		
		// 4. INSERT 수행 결과에 따라 트랜잭션 제어 처리
		if(result > 0) { // INSERT 성공
			commit(conn);
			
		}else { // INSERT 실패
			rollback(conn);
		}
		
		// 5. Connection 반환
		close(conn);
		
		// 6. 결과 반환
		return result;
	}

	/**
	 * user 전체 조회
	 * @return userList : 조회된 user 가 담긴 List
	 * @throws Exception
	 */
	public List<user> selectAll() throws Exception{

		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. 데이터 가공
		
		// 3. Dao 메서드 호출 후 결과 반환
		List<user> userList = dao.selectAll(conn);
		
		// 4. SELECT 수행 결과에 따라 트랜잭션 제어 처리
		// SELECT는 안해도 됨!!!
		
		// 5. Connection 반환
		close(conn);
		
		// 6. 결과 반환
		return userList;
	}

	/**
	 * user 이름으로 조회
	 * @return userList
	 * @throws Exception
	 */
	public List<user> selectName(String keyword) throws Exception{
		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. 데이터 가공
		
		// 3. Dao 호출 후 결과 반환
		List<user> searchList = dao.selectName(conn, keyword);
		
		// 4. SELECT 수행 결과에 따라 트랜잭션 제어 처리
		//   -> SELECT 는 안해도 된다
		
		// 5. Connection 반환
		close(conn);
		
		// 6. 결과 반환
		return searchList;
	}
	
	/**
	 * USER_NO로 조회
	 * @param num
	 * @return numList
	 * @throws Exception
	 */
	public List<user> selectNum(int num) throws Exception{
		
		// 1. conn 생성
		Connection conn = getConnection();
		
		// 2. 데이터 가공
		
		// 3. Dao 호출, 결과 반환
		List<user> numList = dao.selectNum(conn, num);
		
		// 4. 트랜잭션
		
		// 5. close
		close(conn);
		
		// 6. 결과 반환
		return numList;
	}

	
	/**
	 * DELETE 하기
	 * @param num
	 * @return result
	 * @throws Exception
	 */
	public int deleteNum(int num) throws Exception{
		
		
		Connection conn = getConnection();
		
		//
		
		int result = dao.deleteNum(conn, num);
		
		// 트랜잭션
		if(result > 0) { // DELETE 성공
			commit(conn);
			
		}else { // DELETE 실패
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	/**
	 * 
	 * @param userId
	 * @param userPw
	 * @return result
	 * @throws Exception
	 */
	public int updateUser(String userId, String userPw) throws Exception{

		Connection conn = getConnection();
		
		// 데이터 가공
		
		
		int result = dao.updateUser(conn, userId, userPw);
		
		// 트랜잭션
		if(result > 0) { // 성공
			commit(conn);
					
		}else { // 실패
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	
	
	

	public int insert(String userId) throws Exception {

		Connection conn = getConnection();
		
		//
		
		int result = dao.insert(conn, userId);
		
		// 트랜잭션
		
		if(result > 0 ) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
		
		
	}

	public int idCheck(String userId) throws Exception {
		
		Connection conn = getConnection();
		
		int count = dao.idCheck(conn, userId);
		
		close(conn);
		
		return count;
	}

	/**
	 * userList에 있는 모든 user INSERT 하기
	 * @param userList
	 * @return result : 삽입된 행의 갯수
	 * @throws Exception
	 */
	public int multiInsertUser(List<user> userList) throws Exception{

		Connection conn = getConnection();

		// 다중 INSERT 방법
		// 1) SQL을 이용한 다중 INSERT
		// 2) Java 반복문을 이용한 다중 INSERT  (이거 사용!!)
		int count = 0; // 삽입 성공한 행의 개수 count
		
		for(user user : userList) {
			int result = dao.insertUser(conn, user);

			count += result;
		}
		
		// 전체 삽입 성공시 commit / 아니면 rollback
		if(count == userList.size()) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		
		close(conn);
		
		return count;
	}
}
