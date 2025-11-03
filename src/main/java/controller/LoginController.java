package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/out/Login")
public class LoginController extends HttpServlet {
	// form
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/out/login.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customerOrEmpSel = request.getParameter("customerOrEmpSel");
		

		if(customerOrEmpSel.equals("customer")) {
			//session.setAttribute("loginCustomer", "loginCuustomer");
			//session.setAttribute("loginCustomer", "loginCuustomer");
			response.sendRedirect(request.getContextPath()+"/customer/customerIndex");
		} else if (customerOrEmpSel.equals("emp")) {
			//session.setAttribute("loginEmp", "loginEmp");
			response.sendRedirect(request.getContextPath()+"/emp/empIndex");
		}
	}
}
