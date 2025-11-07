package controller;

import java.io.IOException;

import dao.AddressDao;
import dto.Address;
import dto.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/customer/addAddress")
public class AddAddressController extends HttpServlet {
	private AddressDao addressDao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/customer/addAddress.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Customer loingCustomer = (Customer)(request.getSession().getAttribute("loginCustomer"));
		
		String[] addressArr = request.getParameterValues("address");
		String address = String.join(" ", addressArr);
		System.out.println("address: " + address);
		Address a = new Address();
		a.setAddress(address);
		a.setCustomerCode(loingCustomer.getCustomerCode());
		addressDao = new AddressDao();
		addressDao.insertAddress(a);
		
		response.sendRedirect(request.getContextPath()+"/customer/addressList");
	}

}