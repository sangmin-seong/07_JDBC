package edu.kh.todoList.controller;

import java.io.IOException;
import java.util.List;

import edu.kh.todoList.dto.Todo;
import edu.kh.todoList.service.TodoListService;
import edu.kh.todoList.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/todo/add")
public class TodoAddServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String todoTitle = req.getParameter("title");
		String todoDetail = req.getParameter("detail");
		
		
		try {
			
			Todo todo = new Todo();
			todo.setTodoTitle(todoTitle);
			todo.setTodoDetail(todoDetail);
			
			
			
			TodoListService service = new TodoListServiceImpl();
			
			int result = service.addTodo(todo);
			
			String message = null;
			
			if(result > 0) message = todoTitle + " 등록 성공";
			else           message = "등록 실패";
			
			HttpSession session = req.getSession();
			session.setAttribute("message", message);
			
			resp.sendRedirect("/main");
			return;
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
