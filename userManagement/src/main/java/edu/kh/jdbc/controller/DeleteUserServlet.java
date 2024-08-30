package edu.kh.jdbc.controller;

import java.io.IOException;

import edu.kh.jdbc.service.UserService;
import edu.kh.jdbc.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet{
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

			try {
				int userNo = Integer.parseInt(req.getParameter("userNo"));
				
				UserService service = new UserServiceImpl();
				
				int result = service.deleteUser(userNo);
				
				String message = null;
						
				if(result > 0) message = "삭제되었습니다.";
				else	 	   message = "해당 사용자가 존재하지 않습니다.";
				
				HttpSession session = req.getSession();
				
				session.setAttribute("message", message);
				
				
				resp.sendRedirect("/selectAll");
						
						
					 
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
}
