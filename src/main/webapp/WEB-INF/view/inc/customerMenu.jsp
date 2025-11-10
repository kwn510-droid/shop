<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	<!-- 메인 메뉴 -->
	<a href="${pageContext.request.contextPath}/customer/customerIndex">[상품목록]</a>
	<!-- 상품목록 / 상세보기 / 주문 /  -->
	
	<a href="${pageContext.request.contextPath}/customer/customerInfo">[개인정보]</a>
	<!-- 
		개인정보열람
		/ 폰번호수정 
		/ 비밀번호수정() - 트랜잭션 : coustomer 비밀번호 수정 + pw_history에 비밀번호 입력(6개가 되었다 가장빠른데이터는 삭제) 
		/ 회원탈퇴() - 트랜잭션 : outid 입력 + customer 삭제 
	-->
	<a href="${pageContext.request.contextPath}/customer/addressList">[배송지관리]</a>
	<!-- 배송지목록 / 배송지추가(5개까지만 6번재 입력시 가장 먼저 입력된 주소지 삭제) / 삭제  -->
	
	<a href="${pageContext.request.contextPath}/customer/cartList">[장바구니]</a>
</div>