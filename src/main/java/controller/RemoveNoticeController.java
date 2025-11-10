package controller;

import java.io.IOException;

import dao.NoticeDao;
import dto.Notice;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemoveNoticeController
 */
@WebServlet("/emp/RemoveNotice")
public class RemoveNoticeController extends HttpServlet {
    private NoticeDao noticeDao = new NoticeDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String strNoticeCode = request.getParameter("noticeCode");

        // 파라미터 검증
        if (strNoticeCode == null || !strNoticeCode.matches("\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "noticeCode 파라미터 오류");
            return;
        }

        // DTO 생성
        Notice notice = new Notice();
        notice.setNoticeCode(Integer.parseInt(strNoticeCode));

        try {
            // DELETE 실행
            int row = noticeDao.deleteNotice(notice);

            if (row == 1) {
                response.sendRedirect(request.getContextPath() + "/emp/noticeList");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "삭제할 공지가 존재하지 않음");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}