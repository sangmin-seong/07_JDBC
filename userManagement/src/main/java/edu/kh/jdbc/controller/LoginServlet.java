package edu.kh.jdbc.controller;

import java.io.IOException;

import edu.kh.jdbc.dto.user;
import edu.kh.jdbc.service.UserService;
import edu.kh.jdbc.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		try {
			
			// 전달받은 파라미터 얻어오기
			String userId = req.getParameter("userId");
			String userPw = req.getParameter("userPw");
			
			// id,pw가 일치하는 회원의 정보를 조회하는 
			// 서비스 호출 후 결과 반환
			UserService service = new UserServiceImpl();
			
			user loginUser = service.login(userId, userPw);
			
			HttpSession session = req.getSession();

			// 로그인 성공 시(조회 결과가 있는 경우)
			// sessionScope에 로그인 된 회원의 정보 세팅
			
			if(loginUser != null ) {
				session.setAttribute("loginUser", loginUser);
				
				session.setMaxInactiveInterval(180);
			}else {
				session.setAttribute("message", "ID 또는 PW 불일치");
			}
			
			// 메인페이지로  리다이렉트
			resp.sendRedirect("/main");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
