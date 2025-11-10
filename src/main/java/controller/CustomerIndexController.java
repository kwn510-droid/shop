package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import dao.GoodsDao;
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
            // 목록 & 개수
            List<Map<String,Object>> list  = goodsDao.selectGoodsList(beginRow, rowPerPage);
            int count = goodsDao.selectGoodsCount();

            // 마지막 페이지
            int lastPage = (count % rowPerPage == 0) ? (count/rowPerPage) : (count/rowPerPage) + 1;
            if (lastPage == 0) lastPage = 1;

            // [이전] 1 2 3 ... 10 [다음] 형태 (페이지바 10개 고정)
            int startPage = ((currentPage - 1) / 10 * 10) + 1;
            int endPage   = startPage + 9;
            if (lastPage < endPage) endPage = lastPage;

            // 인기 Top5 
            bestList = goodsDao.selectBestGoodsList();

            // 바인딩
            request.setAttribute("goodsList", list); // 목록은 goodsList로 쓰고,
            request.setAttribute("bestList",  bestList);

            request.setAttribute("currentPage", currentPage);
            request.setAttribute("lastPage",   lastPage);
            request.setAttribute("startPage",  startPage);
            request.setAttribute("endPage",    endPage);
            request.setAttribute("rowPerPage", rowPerPage);
            request.setAttribute("count",      count);

            request.getRequestDispatcher("/WEB-INF/view/customer/customerIndex.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
	
	java.util.List<java.util.Map<String,String>> cards = new java.util.ArrayList<>();
    cards.add(java.util.Map.of("title","상품목록","desc","검색/페이징 포함"));
    cards.add(java.util.Map.of("title","주문목록","desc","기간/상태 필터"));
    cards.add(java.util.Map.of("title","포인트","desc","적립/사용 내역"));
    cards.add(java.util.Map.of("title","리뷰","desc","작성/수정/삭제"));
    request.setAttribute("cards", cards);
 }
}
