package controller;

import java.io.IOException;

import dao.CustomerDao;
import dto.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/out/addCustomer")
public class AddCustomerController extends HttpServlet {
	// 폼
	  protected void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {
		    request.getRequestDispatcher("/WEB-INF/view/out/addCustomer.jsp")
		           .forward(request, response);
		  }

	// 액션
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// CustomerDao.insertCustomer(Customer)
		
		request.setCharacterEncoding("UTF-8");
		
		String customerId = request.getParameter("customerId");
		String customerPw = request.getParameter("customerPw");
		String customerName = request.getParameter("customerName");
		String customerPhone = request.getParameter("customerPhone");
		String gender = request.getParameter("gender");
		
		// 서버측 기본 검증
		if(customerId==null || customerPw==null || customerName==null || customerPhone==null || gender==null ||
				customerId.isBlank() || customerPw.isBlank() || customerName.isBlank()
				|| customerPhone.isBlank() || gender.isBlank()) {
			response.sendRedirect(request.getContextPath()+"/out/addCustomer?error=PARAM");
			return;
		}
		
		try {
			CustomerDao customerDao = new CustomerDao();
			// 2) 중복아이디 체크
			if(customerDao.existsCustomerId(customerId)) {
				response.sendRedirect(request.getContextPath() + "/out/addCustomer?error=dup");
				return;
			}
		
		// 3) DTO 채워서 insert
			Customer customer = new Customer();
			customer.setCustomerId(customerId);
			customer.setCustomerPW(customerPw);
			customer.setCustomerName(customerName);
			customer.setCustomerPhone(customerPhone);
			customer.setGender(gender);

		      int row = customerDao.insertCustomer(customer); // ★ 아래 DAO에 구현
		      if (row == 1) {
		    	    HttpSession session = request.getSession();
		    	    session.setAttribute("msg", "회원가입이 완료되었습니다. 입력하신 아이디와 비밀번호로 로그인하세요 :)");
		    	    response.sendRedirect(request.getContextPath() + "/out/Login");
		    	    return;
		    	} else {
		        response.sendRedirect(request.getContextPath()+"/out/addCustomer?error=FAIL");
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "회원가입 오류");
		    }
		  }
		}