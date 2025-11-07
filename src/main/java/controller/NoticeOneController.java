package controller;

import dao.NoticeDao;
import dto.Notice;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/*
 * 공지사항 상세보기 컨트롤러
 * /emp/noticeOne?noticeCode=번호 형태로 요청됨
 */

@WebServlet("/emp/noticeOne")
public class NoticeOneController extends HttpServlet {
    private final NoticeDao noticeDao = new NoticeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
    	// 1) 파라미터 받아오기
    	String strNoticeCode = request.getParameter("noticeCode");
        // 2) 유효성 검사(숫자 아닌 경우 예외 처리)
    	if (strNoticeCode == null || !strNoticeCode.matches("\\d+")) { 
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "존재하지 않는 페이지입니다.");
            return;
        }
        int noticeCode = Integer.parseInt(strNoticeCode);

        try {
        	// 3) dao에서 데이터 조회
            Notice notice = noticeDao.selectNoticeOne(noticeCode);
            // 4) 결과없음
            if (notice == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "존재하지 않음.");
                return;
            }
                   
            request.setAttribute("notice", notice);
            request.getRequestDispatcher("/WEB-INF/view/emp/noticeOne.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // 콘솔 출력
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}