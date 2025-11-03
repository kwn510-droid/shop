package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class empIndexControllter
 */
@WebServlet("/emp/empIndex")
public class EmpIndexController extends HttpServlet {
	  @Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {

	    var cards = new java.util.ArrayList<java.util.Map<String,String>>();
	    cards.add(java.util.Map.of("title","상품관리","desc","등록/수정/삭제"));
	    cards.add(java.util.Map.of("title","주문관리","desc","상태 변경, 검색"));
	    cards.add(java.util.Map.of("title","공지관리","desc","목록/입력/수정/삭제"));
	    cards.add(java.util.Map.of("title","이미지관리","desc","등록/수정/삭제"));

	    req.setAttribute("cards", cards);
	    req.getRequestDispatcher("/WEB-INF/view/emp/empIndex.jsp").forward(req, resp);
	  }
	}