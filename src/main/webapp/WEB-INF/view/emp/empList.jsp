<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>shop | empList</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

  <style>
  /* Layout & reset */
  *{box-sizing:border-box}
  body{font-family:system-ui,-apple-system,'Noto Sans KR',sans-serif;margin:0;background:#f7f8fa;color:#111}
  .container{max-width:680px;margin:64px auto;padding:24px;background:#fff;border-radius:16px;box-shadow:0 8px 24px rgba(0,0,0,.06)}

  /* Headings / text */
  h1{margin:0 0 16px;font-size:24px}
  p.msg{font-size:13px;color:#6b7280;margin-top:8px}

  /* Inputs */
  .row{display:flex;gap:12px;align-items:center;margin:10px 0}
  .row label{width:90px;color:#6b7280}
  input[type="text"],input[type="password"]{flex:1;padding:10px 12px;border:1px solid #e5e7eb;border-radius:10px;outline:none}
  input:focus{border-color:#2563eb;box-shadow:0 0 0 3px rgba(37,99,235,.18)}
  .err{color:#e11d48;font-size:12px;margin-top:6px;display:none}

  /* Buttons / links */
  .actions{display:flex;gap:10px;margin-top:14px}
  button{padding:10px 14px;border:0;border-radius:10px;background:#2563eb;color:#fff;cursor:pointer}
  button.secondary{background:#e5e7eb;color:#111}
  a{color:#2563eb;text-decoration:none}
  .badge{display:inline-block;padding:2px 8px;border-radius:999px;font-size:12px;background:#eef2ff;color:#3730a3}

  /* Role cards */
  .grid{display:grid;grid-template-columns:1fr 1fr;gap:16px}
  .card{border:1px solid #e5e7eb;border-radius:14px;padding:16px}
  .card h2{font-size:18px;margin:0 0 8px}

  /* Top menu (optional inc) */
  .nav{position:sticky;top:0;background:#ffffffd9;backdrop-filter:blur(6px);border-bottom:1px solid #eee;padding:10px 16px;display:flex;gap:12px}
  .nav a{color:#111}
  .nav a.active{color:#2563eb;font-weight:600}

  /* Table (추가) */
  .table-wrap{overflow:auto;border:1px solid #e5e7eb;border-radius:12px;margin-top:12px}
  table{width:100%;border-collapse:separate;border-spacing:0}
  thead th{background:#f8fafc;text-align:left;font-weight:600;font-size:14px;color:#374151;border-bottom:1px solid #e5e7eb;padding:12px 14px}
  tbody td{padding:12px 14px;border-bottom:1px solid #f1f5f9;font-size:14px}
  tbody tr:hover{background:#f9fbff}
  .pager{display:flex;gap:10px;align-items:center;justify-content:center;margin-top:14px}
  .pill{display:inline-block;padding:3px 10px;border-radius:999px;font-size:12px;border:1px solid #e5e7eb;background:#f8fafc;color:#111}
  .pill.on{background:#dcfce7;border-color:#86efac;color:#166534}
  .pill.off{background:#fee2e2;border-color:#fecaca;color:#991b1b}
  </style>
</head>
<body>
  <div class="container">
    <h1>empList</h1>

    <!-- 상단 메뉴 include -->
    <c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
    <hr style="border:none;border-top:1px solid #eee;margin:12px 0 16px;">

    <!-- 액션 라인 -->
    <div class="actions" style="justify-content:space-between;align-items:center">
      <div class="badge" style="background:#f3f4f6;color:#111;border:1px solid #e5e7eb">총 ${totalCount}명</div>

      <c:url var="addEmpUrl" value="/emp/addEmp"/>
      <a class="badge" href="${addEmpUrl}" style="background:#2563eb;color:#fff;border-color:#2563eb">＋ 사원추가</a>
    </div>

    <!-- 목록 -->
    <c:choose>
      <c:when test="${empty empList}">
        <p class="msg">데이터가 없습니다.</p>
      </c:when>
      <c:otherwise>
        <div class="table-wrap">
          <table>
            <thead>
              <tr>
                <th style="width:110px">empCode</th>
                <th style="width:180px">empId</th>
                <th>empName</th>
                <th style="width:200px">createdate</th>
                <th style="width:140px;text-align:center">상태</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="e" items="${empList}">
                <tr>
                  <td>${e.empCode}</td>
                  <td>${e.empId}</td>
                  <td>${e.empName}</td>
                  <td>${e.createdate}</td>
                  <td style="text-align:center">
				  <button type="button"
				          class="pill ${e.active == 1 ? 'on' : 'off'} js-toggle-active"
				          data-empcode="${e.empCode}"
				          data-active="${e.active}">
				    <c:out value="${e.active == 1 ? '활성화' : '비활성화'}"/>
				  </button>
				</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </c:otherwise>
    </c:choose>

    <!-- 페이징 -->
    <div class="pager">
      <c:choose>
        <c:when test="${currentPage > 1}">
          <c:url var="prevUrl" value="/emp/empList">
            <c:param name="currentPage" value="${currentPage-1}"/>
            <c:param name="rowPerPage" value="${rowPerPage}"/>
          </c:url>
          <a href="${prevUrl}">이전</a>
        </c:when>
        <c:otherwise><span style="color:#9ca3af">이전</span></c:otherwise>
      </c:choose>

      <span>${currentPage} / ${lastPage}</span>

      <c:choose>
        <c:when test="${currentPage < lastPage}">
          <c:url var="nextUrl" value="/emp/empList">
            <c:param name="currentPage" value="${currentPage+1}"/>
            <c:param name="rowPerPage" value="${rowPerPage}"/>
          </c:url>
          <a href="${nextUrl}">다음</a>
        </c:when>
        <c:otherwise><span style="color:#9ca3af">다음</span></c:otherwise>
      </c:choose>
    </div>
  </div>
</body>
<script>
$(document).on('click', '.js-toggle-active', function() {
  const $btn = $(this);
  const empCode = $btn.data('empcode');
  const currentActive = Number($btn.data('active')); // 0 or 1

  $.ajax({
    url: '<c:url value="/emp/api/active"/>',
    type: 'POST',
    data: { empCode: empCode, currentActive: currentActive },
    success: function(res) {
      // res = { ok:true, active: 0|1, message:"…" }
      if (res && res.ok) {
        // 1) UI 토글
        const newActive = Number(res.active);
        $btn.data('active', newActive);
        $btn.toggleClass('on', newActive === 1)
            .toggleClass('off', newActive === 0)
            .text(newActive === 1 ? '활성화' : '비활성화');

        // 2) 안내
        alert(res.message || (newActive === 1 ? '활성화 완료' : '비활성화 완료'));
      } else {
        alert(res && res.message ? res.message : '처리에 실패했습니다.');
      }
    },
    error: function() {
      alert('서버 오류가 발생했습니다.');
    }
  });
});
</script>
</html>