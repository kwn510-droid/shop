package controller;

import java.io.IOException;

import dao.NoticeDao;
import dto.Emp;
import dto.Notice;
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
		String noticeTitle = request.getParameter("noticeTitle");
		String noticeContent = request.getParameter("noticeContent");
		
		// 2) 세션에서 작성자(직원) 정보 가져오기.
		Emp loginEmp = (Emp)request.getSession().getAttribute("loginEmp");
		if(loginEmp==null) { // 로그인 안 된 경우
			response.sendRedirect(request.getContextPath() + "/emp/login");
			return;
		}
		
		// 3) 제목/내용 필수 체크
		if(noticeTitle == null || noticeTitle.isBlank() || noticeContent == null || noticeContent.isBlank()) {
			try {
				request.setAttribute("error", "제목과 내용 항목이 입력되지 않았습니다.");
				request.getRequestDispatcher("/WEB-INF/view/emp/addNotice.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		
		//4) DTO에 담기
		Notice notice = new Notice();
		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeContent(noticeContent);
		notice.setEmpCode(loginEmp.getEmpCode());
		
	    try {
	           // 5) DB INSERT
	           int row = noticeDao.insertNotice(notice);
	           
	          // 6) 정상 처리 → 목록으로 이동
	           if (row == 1) {
	               response.sendRedirect(request.getContextPath() + "/emp/noticeList");
	           } else { // 실패 → 다시 폼
	               request.setAttribute("error", "공지 등록에 실패했습니다.");
	               request.getRequestDispatcher("/WEB-INF/view/emp/addNotice.jsp").forward(request, response);
	           }
	       } catch (Exception e) {
	           e.printStackTrace();
	           response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	       }
    }	
}
