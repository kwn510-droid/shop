<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop Login</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
  <%@ include file="/WEB-INF/view/inc/mainMenu.jsp" %>
  <div class="container">
	<h1>login</h1>
	<form id="loginForm" method="post" action="${pageContext.request.contextPath}/out/Login" novalidate>
	<div class="row">
		<div>
			<table>
				<tr>
					<td>id</td>
					<td><input type="text" name="id" id="id"></td>
				</tr>
				<tr><form id="loginForm" method="post" action="${pageContext.request.contextPath}/out/Login" novalidate>
					<td>pw</td>
					<td><input type="password" name="pw" id="pw"></td>
				</tr>
			</table>
		</div>
		<div>
			<input type="radio" name="customerOrEmpSel" class="customerOrEmpSel" value="customer" checked>customer
			<input type="radio" name="customerOrEmpSel" class="customerOrEmpSel" value="emp">emp
		</div>
	</div>
	</form>
	<a href="${pageContext.request.contextPath}/out/addMember">회원가입</a>
	</div>
	
<script>
</script>	
</body>
</html>