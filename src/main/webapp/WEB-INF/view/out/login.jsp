<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop Login</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
  <%@ include file="/WEB-INF/view/inc/mainMenu.jsp" %>
  <div class="container">
	<h1>login</h1>
	<form id="loginForm" method="post" action="${pageContext.request.contextPath}/out/Login" novalidate>
      <div class="row">
        <label for="id">id</label>
        <div style="flex:1">
          <input type="text" id="id" name="id" placeholder="아이디">
          <div id="err-id" class="err">아이디는 3자 이상 입력해 주세요.</div>
        </div>
      </div>

      <div class="row">
        <label for="pw">pw</label>
        <div style="flex:1">
          <input type="password" id="pw" name="pw" placeholder="비밀번호">
          <div id="err-pw" class="err">비밀번호는 4자 이상 입력해 주세요.</div>
        </div>
      </div>

      <div class="row">
        <label>role</label>
        <div class="radio-wrap">
          <label><input type="radio" name="customerOrEmpSel" value="customer" checked> customer</label>
          <label style="margin-left:12px"><input type="radio" name="customerOrEmpSel" value="emp"> emp</label>
        </div>
      </div>
      <div id="err-role" class="err" style="margin-left:90px;">역할을 선택해 주세요.</div>

      <div class="actions">
        <button type="submit">로그인</button>
        <a href="${pageContext.request.contextPath}/out/addCustomer"><button class="secondary" type="button">회원가입</button></a>
      </div>
    </form>
  </div>

<script>
$(function(){
  $('#loginForm').on('submit', function(e){
    let ok = true;
    const id = $('#id').val().trim();
    const pw = $('#pw').val().trim();
    const role = $('input[name="customerOrEmpSel"]:checked').val();

    $('.err').hide();

    if(id.length < 3){ $('#err-id').show(); ok = false; }
    if(pw.length < 4){ $('#err-pw').show(); ok = false; }
    if(!role){ $('#err-role').show(); ok = false; }

    if(!ok){ e.preventDefault(); }
  });
});
</script>
</body>
</html>