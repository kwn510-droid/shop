<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>탈퇴 회원 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">

<style>
table {
  width:100%; border-collapse:collapse; margin-top:20px; table-layout:fixed;
}
th, td {
  padding:10px 8px; text-align:center; border-bottom:1px solid #e5e7eb; white-space:nowrap;
}
th {
  background:#f9fafb; color:#374151; font-weight:600;
}
tr:hover { background:#f5f6ff; }

.pagination { text-align:center; margin-top:18px; }
.pagination a { margin:0 6px; color:#2563eb; text-decoration:none; }
.pagination .current { font-weight:bold; color:#111; }

.badge-del {
  display:inline-block; padding:4px 8px; border-radius:8px;
  background:#fef2f2; color:#dc2626; font-size:12px;
}
</style>
</head>

<body>

<!-- 직원 메뉴 include -->
<c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
<hr style="border:none;border-top:1px solid #eee;margin:12px 0 16px;">

<div class="container">
  <h1>탈퇴 회원 목록</h1>
  <p class="msg">총 ${totalCount}명의 탈퇴 내역이 있습니다.</p>

  <table>
    <thead>
      <tr>
        <th>아이디</th>
        <th>메모</th>
        <th>탈퇴일</th>
      </tr>
    </thead>

    <tbody>
      <c:choose>
        <c:when test="${empty list}">
          <tr><td colspan="3">기록이 없습니다.</td></tr>
        </c:when>

        <c:otherwise>
          <c:forEach var="o" items="${list}">
            <tr>
              <td>${o.id}</td>
              <td><span class="badge-del">${o.memo}</span></td>
              <td>${fn:substring(o.createdate, 0, 19)}</td>
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