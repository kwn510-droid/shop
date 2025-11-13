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
	<h1>goodsOne</h1>
	<!-- customer meun include -->
	<c:import url="/WEB-INF/view/inc/customerMenu.jsp"></c:import>
	<hr>
	
	
	<div>
		${loginCustomer.customerName}님 반갑습니다.
		(point : ${loginCustomer.point})
		<a href="${pageContext.request.contextPath}/customer/customerLogout">로그아웃</a>
	</div>
	
	<div>
		<div>
			<img src="${pageContext.request.contextPath}/upload/${goods.filename}" width="500" height="500">
		</div>
		<div>
			<form id="myForm">
				<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
				<input type="hidden" name="goodsCode" value="${goods.goodsCode}">
				<table border="1">
					<tr>
						<td>goodsName</td>
						<td>${goods.goodsName}</td>
					</tr>
					<tr>
						<td>goodsPrice</td>
						<td>${goods.goodsPrice}</td>
					</tr>
					<tr>
						<td>pointRate</td>
						<td>${goods.pointRate}</td>
					</tr>
					<tr>
						<td>soldout</td>
						<td>${goods.soldout}</td>
					</tr>
					<tr>
						<td>수량</td>
						<td>
							<select name="cartQuantity">
								<c:forEach var="n" begin="1" end="10">
									<option value="${n}">${n}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<button id="cartBtn" type="button">장바구니</button><!-- insert cart -->
				<button id="orderBtn" type="button">바로주문</button><!-- 결제(화면) controller - insert orders  -->
			</form>
		</div>
	</div>
	<script>
		$('#cartBtn').click(function(){
			$('#myForm').attr('method', 'post');
			$('#myForm').attr('action', $('#contextPath').val()+'/customer/addCart');
			alert('cartBtn:' + $('#myForm').attr('method') + ',' + $('#myForm').attr('action')); // cart 액션
			$('#myForm').submit();
		});
		
		$('#orderBtn').click(function(){
			$('#myForm').attr('method', 'get');
			$('#myForm').attr('action', $('#contextPath').val()+'/customer/addOrders');
			alert('orderBtn: ' + $('#myForm').attr('method') + ',' + $('#myForm').attr('action')); // orders 화면
			$('#myForm').submit();
		});
	</script>
</body>
</html>