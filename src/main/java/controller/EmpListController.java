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
@WebServlet("/EmpListController")
public class EmpListController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} 
		
		int rowPerPage = 10;
		int beginRow = (currentPage - 1) * rowPerPage;
		
		int lastPage = 0;
		
		EmpDao empDao = new EmpDao();
		List<Emp> empList = null;
		try {
			empList = empDao.selectEmpListByPage(beginRow, rowPerPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 모델 값 전달
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("empList", empList);

		request.getRequestDispatcher("/WEB-INF/view/emp/empList.jsp").forward(request, response);
	}
}
