package edu.kh.todoList.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import edu.kh.todoList.dto.Todo;

public interface TodoListDao {
	
	/**
	 * 할 일 목록 반환
	 * @param conn
	 * @return todoList
	 */
	List<Todo> todoListFullView(Connection conn) throws Exception;

	
	/**
	 * 할일 추가
	 * @param conn
	 * @param todo
	 * @return result
	 */
	int addTodo(Connection conn, Todo todo) throws Exception;


	Todo todoDetailView(Connection conn, int index) throws Exception;


	
	
	
}
