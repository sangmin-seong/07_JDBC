package edu.kh.todoList.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import edu.kh.todoList.dto.Todo;

public interface TodoListService {
	
	/**
	 * 할일 목록 반환	
	 * @return map
	 */
	public abstract Map<String, Object> todoListFullView() throws Exception;

	/**
	 * 할일 추가
	 * @param todo
	 * @return result
	 * @throws Exception
	 */
	public abstract int addTodo(Todo todo) throws Exception;

	/**
	 * 상세 조회
	 * @param todoNo
	 * @return todo
	 * @throws Exception
	 */
	public abstract Todo todoDetailView(int index) throws Exception;

	public abstract int completeTodo() throws;

}
