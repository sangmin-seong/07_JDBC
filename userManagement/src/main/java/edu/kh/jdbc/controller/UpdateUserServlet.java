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

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet{
	
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		try {
//			
//			String path = "/WEB-INF/views/updateUser.jsp";
//			req.getRequestDispatcher(path).forward(req, resp);
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		try {
			int userNo = Integer.parseInt(req.getParameter("userNo")); 
			String userPw = req.getParameter("userPw");
			String userName = req.getParameter("userName");
			
			user user = new user();
			user.setUserNo(userNo);
			user.setUserPw(userPw);
			user.setUserName(userName);
			
			UserService service = new UserServiceImpl();
			
			int result = service.updateUser(user);
			
			String message = null;
			
			if(result == 0) message = "수정 실패";
			else            message = userName + "님의 정보를 수정하였습니다.";
			
			HttpSession session = req.getSession();
			session.setAttribute("message", message);
			
			resp.sendRedirect("/selectUser?userNo="+ userNo);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
