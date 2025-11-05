<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>

<body>
  <h1>customerList</h1>

  <!-- customer 공통 메뉴 include (경로는 프로젝트에 맞게 조정) -->
  <c:import url="/WEB-INF/view/inc/customerMenu.jsp"></c:import>
  <hr>

  <div>
    <!-- 
      1) 고객 리스트
      2) 고객 추가 링크
      3) 활성/비활성은 모델에 없어서 제외
    -->
    총 ${fn:length(customerList)}명
    &nbsp;|&nbsp;
    <a href="<c:url value='/customer/addCustomer'/>">고객추가</a>

    <table border="1" cellpadding="6" cellspacing="0">
      <thead>
        <tr>
          <th>customerCode</th>
          <th>customerId</th>
          <th>customerName</th>
          <th>customerPhone</th>
          <th>point</th>
          <th>createdate</th>
        </tr>
      </thead>
      <tbody>
        <c:if test="${empty customerList}">
          <tr>
            <td colspan="6">데이터가 없습니다.</td>
          </tr>
        </c:if>

        <c:forEach var="cst" items="${customerList}">
          <tr>
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

    <!-- 페이징 (currentPage/rowPerPage를 컨트롤러에서 setAttribute 해줘) -->
    <div style="margin-top:12px;">
      <c:choose>
        <c:when test="${currentPage > 1}">
          <a href="<c:url value='/customer/list'>
                     <c:param name='currentPage' value='${currentPage-1}'/>
                     <c:param name='rowPerPage' value='${rowPerPage}'/>
                   </c:url>">[이전]</a>
        </c:when>
        <c:otherwise>
          [이전]
        </c:otherwise>
      </c:choose>

      &nbsp;${currentPage}&nbsp;

      <!-- 총 페이지를 모르면 일단 다음 페이지 링크는 항상 노출(컨트롤러에서 hasNext 주면 더 정확) -->
      <a href="<c:url value='/customer/list'>
                 <c:param name='currentPage' value='${currentPage+1}'/>
                 <c:param name='rowPerPage' value='${rowPerPage}'/>
               </c:url>">[다음]</a>
    </div>
  </div>
</body>
</html>