package controller;

import java.io.IOException;
import java.util.List;

import dao.CustomerDao;
import dto.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CustomerListController
 */
@WebServlet("/emp/customerList")
public class CustomerListController extends HttpServlet {
	private CustomerDao customerDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//CustomerDao customerDao = new CustomerDao(); 앞으로 이것보다는 전역변수 선언하고 객체 생성하자
		
		//request
		int currentPage = 1;
		int beginRow = 0;
		int rowPerPage = 10;
		
		String cp = request.getParameter("currentPage");
		String rp = request.getParameter("rowPerPage");
		
		beginRow = (currentPage - 1) * rowPerPage;
		
		// dao 호출		
		this.customerDao = new CustomerDao();
		List<Customer> customerList = null;
		try {
			customerList = customerDao.selectCustomerList(beginRow, rowPerPage);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 총 고객수 
		int totalCount = 0;
		try {
			totalCount = customerDao.selectCustomerCount();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		// 속성에 모델 저장
		request.setAttribute("customerList", customerList);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("rowPerPage", rowPerPage);
		request.setAttribute("beginRow", beginRow);
		request.setAttribute("totalCount", totalCount);
		
		request.getRequestDispatcher("/WEB-INF/view/emp/customerList.jsp").forward(request,response);
	}
}
