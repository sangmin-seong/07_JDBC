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
	
	@Override
	public int idCheck(String userId) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.idCheck(conn, userId);
		
		close(conn);
		
		return result;
	}
	
	
	@Override
	public user login(String userId, String userPw) throws Exception {
		
		Connection conn = getConnection();
		
		user loginUser = dao.login(conn, userId, userPw);
		
		close(conn);
		
		return loginUser;
	}
	
	
	@Override
	public List<user> selectAll() throws Exception {
		List<user> userList = new ArrayList<user>();
		
		Connection conn = getConnection();
		
		userList = dao.selectAll(conn);
		
		close(conn);
		
		
		return userList;
	}
	
}
