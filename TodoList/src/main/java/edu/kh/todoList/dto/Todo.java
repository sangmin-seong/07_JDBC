package edu.kh.todoList.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Todo implements Serializable {

	
	private int   todoNo ;
	private String todoTitle;
	private String todoDetail;
	private boolean complete;
	private String enrollDate;
}
