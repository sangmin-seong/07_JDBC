<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>사용자 전체 조회</title>
</head>
<body>
<h1>사용자 전체 조회</h1>

<c:if test = "${not empty param.searchId}">
  <h3>"${param.searchId}" 검색 결과</h3>
</c:if>

<form action="/search">
  ID 검색 : <input type="text" name = "searchId" placeholder = "포함되는 아이디 검색"
             value = "${param.searchId}" >
  <button id = "searchName">검색</button>
</form>

<hr>

<%-- 할 일 목록 출력 --%>
  <table id= "selectAll" border="1">
    <thead>
      <tr>
        <th>회원 번호</th>
        <th>
          아이디
        </th>
        <%-- <th>비밀번호</th> --%>
        <th>이름</th>
        <%-- <th>등록일</th> --%>
      </tr>
    </thead>

    <tbody>
      <%-- 조회 결과 있을 경우 --%>
      <c:if test = "${not empty userList}">
        <c:forEach items="${userList}" var="user" varStatus = "vs">
          <tr>
            <th>${user.userNo}</th>
            <td>
              <a href = "/selectUser?userNo=${user.userNo}" id = "selectUser">${user.userId}</a>
            </td>
            <%-- <td>${user.userPw}</td> --%>
            <td>${user.userName}</td>
            <%-- <td>${user.enrollDate}</td> --%>
          </tr>
        </c:forEach>
      </c:if>

      <%-- 조회 결과 없을 경우 --%>
      <c:if test="${empty userList}" >
        <tr>
          <th colspan ="5">조회 결과가 없습니다.</th>
        </tr>
      </c:if>
  
</body>
</html>