<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>사원 추가</title>
  <link rel="stylesheet" href="<c:url value='/static/css/app.css'/>">
  <style>
    /* 폼 카드만 살짝 */
    .container{max-width:640px;margin:64px auto;padding:24px;background:#fff;border-radius:16px;border:1px solid #e5e7eb;box-shadow:0 8px 24px rgba(0,0,0,.06)}
    .row{display:flex;gap:12px;align-items:center;margin:12px 0}
    .row label{width:110px;color:#6b7280}
    input[type=text], input[type=password]{flex:1;padding:10px 12px;border:1px solid #e5e7eb;border-radius:10px;outline:none}
    input:focus{border-color:#2563eb;box-shadow:0 0 0 3px rgba(37,99,235,.15)}
    .actions{display:flex;gap:10px;margin-top:16px}
    button{padding:10px 14px;border:0;border-radius:10px;background:#2563eb;color:#fff;cursor:pointer}
    button.secondary{background:#e5e7eb;color:#111}
    .error{color:#e11d48;font-size:13px;margin-bottom:6px}
  </style>
</head>
<body>
  <div class="container">
    <h1 style="margin:0 0 16px">사원 추가</h1>
    <c:import url="/WEB-INF/view/inc/empMenu.jsp"/>

    <hr style="border:none;border-top:1px solid #eee;margin:12px 0 16px">

    <c:if test="${not empty error}">
      <div class="error">${error}</div>
    </c:if>

    <form method="post" action="<c:url value='/emp/addEmp'/>">
      <div class="row">
        <label for="empId">사원ID</label>
        <input id="empId" name="empId" type="text" placeholder="emp123" required>
      </div>

      <div class="row">
        <label for="empPw">비밀번호</label>
        <input id="empPw" name="empPw" type="password" placeholder="******" required>
      </div>

      <div class="row">
        <label for="empName">이름</label>
        <input id="empName" name="empName" type="text" placeholder="홍길동" required>
      </div>

      <div class="row">
        <label>상태</label>
        <label style="display:flex;align-items:center;gap:6px">
          <input type="radio" name="active" value="1" checked> 활성화
        </label>
        <label style="display:flex;align-items:center;gap:6px">
          <input type="radio" name="active" value="0"> 비활성화
        </label>
      </div>

      <div class="actions">
        <button type="submit">등록</button>
        <a href="<c:url value='/emp/empList'/>"><button type="button" class="secondary">취소</button></a>
      </div>
    </form>
  </div>
</body>
</html>