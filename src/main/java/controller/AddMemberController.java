package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/out/addCustomerController")
public class AddMemberController extends HttpServlet {
	// Form
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//addCustomer.jsp
	}
	
	// Action
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//CustomerDao.insertCustomer(Customer)
		response.sendRedirect(request.getContextPath()+"/out/login");
	}
}
