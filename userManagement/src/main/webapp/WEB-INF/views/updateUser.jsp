<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>사용자 정보 수정</title>
</head>
<body>

<h3>${user.userId} 정보 수정</h3>

<hr>

<table>
  <tr>
    <th>이름</th>
    <td>
      <input type="text" id = "userId" value = "${user.userName}">
    </td>

    <th>비밀번호</th>
    <td>
      <input type="text" id = "userPw" value = "${user.userPw}">    
    </td>
  </tr>

  <div>
    <button id = update> 수정 </button>
    <button id = return> 돌아가기 </button>
  </div>

</table>


  
</body>
</html>