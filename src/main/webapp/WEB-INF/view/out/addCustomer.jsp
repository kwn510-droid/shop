<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
$(document).ready(function() {
  $('#btnCheck').click(function() {
    const id = $('#id').val().trim();
    if (id === '') {
      alert('아이디를 입력해주세요.');
      return;
    }

    $.ajax({
      url: '${pageContext.request.contextPath}/rest/checkCustomerId',
      type: 'GET',
      data: { id: id },
      dataType: 'json',
      success: function(result) {
        if (result.exists) {
          $('#msg').text('❌ 이미 사용 중인 아이디입니다.').css('color', '#e11d48');
        } else {
          $('#msg').text('✅ 사용 가능한 아이디입니다!').css('color', '#16a34a');
        }
      },
      error: function() {
        $('#msg').text('오류 발생. 다시 시도해주세요.').css('color', 'red');
      }
    });
  });
});
</script>
</head>
<body>
  <div class="container">
    <h1>회원가입</h1>
    <form action="${pageContext.request.contextPath}/out/addCustomer" method="post">
      <div class="row">
        <label for="id">아이디</label>
        <input type="text" id="id" name="customerId" placeholder="아이디 입력" required>
        <button type="button" id="btnCheck">중복확인</button>
      </div>
      <p id="msg" class="err"></p>

      <div class="row">
        <label for="pw">비밀번호</label>
        <input type="password" id="pw" name="pw" placeholder="비밀번호 입력" required>
      </div>

      <div class="row">
        <label for="name">이름</label>
        <input type="text" id="name" name="name" placeholder="이름 입력" required>
      </div>

      <div class="actions">
        <<button type="button" class="secondary"
        onclick="location.href='${pageContext.request.contextPath}/out/addCustomer'">회원가입</button>
        <button type="button" class="secondary" onclick="location.href='${pageContext.request.contextPath}/login'">로그인</button>
      </div>
    </form>
  </div>
</body>
</html>