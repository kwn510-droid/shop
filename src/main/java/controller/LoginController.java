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


@WebServlet("/out/Login")
public class LoginController extends HttpServlet {
	// form
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/out/login.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customerOrEmpSel = request.getParameter("customerOrEmpSel");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
	      // 기본 파라미터 유효성 검증
        if (customerOrEmpSel == null || id == null || pw == null || id.isBlank() || pw.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/out/Login?error=PARAM");
            return;
        }
        
        try {
        	if("customer".equals(customerOrEmpSel)) {
        		CustomerDao customerdao = new CustomerDao();
        		Customer login = customerdao.selectCustomerByLogin(id, pw);
				
        		if(login != null) {
        			HttpSession session = request.getSession();
        			session.setAttribute("loginCustomer", login);

                    // 3) 고객 대시보드로
                    response.sendRedirect(request.getContextPath() + "/customer/customerIndex");
                } else {
                    // 로그인 실패
                    response.sendRedirect(request.getContextPath() + "/out/Login?error=INVALID");
                }
        			
        			
	        	} else if ("emp".equals(customerOrEmpSel)) {
	                 response.sendRedirect(request.getContextPath() + "/emp/empIndex");
	             } else {
	                 response.sendRedirect(request.getContextPath() + "/out/Login?error=ROLE");
	             }
	         } catch (Exception e) {
	             throw new ServletException(e);
	         }
	     }
	 }
