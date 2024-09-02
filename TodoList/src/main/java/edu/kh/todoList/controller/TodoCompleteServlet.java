package edu.kh.todoList.controller;

import java.io.IOException;
import java.util.HashMap;

import edu.kh.todoList.service.TodoListService;
import edu.kh.todoList.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("//todo/complete")
public class TodoCompleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			
			TodoListService service = new TodoListServiceImpl();
			
			int result = service.completeTodo();
			
			String message = null;
			
			if(result > 0 ) message = "변경 완료";
			else            message = "변경 실패";
			
			HttpSession session = req.getSession();
			session.setAttribute("message", message);
			
			resp.sendRedirect("/main");
			return;
					
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
