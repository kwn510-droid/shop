package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/out/addCustomer")
public class AddCustomerController extends HttpServlet {
	// 폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// /out/addMember.jsp
		request.getRequestDispatcher("/WEB-INF/view/out/addMember.jsp").forward(request, response);
	}

	// 액션
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// CustomerDao.insertCustomer(Customer)
		response.sendRedirect(request.getContextPath()+"/out/login"); 
	}
}