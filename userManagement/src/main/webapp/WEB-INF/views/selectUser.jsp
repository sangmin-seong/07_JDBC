<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%-- 조회되는 사용자 ID --%>
  <title>${param.userId} 사용자 상세조회</title>
</head>
<body>
<%-- 조회되는 사용자 ID --%>
  <h1>${param.userId} 사용자 상세조회</h1>

  <hr>

  <form action ="/updateUser" method = "post">
    <table border = "1">
      <tr>
        <th>사용자 번호</th>
        <td id="userNoTd">${user.userNo}</td>
      </tr>
  
      <tr>
        <th>아이디</th>
        <td>${user.userId}</td>
      </tr>
  
      <tr>
        <th>비밀번호</th>
        <td>
          <input type="text" name="userPw" value = "${user.userPw}">  
        </td>
      </tr>
  
      <tr>
        <th>이름</th>
        <td>
          <input type="text" name="userName" value = "${user.userName}">
        </td>
      </tr>
  
      <tr>
        <th>등록일</th>
        <td>${user.enrollDate}</td>
      </tr>
    </table>
  
    <div>
      <button type = "submit" id = "updateBtn">수정</button>
      <button type = "button" id = "deleteBtn">삭제</button>
      <button type = "button" id = "goToList">목록으로 돌아가기</button>
    </div>

    <input type="hidden" name = "userNo" value = "${user.userNo}">
  </form>

    <%-- session에 message가 존재하는 경우 --%>
  <c:if test="${!empty sessionScope.message}" >
    <script>
      alert("${message}")
    </script>

    <c:remove var="message" scope="session"/>
  </c:if>

<script src = "/resources/js/selectUser.js"></script>
</body>
</html>