package edu.kh.jdbc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.dto.user;
import edu.kh.jdbc.service.UserService;
import edu.kh.jdbc.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/selectAll")
public class SelectAllServlet extends HttpServlet{
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		try {
			
			UserService service = new UserServiceImpl();
			
			List<user> userList  = service.selectAll();
			
			if(userList.isEmpty()) {
				System.out.println("조회 결과가 없습니다.");
				return;
				
			}
			
			req.setAttribute("userList", userList);
			
			String path = "/WEB-INF/views/selectAll.jsp";
			req.getRequestDispatcher(path).forward(req, resp);
		
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
