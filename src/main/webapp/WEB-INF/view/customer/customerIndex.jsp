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
	<%@ include file="/WEB-INF/view/inc/customerMenu.jsp" %>
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
	<h2>인기 상품목록</h2>
	<!-- 베스트 : 가장 많이 주문(주문완료)된 상품 5개 -->
	<div>
  <table border="1" width="70%">
    <tr>
      <c:forEach var="m" items="${bestList}" varStatus="st">
        <td>
          <div>
            <c:choose>
              <c:when test="${not empty m.filename}">
                <img src="${pageContext.request.contextPath}/upload/${m.filename}" style="max-width:120px;">
              </c:when>
              <c:otherwise>
                <img src="${pageContext.request.contextPath}/static/img/no-image.png" style="max-width:120px;">
              </c:otherwise>
            </c:choose>
          </div>
          <div>
            ${m.goodsName}<br>
            ${m.goodsPrice}
          </div>
        </td>
        <c:if test="${!st.last && st.count % 5 == 0}">
          </tr><tr>
        </c:if>
      </c:forEach>
    </tr>
  </table>
</div>
	<hr>
	
	<h2>상품목록</h2>
	<div>
		<table = border = "1" width = "70%">
			<tr>
				<!-- c:forEach varStatus : index(0~), count(1~), first(true/false), last(true/false) -->
				<c:forEach var="m" items="${goodsList}" varStatus="state">
					<td>
						<!-- image -->
						<div>
							<img src="${pageContext.request.contextPath}/upload/${m.filename}">
						</div>
						<!-- 이름, 가격 -->
						<div>
							${m.goodsName}<br>
							${m.goodsPrice}
							
							<br>${state.last}
						</div>
					</td>
					<c:if test="${state.last == false && state.count % 5 == 0}">
						</tr><tr>
					</c:if>
				</c:forEach>
				
			</tr>
		</table>
	</div>
</body>
</html>










