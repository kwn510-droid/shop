<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	<!-- emp 메뉴 -->
	<a href="${pageContext.request.contextPath}/emp/empList">[사원관리]</a>
	<a href="${pageContext.request.contextPath}/emp/customerList">[고개관리]</a>
	<a href="${pageContext.request.contextPath}/emp/outidList">[탈퇴ID관리]</a>
	<a href="${pageContext.request.contextPath}/emp/goodsList">[상품관리]</a>
	<a href="${pageContext.request.contextPath}/emp/ordersList">[주문관리]</a>
	<a href="${pageContext.request.contextPath}/emp/noticeList">[공지관리]</a>
	<a href="${pageContext.request.contextPath}/emp/questionList">[주문질문관리]</a>
	<!-- 
		(select q.*, qc.comment_code, nvl(qc.comment_memo, '답변하기') memo
		from question q left outer join question_comment qc
		on q.question_code = qc.question_code) -- X orders X goods 
	 -->
	
	<a href="${pageContext.request.contextPath}/emp/reviewList">[상품리뷰관리]</a>
	<!-- 
		select *
		from review
		order by order_code desc -- x orders x goods x customer + score에 오름차순/내림차순
	 -->
	
	<a href="${pageContext.request.contextPath}/emp/stats">[통계자료]</a><!-- AJax -->
</div>