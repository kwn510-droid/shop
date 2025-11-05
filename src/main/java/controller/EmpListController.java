package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.EmpDao;
import dto.Emp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EmpListController
 */
@WebServlet("/emp/empList")
public class EmpListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int currentPage = 1;
        int rowPerPage = 10;
        try {
            String p = request.getParameter("currentPage");
            if (p != null && !p.isBlank()) currentPage = Math.max(1, Integer.parseInt(p));
            String rpp = request.getParameter("rowPerPage");
            if (rpp != null && !rpp.isBlank()) rowPerPage = Math.max(1, Integer.parseInt(rpp));
        } catch (NumberFormatException ignore) {}

        int beginRow = (currentPage - 1) * rowPerPage;

        EmpDao dao = new EmpDao();
        try {
            int totalCount = dao.countEmp();
            int lastPage = (int)Math.ceil(totalCount / (double)rowPerPage);
            if (lastPage == 0) lastPage = 1;              // 데이터 0건 방어
            if (currentPage > lastPage) {                  // 범위 초과 방어
                currentPage = lastPage;
                beginRow = (currentPage - 1) * rowPerPage;
            }

            var empList = dao.selectEmpListByPage(beginRow, rowPerPage);

            request.setAttribute("empList", empList);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("rowPerPage", rowPerPage);
            request.setAttribute("lastPage", lastPage);
            request.setAttribute("hasPrev", currentPage > 1);
            request.setAttribute("hasNext", currentPage < lastPage);

            request.getRequestDispatcher("/WEB-INF/view/emp/empList.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}