<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>

<body>
	<h1>addAddress</h1>
	<!-- customer meun include -->
	<c:import url="/WEB-INF/view/inc/customerMenu.jsp"></c:import>
	<hr>
	
	<div>
		<form>
			<div>		
				기본주소 : <input type="text" name="address1" id="address1" readonly>
				<button type="button">주소검색</button><!-- OPEN API 호출(kakao 주소 API) -->
				<br>
				상세주소 : <input type="text" name="address2" id="address2">
			</div>
			<button type="button">배송지추가</button>
		</form>
	</div>
</body>
</html>