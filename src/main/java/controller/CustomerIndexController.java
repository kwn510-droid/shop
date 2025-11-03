package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class CustomerIndexControllter
 */
@WebServlet("/customer/customerIndex")
public class CustomerIndexController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 카드 데이터 (title, desc)
	    java.util.List<java.util.Map<String,String>> cards = new java.util.ArrayList<>();
	    cards.add(java.util.Map.of("title","상품목록","desc","검색/페이징 포함"));
	    cards.add(java.util.Map.of("title","주문목록","desc","기간/상태 필터"));
	    cards.add(java.util.Map.of("title","포인트","desc","적립/사용 내역"));
	    cards.add(java.util.Map.of("title","리뷰","desc","작성/수정/삭제"));

	    request.setAttribute("cards", cards);
	    request.getRequestDispatcher("/WEB-INF/view/customer/customerIndex.jsp")
	           .forward(request, response);
	  }
	}