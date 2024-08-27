package edu.kh.jdbc.service;

import java.sql.Connection;
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
}
