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
	<h1>noticeList</h1>
	<!-- emp meun include -->
	<c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
	<hr>
	
	<div>
		<a href="${pageContext.request.contextPath}/emp/addNotice">[공지추가]</a>
	</div>
	
	<table border="1">
		<tr>
			<th>noticeCode</th>
			<th>noticeTitle</th>
			<th>createdate</th>
		</tr>
		<c:forEach var="n" items="${list}">
			<tr>
				<td>${n.noticeCode}</td>
				<td><a href="">${n.noticeTitle}</a></td>
				<td>${n.createdate}</td>
			</tr>
		</c:forEach>
	</table>
	
	<!-- [처음으로][이전] 1 2 3 4 5 6 7 8 9 10 [다음][끝으로]-->
	<a href="${pageContext.request.contextPath}/emp/noticeList?currentPage=1">[처음으로]</a>
	<c:if test="${startPage > 1}">
		<a href="${pageContext.request.contextPath}/emp/noticeList?currentPage=${startPage-10}">[이전]</a>
	</c:if>
	<span>
		<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
			<c:if test="${currentPage == i}">
				[${i}]
			</c:if>
			<c:if test="${currentPage != i}">
				<a href="${pageContext.request.contextPath}/emp/noticeList?currentPage=${i}">[${i}]</a>
			</c:if>
		</c:forEach>
	</span>
	
	<c:if test="${lastPage != endPage}">
		<a href="${pageContext.request.contextPath}/emp/noticeList?currentPage=${startPage+10}">[다음]</a>
	</c:if>
	<a href="${pageContext.request.contextPath}/emp/noticeList?currentPage=${lastPage}">[끝으로]</a>
</body>
</html>