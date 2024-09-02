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

@WebServlet("/todo/detail")
public class DetailTodoServlet extends HttpServlet{
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// 전달 받은 파라미터 얻어오기
			int index = Integer.parseInt(req.getParameter("index"));
			
			// 상세 조회하는 서비스 호출 후 결과 반환받기
			TodoListService service = new TodoListServiceImpl();
			
			Todo todo = service.todoDetailView(index);
			// index번째 todo가 없으면 null 반환
			
			
			// index번째 todo가 존재하지 않을 경우
			// ->  메인 페이지(/) redirect 후
			//	   "해당 index에 할 일이 존재하지 않습니다."
			//     alert출력
			if(todo == null) {
				
				// session에 message 세팅
				HttpSession session = req.getSession();
				session.setAttribute("message",
						"해당 index에 할 일이 존재하지 않습니다.");
				
				resp.sendRedirect("/");
				return;
			}
			
			// index번째 todo가 존재하는 경우
			// detail.jsp로 forward해서 응답
			req.setAttribute("todo", todo); // request scope에 세팅
			
			// JSP 파일 경로 기준은 webapp 폴더
			String path = "/WEB-INF/views/detail.jsp";
			
			// 요청발송자 이용해서 요청 위임
			req.getRequestDispatcher(path).forward(req, resp);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
