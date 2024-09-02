// 쿼리스트링 값 얻어오기

// location.search : 쿼리스트링만 얻어오기
// URLSearchParams : 쿼리스트링을 객체 형태로 다룰 수 있는 객체
const params = new URLSearchParams(location.search);

// 쿼리스트링 중 key가 "index"인 파라미터의 값 얻어오기
// 할 일 완료 여부 변경, 수정, 삭제 시 사용
const index = params.get("index");

const goToList = document.querySelector("#goToList"); // 목록으로 버튼

// 목록으로 버튼이 클릭된 경우
goToList.addEventListener("click", ()=>{
  
  // "/"(메인페이지) 요청 (GET 방식)
  location.href ="/";
});



// 완료 여부 변경 버튼 클릭 시
const completeBtn = document.querySelector("#completeBtn");
completeBtn.addEventListener("click", ()=>{

  // 현재 보고 있는 Todo의 완료 여부 
  // (true) O <-> X (false) 변경 요청
  location.href = "/todo/complete?todoNo=" + todoNo;
});



// 삭제 버튼 클릭 시
const deleteBtn = document.querySelector("#deleteBtn");
deleteBtn.addEventListener("click", ()=> {
  
  // 현재 보고 있는 Todo의 삭제
  // 1. 정말 삭제 할 것인지 confirm() 이용 확인
  
  // 취소 클릭 시
  if( !confirm("정말 삭제하시겠습니까?") ) return;
  
  // 2. confirm() 확인 클릭 시 
  //    /todo/delete?index=인덱스번호 GET 방식 보내기
  location.href="/todo/delete?todoNo="+ todoNo;
});


// 수정 버튼 클릭 시
// 수정할 수 있는 화면 요청
const updateBtn = document.querySelector("#updateBtn");
updateBtn.addEventListener("click", ()=>{

  // 현재 보고 있는 Todo 수정
  // 제목, 상세내용 수정할 페이지
  location.href="/todo/update?todoNo="+ todoNo;
});