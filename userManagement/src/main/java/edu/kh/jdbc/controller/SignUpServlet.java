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

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/signUp.jsp";
		req.getRequestDispatcher(path).forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 제출된 파라미터 가져오기
		String userId = req.getParameter("userId");
		String userPw = req.getParameter("userPw");
		String userName = req.getParameter("userName");
		
		try {
			// 전달 받은 파라미터를 한번에 저장할 User DTO 객체 생성
			user user = new user();
			user.setUserId(userId);
			user.setUserPw(userPw);
			user.setUserName(userName);
			
			// 서비스(INSERT) 호출 후
			// 결과(삽입된 행의 개수, int) 반환 받기
			UserService service = new UserServiceImpl();
			
			int result = service.insertUser(user);
			
			// 결과에 따른 응답처리
			String message = null;
			
			if(result > 0) message = userId + " 사용자 등록 성공";
			else		   message = "사용자 등록 실패";
			
			// page, request, session, application 중
			// session을 이용해서 message 값 전달
			HttpSession session = req.getSession();
			session.setAttribute("message", message);
			
			// 메인페이지로 redirect
			resp.sendRedirect("/main");
			return;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
