package edu.kh.jdbc.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import edu.kh.jdbc.dao.UserDao;
import edu.kh.jdbc.dao.UserDaoImpl;
import edu.kh.jdbc.dto.user;

public class UserServiceImpl implements UserService{
	
	//필드
	private UserDao dao = new UserDaoImpl();
	
	/**
	 * 사용자 등록
	 */
	@Override
	public int insertUser(user user) throws Exception{
		
		// 커넥션 객체 생성
		Connection conn = getConnection();
		
		// 데이터 가공
		
		// dao 호출 후 결과 반환
		int result = dao.insertUser(conn, user);
		
		// INSERT 수행 결과에 따른 트랜잭션 제어 처리
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		// 커넥션 반환
		close(conn);
		
		// 결과 반환
		return result;
	}
	
	/**
	 * 아이디 중복 체크
	 */
	@Override
	public int idCheck(String userId) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.idCheck(conn, userId);
		
		close(conn);
		
		return result;
	}
	
	/**
	 * 로그인
	 */
	@Override
	public user login(String userId, String userPw) throws Exception {
		
		Connection conn = getConnection();
		
		user loginUser = dao.login(conn, userId, userPw);
		
		close(conn);
		
		return loginUser;
	}
	
	/**
	 * 사용자 전체 조회
	 */
	@Override
	public List<user> selectAll() throws Exception {
		List<user> userList = new ArrayList<user>();
		
		Connection conn = getConnection();
		
		userList = dao.selectAll(conn);
		
		close(conn);
		
		
		return userList;
	}

	/**
	 * 이름으로 검색
	 */
	@Override
	public List<user> search(String searchId) throws Exception {
		
		Connection conn = getConnection();
		
		searchId = '%'+searchId + '%';
		
		
		List<user> userList = dao.search(conn, searchId);
		
		close(conn);
		
		return userList;
	}
	
	
	@Override
	public user selectUser(String userNum) throws Exception {
		
		Connection conn = getConnection();
		
		// 데이터 가공
		
		user user = dao.selectUser(conn, userNum);
		
		close(conn);
		
		return user;
	}
	
	
	@Override
	public int updateUser(user user) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateUser(conn, user);
		
		if(result > 0 ) commit(conn);
		else  			rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	
	
	
	@Override
	public int deleteUser(int userNo) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.deleteUser(conn, userNo);
		
		if(result > 0 ) commit(conn);
		else  			rollback(conn);
		
		close(conn);
		
		
		return result;
	}
}
