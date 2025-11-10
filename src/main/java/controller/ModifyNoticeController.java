package controller;

import dao.NoticeDao;
import dto.Notice;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/*
 * 공지 수정 컨트롤러
 * GET : 기존 데이터 보여주는 폼
 * POST : UPDATE 실행
 */
@WebServlet("/emp/modifyNotice")
public class ModifyNoticeController extends HttpServlet {
    private final NoticeDao noticeDao = new NoticeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 공지 번호 받기
        String strNoticeCode = request.getParameter("noticeCode");

        if (strNoticeCode == null || !strNoticeCode.matches("\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "noticeCode 파라미터 오류");
            return;
        }

        try {
            // 기존 데이터 가져오기
            Notice n = noticeDao.selectNoticeOne(Integer.parseInt(strNoticeCode));
            request.setAttribute("n", n);
            request.getRequestDispatcher("/WEB-INF/view/emp/modifyNotice.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String strNoticeCode = request.getParameter("noticeCode");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        if (strNoticeCode == null || !strNoticeCode.matches("\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // DTO 생성
        Notice notice = new Notice();
        notice.setNoticeCode(Integer.parseInt(strNoticeCode));
        notice.setNoticeTitle(title.trim());
        notice.setNoticeContent(content.trim());

        try {
            int row = noticeDao.updateNotice(notice);

            if (row == 1) {
                // 수정 성공 → 상세보기로 이동
                response.sendRedirect(request.getContextPath() + "/emp/noticeOne?noticeCode=" + strNoticeCode);
            } else {
                request.setAttribute("error", "수정 실패했어.");
                request.setAttribute("n", noticeDao.selectNoticeOne(Integer.parseInt(strNoticeCode)));
                request.getRequestDispatcher("/WEB-INF/view/emp/modifyNotice.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}