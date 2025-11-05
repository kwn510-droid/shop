<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객 목록</title>

<!-- 기존 app.css 연결 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>

<body>
  <!-- 상단 메뉴 -->
  <div class="nav">
    <a href="<c:url value='/emp/empIndex'/>">직원홈</a>
    <a href="<c:url value='/customer/list'/>" class="active">고객관리</a>
    <a href="<c:url value='/emp/goodsList'/>">상품관리</a>
    <a href="<c:url value='/emp/orderList'/>">주문관리</a>
  </div>

  <div class="container">
    <h1>고객 관리</h1>
    <p class="msg">총 ${totalCount}명의 고객이 있습니다.</p>

    <div style="margin-bottom:12px; text-align:right;">
      <a href="<c:url value='/customer/addCustomer'/>" class="badge">+ 고객추가</a>
    </div>

    <table border="0" cellpadding="8" cellspacing="0" style="width:100%; border-collapse:collapse;">
      <thead style="background:#f3f4f6;">
        <tr style="border-bottom:1px solid #e5e7eb;">
          <th align="left">고객코드</th>
          <th align="left">아이디</th>
          <th align="left">이름</th>
          <th align="left">전화번호</th>
          <th align="left">포인트</th>
          <th align="left">가입일</th>
        </tr>
      </thead>
      <tbody>
        <c:if test="${empty customerList}">
          <tr><td colspan="6" style="text-align:center; color:#9ca3af;">데이터가 없습니다.</td></tr>
        </c:if>

        <c:forEach var="cst" items="${customerList}">
          <tr style="border-bottom:1px solid #f1f5f9;">
            <td>${cst.customerCode}</td>
            <td>${cst.customerId}</td>
            <td>${cst.customerName}</td>
            <td>${cst.customerPhone}</td>
            <td>${cst.point}</td>
            <td>${cst.createdate}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>

    <!-- 페이징 -->
    <div style="margin-top:18px; text-align:center;">
      <c:choose>
        <c:when test="${currentPage > 1}">
          <a href="<c:url value='/emp/customerList'>
                     <c:param name='currentPage' value='${currentPage-1}'/>
                     <c:param name='rowPerPage' value='${rowPerPage}'/>
                   </c:url>">[이전]</a>
        </c:when>
        <c:otherwise>[이전]</c:otherwise>
      </c:choose>

      &nbsp;<span class="badge">${currentPage}</span>&nbsp;

      <a href="<c:url value='/emp/customerList'>
                 <c:param name='currentPage' value='${currentPage+1}'/>
                 <c:param name='rowPerPage' value='${rowPerPage}'/>
               </c:url>">[다음]</a>
    </div>
  </div>
</body>
</html>