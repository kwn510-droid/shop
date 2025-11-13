<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>shop</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.5.0"></script>
</head>

<body>
	<h1>stats</h1>
	<!-- customer meun include -->
	<c:import url="/WEB-INF/view/inc/empMenu.jsp"></c:import>
	<hr>


	<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">

	<!-- 월 입력: YYYY-MM -->
	<input type="month" id="fromYM" value="2025-01">
	~
	<input type="month" id="toYM"   value="2025-12">
	
	<br>
	
	<button type="button" id="btnTotalOrderCnt">특정년도의 월별 주문횟수(누적) : 선 차트</button>
	<button type="button" id="btnTotalRevenue">특정년도의 월별 주문매출(누적) : 선 차트</button>
	
	<button type="button" id="btnOrderCntByYM">특정년도의 월별 주문수량 : 막대 차트</button>
	<button type="button" id="btnOrderRevenueByYM">특정년도의 월별 주문매출 : 막대 차트</button>
	
	<button type="button" id="btnTop10CustCnt">고객별 주문횟수 1위 ~ 10위 : 막대 차트</button>
	<button type="button" id="btnTop10CustRevenue">고객별 총매출 1위 ~ 10위 : 막대 차트</button>
	<button type="button" id="btnTop10GoodsCnt">상품별 주문횟수 1위 ~ 10위 : 막대 차트</button>
	<button type="button" id="btnTop10GoodsRevenue">상품별 매출 1위 ~ 10위 : 막대 차트</button>
	<button type="button" id="btnTop10GoodsAvg">상품별 평균 리뷰평점 1위 ~ 10위 : 막대 차트</button>
	
	<button type="button" id="btnGenderOrderRevenue">성별 총주문 매출 : 파이 차트</button>
	<button type="button" id="btnGenderOrderCnt">성별 총주문 수량 : 파이 차트</button>
	
	<canvas id="myChart" style="width:100%;max-width:700px"></canvas>
	
	<script>
		let myChart = null;
		const ctxEl = document.getElementById('myChart');
		const base = $('#contextPath').val();
		const from = () => $('#fromYM').val();  // "YYYY-MM"
		const to   = () => $('#toYM').val();
		
		function reset(){ if(myChart) myChart.destroy(); }
		function drawBar(labels,data,title){
		  reset();
		  myChart = new Chart(ctxEl, {
		    type:'bar',
		    data:{ labels, datasets:[{ data }] },
		    options:{ plugins:{ legend:{display:false}, title:{display:true, text:title, font:{size:16}} },
		              scales:{ y:{ beginAtZero:true } } }
		  });
		}
		function drawLine(labels,data,title){
		  reset();
		  myChart = new Chart(ctxEl, {
		    type:'line',
		    data:{ labels, datasets:[{ label:title, data, fill:false }] },
		    options:{ plugins:{ legend:{display:false} } }
		  });
		}
		function drawPie(labels,data,title){
		  reset();
		  myChart = new Chart(ctxEl, {
		    type:'pie',
		    data:{ labels, datasets:[{ data }] },
		    options:{ plugins:{ legend:{display:true}, title:{display:true, text:title, font:{size:16}} } }
		  });
		}
		
		/* ===== 월별/누적 ===== */
		$('#btnOrderCntByYM').on('click', function(){
		  $.getJSON(base + '/emp/order', { fromYM: from(), toYM: to() }, function(res){
		    drawBar(res.map(m=>m.ym), res.map(m=>m.orderCnt), '월별 주문수량');
		  });
		});
		$('#btnOrderRevenueByYM').on('click', function(){
		  $.getJSON(base + '/emp/orderRevenueByMonth', { fromYM: from(), toYM: to() }, function(res){
		    drawBar(res.map(m=>m.ym), res.map(m=>m.orderRevenue), '월별 주문매출');
		  });
		});
		$('#btnTotalOrderCnt').on('click', function(){
		  $.getJSON(base + '/emp/totalOrder', { fromYM: from(), toYM: to() }, function(res){
		    drawLine(res.map(m=>m.ym), res.map(m=>m.totalOrderCnt),
		      from() + ' ~ ' + to() + ' 주문수량 추이(누적)');
		  });
		});
		$('#btnTotalRevenue').on('click', function(){
		  $.getJSON(base + '/emp/totalRevenue', { fromYM: from(), toYM: to() }, function(res){
		    drawLine(res.map(m=>m.ym), res.map(m=>m.totalOrderRevenue),
		      from() + ' ~ ' + to() + ' 주문매출 추이(누적)');
		  });
		});
		
		/* ===== 성별 파이 ===== */
		$('#btnGenderOrderRevenue').on('click', function(){
		  $.getJSON(base + '/emp/gender/orderRevenue', function(res){
		    drawPie(res.map(m=>m.gender), res.map(m=>m.orderRevenue), '성별 총주문 매출');
		  });
		});
		$('#btnGenderOrderCnt').on('click', function(){
		  $.getJSON(base + '/emp/gender/orderCnt', function(res){
		    drawPie(res.map(m=>m.gender), res.map(m=>m.orderCnt), '성별 총주문 수량');
		  });
		});
		
		/* ===== Top10 ===== */
		$('#btnTop10CustCnt').on('click', function(){
		  $.getJSON(base + '/emp/top10/customer/orderCnt', function(res){
		    drawBar(res.map(m=>m.customerId), res.map(m=>m.orderCnt), '고객 Top10 (주문수량)');
		  });
		});
		$('#btnTop10CustRevenue').on('click', function(){
		  $.getJSON(base + '/emp/top10/customer/orderRevenue', function(res){
		    drawBar(res.map(m=>m.customerId), res.map(m=>m.orderRevenue), '고객 Top10 (매출)');
		  });
		});
		$('#btnTop10GoodsCnt').on('click', function(){
		  $.getJSON(base + '/emp/top10/goods/orderCnt', function(res){
		    drawBar(res.map(m=>m.goodsName), res.map(m=>m.orderCnt), '상품 Top10 (주문수량)');
		  });
		});
		$('#btnTop10GoodsRevenue').on('click', function(){
		  $.getJSON(base + '/emp/top10/goods/orderRevenue', function(res){
		    drawBar(res.map(m=>m.goodsName), res.map(m=>m.orderRevenue), '상품 Top10 (매출)');
		  });
		});
		$('#btnTop10GoodsAvg').on('click', function(){
		  $.getJSON(base + '/emp/top10/goods/avgReviewScore', function(res){
		    drawBar(res.map(m=>m.goodsName), res.map(m=>m.avgReviewScore), '상품 Top10 (평균 리뷰 평점)');
		  });
		});
	</script>
</body>
</html>