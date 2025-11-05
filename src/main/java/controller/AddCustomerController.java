package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/out/addCustomer")
public class AddCustomerController extends HttpServlet {
	// Form
	protected void doGet(HttpServletRequest request, HttpServletResponse response
			) throws ServletException, IOException {
	    request.getRequestDispatcher("/WEB-INF/view/out/addCustomer.jsp").forward(request, response);
	}
	// Action
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//CustomerDao.insertCustomer(Customer)
		response.sendRedirect(request.getContextPath()+"/out/login");
	}
}