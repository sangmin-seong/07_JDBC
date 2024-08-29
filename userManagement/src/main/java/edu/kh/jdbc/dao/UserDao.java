package edu.kh.jdbc.dao;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.dto.user;

public interface UserDao {

	int insertUser(Connection conn, user user) throws Exception;

	int idCheck(Connection conn, String userId) throws Exception;

	user login(Connection conn, String userId, String userPw) throws Exception;

	List<user> selectAll(Connection conn) throws Exception;


}
