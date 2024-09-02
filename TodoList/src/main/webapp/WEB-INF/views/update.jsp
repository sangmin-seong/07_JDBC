<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${todo.title} 수정 페이지</title>
</head>
<body>
  
  <h4>할 일 수정</h4>
  
  <%-- 
    todo/update - Post 방식 요청
                -> Servlet의 doPost 방식 오버라이딩
   --%>
  <form action="/todo/update" method = "post" id = "updateForm">
    <div>
      제목 : <input type="text" name = "title" value = "${todo.title}">
    </div>
    <div>
    <%-- 사이에 떨어지면 떨어진대로 인식하여
        텍스트의 위치가 바뀔 수 있음
     --%>
     <textarea name="detail"
       cols="50" rows = "3" placeholder="상세내용" >${todo.detail}</textarea>
    </div>

    <button>수정</button>

    <input type="hidden" name = "index" value="${param.index}">
  </form>

<c:if test="${not empty sessionScope.message}">
    <script>
      alert("${message}");
    </script>
    
    <c:remove var="message" scope="session" />
  </c:if>

</body>
</html>