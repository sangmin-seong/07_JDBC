package edu.kh.jdbc.dao;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.dto.user;

public interface UserDao {
	
	/* 사용자 등록 */
	int insertUser(Connection conn, user user) throws Exception;

	/* 아이디 중복 검사 */
	int idCheck(Connection conn, String userId) throws Exception;
	
	/* 로그인 */ 
	user login(Connection conn, String userId, String userPw) throws Exception;

	/* 사용자 전체 조회 */
	List<user> selectAll(Connection conn) throws Exception;

	List<user> search(Connection conn, String searchID) throws Exception;

	user selectUser(Connection conn, String userNum) throws Exception;

	int updateUser(Connection conn, user user) throws Exception;

	int deleteUser(Connection conn, int userNo) throws Exception;






}
