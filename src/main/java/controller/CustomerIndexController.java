package controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import dao.GoodsDao;
import dto.Goods;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CustomerIndexControllter
 */
@WebServlet("/customer/customerIndex")
public class CustomerIndexController extends HttpServlet {
	
	private GoodsDao goodsDao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int rowPerPage = 20;
        int beginRow = (currentPage - 1) * rowPerPage; 

        goodsDao = new GoodsDao();
        // 기본값(빈 리스트 준비)
        List<Map<String,Object>> goodsList;
        List<Map<String,Object>> bestList;

        try {
            goodsList = goodsDao.selectGoodsList(beginRow, rowPerPage);
            bestList  = goodsDao.selectBestGoodsList();
        } catch (Exception e) {
            e.printStackTrace();
            goodsList = java.util.Collections.emptyList();
            bestList  = java.util.Collections.emptyList();
        }

        request.setAttribute("goodsList", goodsList);
        request.setAttribute("bestList",  bestList);
        request.setAttribute("currentPage", currentPage);


	    java.util.List<java.util.Map<String,String>> cards = new java.util.ArrayList<>();
	    cards.add(java.util.Map.of("title","상품목록","desc","검색/페이징 포함"));
	    cards.add(java.util.Map.of("title","주문목록","desc","기간/상태 필터"));
	    cards.add(java.util.Map.of("title","포인트","desc","적립/사용 내역"));
	    cards.add(java.util.Map.of("title","리뷰","desc","작성/수정/삭제"));
	    request.setAttribute("cards", cards);
	    
	    
	 // 6) JSP 포워딩
        request.getRequestDispatcher("/WEB-INF/view/customer/customerIndex.jsp")
                .forward(request, response);
    }
}
