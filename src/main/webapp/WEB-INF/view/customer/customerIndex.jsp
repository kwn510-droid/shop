<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>shop</title>
  <link rel="stylesheet" href="<c:url value='/static/css/app.css'/>">
</head>
<body>
	<!-- 상단 메뉴 인클루드 -->
	<%@ include file="/WEB-INF/view/inc/mainMenu.jsp" %>
	<div>
		   ${sessionScope.loginCustomer.customerName}님 반갑습니다.
		   (point: ${sessionScope.loginCustomer.point})
  			<a href="<c:url value='/customer/customerLogout'/>">로그아웃</a>
	</div>
	<div class="container">
	  <h1>Customer</h1>
	  <p class="msg">고객 전용 대시보드입니다.</p>
	
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