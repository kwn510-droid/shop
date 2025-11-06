<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">
<style>
  table { width:100%; border-collapse:collapse; margin-top:16px; table-layout:fixed; }
  th, td { padding:10px 8px; text-align:center; border-bottom:1px solid #e5e7eb; white-space:nowrap; }
  th { background:#f9fafb; color:#374151; font-weight:600; }
  tr:hover { background:#f5f6ff; }

  
  table th:nth-child(3), table td:nth-child(3) { width: 120px; } /* 이름 */
  table th:nth-child(5), table td:nth-child(5) { width: 100px; } /* 포인트 */

  .btn-danger { display:inline-block; padding:6px 10px; border-radius:8px; background:#e11d48; color:#fff; font-size:12px; }
  .btn-danger:hover { background:#be123c; }
  .pagination { text-align:center; margin-top:18px; }
  .pagination a { margin:0 6px; color:#2563eb; text-decoration:none; }
  .pagination .current { font-weight:bold; color:#111; }
</style>
</head>
<!-- 상단 메뉴 include -->
    <c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
    <hr style="border:none;border-top:1px solid #eee;margin:12px 0 16px;">

<body>

<div class="container">
  <h1>고객 관리</h1>
  <p class="msg">총 ${totalCount}명의 고객이 있습니다.</p>

  <table>
    <thead>
      <tr>
        <th>고객코드</th>
        <th>아이디</th>
        <th>이름</th>
        <th>전화번호</th>
        <th>포인트</th>
        <th>가입일</th>
        <th>강퇴</th>
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${empty customerList}">
          <tr><td colspan="7">데이터가 없습니다.</td></tr>
        </c:when>

        <c:otherwise>
          <c:forEach var="cst" items="${customerList}">
            <tr>
              <td>${cst.customerCode}</td>
              <td>${cst.customerId}</td>
              <td>${cst.customerName}</td>
              <td>${cst.customerPhone}</td>
              <td>${cst.point}</td>
              <td>${fn:substring(cst.createdate, 0, 10)}</td>
              <td>
                <a href="<c:url value='/emp/removeCustomerByEmp'>
                          <c:param name='customerId' value='${cst.customerId}'/>
                          <c:param name='memo' value='강제탈퇴(불량)'/>
                          <c:param name='currentPage' value='${currentPage}'/>
                          <c:param name='rowPerPage' value='${rowPerPage}'/>
                        </c:url>"
                   onclick="return confirm('정말 ${cst.customerId} 님을 강퇴하시겠습니까? ');"
                   class="btn-danger">강퇴</a>
              </td>
            </tr>
          </c:forEach>
        </c:otherwise>
      </c:choose>
    </tbody>
  </table>

  <div class="pagination">
    <a href="?currentPage=${currentPage-1 < 1 ? 1 : currentPage-1}&rowPerPage=${rowPerPage}">이전</a>
    <span class="current">${currentPage}</span>
    <a href="?currentPage=${currentPage+1 > lastPage ? lastPage : currentPage+1}&rowPerPage=${rowPerPage}">다음</a>
  </div>

</div>

</body>
</html>