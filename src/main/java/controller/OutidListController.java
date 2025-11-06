// OutidListController.java
package controller;

import java.io.IOException;
import java.util.List;

import dao.CustomerDao;
import dto.Outid;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/emp/outidList")
public class OutidListController extends HttpServlet {
	
	private CustomerDao customerDao = new CustomerDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		String cp = request.getParameter("currentPage");
		String rp = request.getParameter("rowPerPage");

		int currentPage = (cp != null && cp.matches("\\d+")) ? Integer.parseInt(cp) : 1;
		int rowPerPage  = (rp != null && rp.matches("\\d+")) ? Integer.parseInt(rp) : 10;

		try {
			List<Outid> list = customerDao.selectOutidList(currentPage, rowPerPage);
			int totalCount   = customerDao.selectOutidCount();
			int lastPage     = (int)Math.ceil((double)totalCount / rowPerPage);

			request.setAttribute("list", list);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("rowPerPage", rowPerPage);
			request.setAttribute("lastPage", lastPage);
			request.setAttribute("totalCount", totalCount);

			request.getRequestDispatcher("/WEB-INF/view/emp/outidList.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace(); // ← SQLException ignore 말고 printStackTrace()
			throw new ServletException("탈퇴 회원 목록 조회 중 오류", e);
		}
	}
}