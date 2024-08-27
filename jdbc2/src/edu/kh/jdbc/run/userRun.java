package edu.kh.jdbc.run;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.view.userView;

public class userRun {
	public static void main(String[] args) {
		
		
		System.out.println(JDBCTemplate.getConnection());
		
		userView view = new userView();
		view.test();
		
	}
}
