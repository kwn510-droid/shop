package controller;

import java.io.IOException;

import dao.NoticeDao;
import dto.Emp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 공지사항 추가 컨트롤러
 */
@WebServlet("/emp/addNotice")
public class AddNoticeController extends HttpServlet {
	private NoticeDao noticeDao = new NoticeDao();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.getRequestDispatcher("/WEB-INF/view/emp/addNotice.jsp").forward(request, response);	
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	// 공지 insert 실행
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 폼데이터 
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		// 2) 세션에서 작성자(직원) 정보 가져오기.
		Emp loginEmp = (Emp)request.getSession().getAttribute("loginEmp");
		if(loginEmp==null) {
			
		}
		doGet(request, response);
	}

}
