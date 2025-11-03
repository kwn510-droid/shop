<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>shop - EMP</title>
<link rel="stylesheet" href="<c:url value='/static/css/app.css'/>">
</head>
<body>
  <!-- 상단 메뉴 인클루드 -->
  <%@ include file="/WEB-INF/view/inc/mainMenu.jsp" %> 
	  <div class="container">
    <h1>EMP</h1>
    <p class="msg">직원 전용 대시보드입니다.</p>

    <div class="grid">
      <c:forEach var="c" items="${cards}">
        <div class="card">
          <h2>${c.title}</h2>
          <p class="msg">${c.desc}</p>
        </div>
      </c:forEach>
    </div>

    <div class="actions" style="margin-top:18px">
      <a class="badge" href="<c:url value='/out/Login'/>">로그아웃</a>
      <a class="badge" href="<c:url value='/home'/>">홈으로</a>
    </div>
  </div>
</body>
</html>