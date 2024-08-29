package edu.kh.jdbc.service;

import java.util.List;

import edu.kh.jdbc.dto.user;

public interface UserService {
	
	/**	사용자 등록
	 * @param user
	 * @return result ": 1 || 0
	 * @throws Exception
	 */
	int insertUser(user user) throws Exception;

	/** 아이디 중복 여부 확인
	 * @param userId
	 * @return result (1 : 중복, 0 : 중복 X)
	 * @throws Exception
	 */
	int idCheck(String userId) throws Exception;

	
	
	/** 로그인
	 * @param userId
	 * @param userPw
	 * @return loginUser
	 * @throws Exception
	 */
	user login(String userId, String userPw) throws Exception;

	List<user> selectAll() throws Exception;

}
