package edu.kh.jdbc.view;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
	
	
	
	
	/** user 관리 프로그램 메인메뉴
	 */
	public void mainManu() {
		
		int input = 0;
		
		do {
			
			try {
				
				System.out.println("\n----- user 관리 프로그램 -----\n");
				System.out.println("1. user 등록하기(INSERT)");
				System.out.println("2. user 전체 조회(SELECT)");
				System.out.println("3. user 중 이름에 검색어가 포함된 회원 조회(SELECT)");
				System.out.println("4. USER_NO를 입력 받아 일치하는 회원 조회(SELECT)");
				System.out.println("5. USER_NO를 입력 받아 일치하는 회원 삭제(DELETE)");
				System.out.println("6. ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)");
				System.out.println("7. user 등록(아이디 중복 검사)");
				System.out.println("8. 여러 user 등록하기");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 :");
				input = sc.nextInt();				
				sc.nextLine();
				
				switch(input) {
				case 1 : insertUser(); break;
				case 2 : selectAll(); break;
				case 3 : selectName(); break;
				case 4 : selectNum(); break;
				case 5 : deleteNum(); break;
				case 6 : updateName(); break;
				case 7 : insertUser2(); break;
				case 8 : multiInsertUser(); break;
				case 0 : System.out.println("\n[프로그램 종료]\n");break;
				default: System.out.println("\n[메뉴번호만 입력하세요]\n");
				}
				System.out.println("\n-------------------------------------\n");

				
			}catch (InputMismatchException e) {
				// Scanner를 이용한 입력 시, 자료형이 잘못된 경우
				System.out.println("\n*** 잘못입력하셨습니다. ***\n");
				
				input = -1; // 잘못 입력해서 while문 멈추는 걸 방지
				sc.nextLine(); // 입력 버퍼에 남아 있는 잘못된 문자 제거
				
			}catch (Exception e) {
				// 발생되는 예외를 모두 해당 catch구문으로 모아서 처리
				e.printStackTrace();
			}
			
			
		}while(input!=0);
	}


	/**
	 * 1. user 등록
	 * @throws Exception 
	 */
	private void insertUser() throws Exception  {
		System.out.println("\n=== 1. user 등록 ===\n");
		
		System.out.print("ID : ");
		String userId = sc.next();
		
		System.out.print("PW : ");
		String userPw = sc.next();
		
		System.out.print("Name : ");
		String userName = sc.next();
		
		// 입력받은 값 3개를 한번에 묶어서 전달할 수 있도록 
		// user DTO 객체를 생성한 후 필드에 값을 세팅
		user user = new user();
		
		// lombok이용, 자동 생성된 setter 이용
		user.setUserId(userId);
		user.setUserPw(userPw);
		user.setUserName(userName);
		
		// 서비스 호출(INSERT) 후 
		// 결과 반환(삽입된 행의 개수, int) 받기
		int result = service.insertUser(user);
		
		// 반환된 결과에 따라 출력할 내용 선택
		if(result > 0 ) {
			System.out.println("\n " + userId + " 사용자가 등록 되었습니다.\n");
		}else {
			System.out.println("\n *** 등록 실패 ***\n");
		}
		
	}
	
	/**
	 * 2. user 전체 조회
	 */
	private void selectAll() throws Exception {
		System.out.println("\n=== 2. user 전체 조회(SELECT) ===\n");
	
		// 서비스 메서드(SELECT) 호출 후
		// 결과(List<user>) 반환 받기
		List<user> userList = service.selectAll();
		
		// 조회 결과가 없을 경우
		if(userList.isEmpty()) {
			System.out.println("조회 결과가 없습니다.");
			return;
		}
		// 향상된 for문
		for( user user : userList ) {
			System.out.println(user);
			// 자동으로 user.toString() 호출
		}
	}
	
	/**
	 * 3. user 이름으로 조회
	 */
	private void selectName() throws Exception{
		System.out.println("\n=== 3. user 이름으로 조회(SELECT) ===\n");
		
		
		System.out.println("조회하려는 이름을 입력하세요 : ");
		String keyword = sc.nextLine();
				
		// 서비스 메서드(SELECT) 호출 후
		// 결과(List<user>) 반환 받기
		List<user> searchList = service.selectName(keyword);
		
		// 조회 결과가 없을 경우
		if(searchList.isEmpty()) {
			System.out.println("검색 결과 없음");
			return;
		}
		
		// 향상된 for문
		for( user user : searchList ) {
			System.out.println(user);
			// 자동으로 user.toString() 호출
		}
		
	}

	
	/**
	 * 4. USER_NO로 조회하기
	 * @throws Exception
	 */
	private void selectNum() throws Exception{
		System.out.println("\n=== 4. USER_NO로 조회(SELECT) ===\n");
		
		
		System.out.println("조회하려는 번호를 입력하세요 : ");
		int num = sc.nextInt();
		
		List<user> numList = service.selectNum(num);
		
		if(numList.isEmpty()) {
			System.out.println("조회 결과가 없습니다.");
			return;
		}
		
		for(user user : numList) {
			System.out.println(user);
		}
	}
	
	/**
	 * 5. USER_NO로 삭제하기
	 * @throws Exception
	 */
	private void deleteNum() throws Exception{
		System.out.println("\n=== 5. USER_NO로 삭제(DELETE) ===\n");
		
		
		System.out.println("삭제하려는 번호를 입력하세요 : ");
		int num = sc.nextInt();
		
		int result = service.deleteNum(num);
		
		if(result > 0) {
			System.out.println("삭제 하였습니다.");
		}else {
			System.out.println("삭제 실패");
		}
		
		
	}
	
	
	
	private void updateName() throws Exception{
		System.out.println("\\n=== 6. USER_ID, USER_PW로 수정(UPDATE) ===\\n");
		
		System.out.println("아이디 입력 : ");
		String userId = sc.nextLine();
		
		System.out.println("비밀번호 입력 : ");
		String userPw = sc.nextLine();
		
		int result = service.updateUser(userId, userPw);
		
		
		if(result > 0 ) {
			System.out.println("\n" + userId + "님의 사용자 정보를 수정하였습니다.\n");
		}else {
			System.out.println("\n *** 존재하지 않은 아이디/비밀번호 입니다. ***\n");
			return;
		}
	}
	
	
	
	private void insertUser2() throws Exception{
		System.out.println("\\n=== 7. user 등록(아이디 중복 검사) ===\\n");
		
		System.out.print("ID : ");
		String userId = sc.next();
		
		// 서비스 호출(INSERT) 후 
		// 결과 반환(삽입된 행의 개수, int) 받기
		int result = service.insert(userId);
		
		// 반환된 결과에 따라 출력할 내용 선택
		if(result > 0 ) {
			System.out.println("\n " + userId + " 사용자가 등록 되었습니다.\n");
		}else {
			System.out.println("\n *** 등록 실패 ***\n");
		}
		
	}
	
	private void multiInsertUser() throws Exception{
		System.out.println("\\n=== 8. 여러 user 등록(아이디 중복 검사) ===\\n");
		
		System.out.println("등록할 user 수 : ");
		int num = sc.nextInt();
		sc.nextLine();
		
		List<user> userList = new ArrayList<user>();
		
		for(int i = 0; i < num; i++) {
		
			String userId = null; // 입력된 아이디를 저장할 변수
			
			while(true) {
				System.out.print((i+1) + "번째 userID : ");
				userId = sc.nextLine();
				
				// 입력받은 userId가 중복인지 검사하는
				// 서비스(SELECT) 호출 후 
				// 결과(int, 중복 == 1, 아니면 == 0) 반환 받기
				int count = service.idCheck(userId);
				
				if(count == 0) { // 중복이 아닌 경우
					System.out.println("사용 가능한 아이디 입니다");
					break;
				}
				
				System.out.println("이미 사용중인 아이디 입니다. 다시 입력 해주세요");
			}
			// 아이디가 중복이 아닌 경우 while 종료 후 pw, name 입력 받기
			System.out.print("PW : ");
			String userPw = sc.nextLine();
			
			System.out.print("Name : ");
			String userName = sc.nextLine();
			
			System.out.println("---------------------------------------------");
			
			// 입력 받은 ID, PW, NAME을 User 객체로 묶어서 서비스 호출
			user user = new user();
			user.setUserId(userId);
			user.setUserPw(userPw);
			user.setUserName(userName);
			
			// userList에 user 추가 하기
			userList.add(user);
		}
		
		// 입력 받은 모든 사용자 insert하는 서비스 호출
		int result = service.multiInsertUser(userList);
		
		// 전체 삽입 성공 시
		if( result == userList.size() ) {
			System.out.println("\n " + num + "명의 사용자가 등록 되었습니다.\n");
		}else {
			System.out.println("\n *** 등록 실패 ***\n");
		}
		
		
		
		
	}
}

