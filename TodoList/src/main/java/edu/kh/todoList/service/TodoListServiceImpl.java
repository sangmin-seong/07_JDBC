package edu.kh.todoList.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static edu.kh.todoList.common.JDBCTemplate.*;

import edu.kh.todoList.common.JDBCTemplate;
import edu.kh.todoList.dao.TodoListDao;
import edu.kh.todoList.dao.TodoListDaoImpl;
import edu.kh.todoList.dto.Todo;

public class TodoListServiceImpl implements TodoListService{

	private TodoListDao dao = null;
	
	// 기본 생성자
	public TodoListServiceImpl() throws FileNotFoundException, IOException, ClassNotFoundException {
		// 객체 생성 시 TodoListDAO 객체 생성
		dao = new TodoListDaoImpl();
	}
	
	/**
	 * 
	 */
	@Override
	public Map<String, Object> todoListFullView() throws Exception{
	
			Connection conn = getConnection();
			
			// 할 일 목록 얻어오기 
			List<Todo> todoList = dao.todoListFullView(conn);
			
			
			close(conn);
			
			// 완료된 할 일 개수 카운트
			int completeCount = 0;
	
			for(Todo todo : todoList) {
				if(todo.isComplete()) {
					completeCount++;
				}
			}
			
			// 메서드에서 반환은 하나의 값 또는 객체 밖에 할 수 없기 때문에
			// Map이라는 컬렉션을 이용해 여러 값을 한 번에 담아서 반환
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("todoList", todoList);
			map.put("completeCount", completeCount);
			
		return map;
	}

	
	/**
	 * 할일 추가
	 */
	@Override
		public int addTodo(Todo todo) throws Exception {
			Connection conn = getConnection();
			
			int result = dao.addTodo(conn, todo);
			
			close(conn);
			
			return result;
		}
	
	/**
	 * 상세조회
	 * @throws Exception 
	 */
	@Override
	public Todo todoDetailView(int index) throws Exception {
			
			Connection conn = getConnection();
			
			Todo todo = dao.todoDetailView(conn, index);
			
			close(conn);
		
			return todo;
		}
}
