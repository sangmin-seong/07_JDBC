package edu.kh.todoList.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import static edu.kh.todoList.common.JDBCTemplate.*;

import edu.kh.todoList.common.JDBCTemplate;
import edu.kh.todoList.dto.Todo;

public class TodoListDaoImpl implements TodoListDao{
	
	private List<Todo> todoList = new ArrayList<Todo>();
	
	private ObjectOutputStream oos = null;
	private ObjectInputStream  ois = null;
	
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	private Properties prop;
	
	
	
	// 기본 생성자
	public TodoListDaoImpl() throws FileNotFoundException, IOException, ClassNotFoundException {
	
			
			// 객체 생성 시 외부에 존재하는 sql.xml 파일 읽어와
			// prop에 저장
		try {
			String filePath = 
					JDBCTemplate.class.getResource("/edu/kh/todoList/sql/sql.xml").getPath();	
				
				// 지정된 경로의 XML 파일 내용을 읽어와
				// Properties 객체에 K:V 세팅
				prop = new Properties();
				prop.loadFromXML(new FileInputStream(filePath));
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	
	//-------------------------------------------------------------------------------------------------
	/**
	 * 할 일 목록 반환
	 */
	
	@Override
	public List<Todo> todoListFullView(Connection conn) throws Exception {
		try {
			
			String sql = prop.getProperty("selectAll");  
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int todoNo = rs.getInt("TODO_NO");
				String todoTitle = rs.getString("TODO_TITLE");
				String todoDetail = rs.getString("TODO_DETAIL");
				String todoComplete = rs.getString("TODO_COMPLETE");
				String enrollDate = rs.getString("ENROLL_DATE");
					
				boolean complete = false; 
				if(todoComplete == "O") complete = true;
				else                    complete = false; 
					
				Todo todo = new Todo(todoNo, todoTitle, todoDetail, complete, enrollDate);
					
				todoList.add(todo);
		}
			
				
		}finally {
			try {
				close(rs);
				close(pstmt);
					
			}catch (Exception e) {
				e.printStackTrace();
			}
			}
		
		return todoList;
	}
	
	
	@Override
	public int addTodo(Connection conn, Todo todo) throws Exception {
	
		int result = 0;
		
		try{
			String sql = prop.getProperty("addTodo");
		
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, todo.getTodoTitle());
		pstmt.setString(2, todo.getTodoDetail());
		
		result = pstmt.executeUpdate();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	@Override
	public Todo todoDetailView(Connection conn, int index) throws Exception {

		Todo todo = new Todo();
		
		try {
			
			String sql = prop.getProperty("detailTodo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, index);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int todoNum = rs.getInt("TODO_NO");
				String todoTitle = rs.getString("TODO_TITLE");
				String todoDetail = rs.getString("TODO_DETAIL");
				String todoComplete = rs.getString("TODO_COMPLETE");
				String enrollDate = rs.getString("ENROLL_DATE");
				
				boolean complete = false; 
				if(todoComplete == "O") complete = true;
				else                    complete = false; 
				
				todo = new Todo(todoNum, todoTitle, todoDetail, complete, enrollDate);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return todo;
	}
}
