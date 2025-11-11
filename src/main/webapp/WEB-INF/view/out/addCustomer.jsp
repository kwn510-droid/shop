<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
$(function(){
  // 아이디 중복확인
  $('#btnCheck').on('click', function () {
    const id = $('#id').val().trim();
    if (!id) { alert('아이디를 입력해주세요.'); return; }
    $('#msg').text('조회 중...').css({color:'#374151'}).show();

    $.ajax({
      url: '${pageContext.request.contextPath}/rest/checkCustomerId',
      type: 'GET',
      data: { id: id },
      dataType: 'json',
      cache: false
    })
    .done(function(res){
      if (res && res.exists) {
        $('#msg').text('❌ 이미 사용 중인 아이디입니다.').css('color','#e11d48').show();
      } else {
        $('#msg').text('✅ 사용 가능한 아이디입니다!').css('color','#16a34a').show();
      }
    })
    .fail(function(xhr){
      $('#msg').text('오류 발생(' + xhr.status + ')').css('color','red').show();
    });
  });

  // 간단 유효성 검사
  $('#joinForm').on('submit', function(e){
    const id = $('#id').val().trim();
    const pw = $('#pw').val().trim();
    const name = $('#name').val().trim();
    const phone = $('#phone').val().trim();
    const gender = $('input[name="gender"]:checked').val();

    if (id.length < 3) { alert('아이디는 3자 이상'); e.preventDefault(); return; }
    if (pw.length < 4) { alert('비밀번호는 4자 이상'); e.preventDefault(); return; }
    const rePhone = /^01[016789]-?\d{3,4}-?\d{4}$/;
    if (!rePhone.test(phone)) { alert('휴대폰 형식이 올바르지 않아'); e.preventDefault(); return; }
    if (!gender) { alert('성별을 선택해줘'); e.preventDefault(); return; }
  });
});
</script>
</head>
<body>
  <div class="container">
    <h1>회원가입</h1>

    <form id="joinForm" action="${pageContext.request.contextPath}/out/addCustomer" method="post">
      <!-- 아이디 -->
      <div class="row">
        <label for="id">아이디</label>
        <input type="text" id="id" name="customerId" placeholder="아이디 입력" required>
        <button type="button" id="btnCheck">중복확인</button>
      </div>
      <p id="msg" class="err" style="display:none;"></p>

      <!-- 비밀번호 -->
      <div class="row">
        <label for="pw">비밀번호</label>
        <input type="password" id="pw" name="customerPw" placeholder="비밀번호 입력" required>
      </div>

      <!-- 이름 -->
      <div class="row">
        <label for="name">이름</label>
        <input type="text" id="name" name="customerName" placeholder="이름 입력" required>
      </div>

      <!-- 휴대폰 -->
      <div class="row">
        <label for="phone">휴대폰</label>
        <input type="text" id="phone" name="customerPhone" placeholder="010-1234-5678" maxlength="13">
      </div>

      <!-- 성별 -->
      <div class="row">
        <label>성별</label>
        <div class="radio-wrap">
          <label><input type="radio" name="gender" value="M"> 남</label>
          <label style="margin-left:12px"><input type="radio" name="gender" value="F"> 여</label>
        </div>
      </div>

      <div class="actions">
        <button type="submit">회원가입</button>
        <button type="button" class="secondary" onclick="location.href='${pageContext.request.contextPath}/out/Login'">로그인</button>
      </div>
    </form>
  </div>
</body>
</html>