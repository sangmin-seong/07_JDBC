package edu.kh.jdbc.view;

import java.util.Scanner;

import edu.kh.jdbc.dto.user;
import edu.kh.jdbc.service.userService;

public class userView {
	
	// 필드
	private userService service = new userService();
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * JDBCTemplate 사용 테스트
	 */
	public void test() {
		// 입력된 ID와 일치하는 USER 정보 조회
		System.out.print("ID 입력 : ");
		String input = sc.nextLine();
		
		// 서비스 호출 후 결과 반환 받기
		user user = service.selectId(input); 
		
		// 결과 출력
		System.out.println(user);
				
	}
	
	
}
