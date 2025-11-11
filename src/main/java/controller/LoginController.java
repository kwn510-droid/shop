package controller;

import java.io.IOException;

import dao.CustomerDao;
import dao.EmpDao;
import dto.Customer;
import dto.Emp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/out/login")
public class LoginController extends HttpServlet {
	// form
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/out/login.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String customerOrEmpSel = request.getParameter("customerOrEmpSel");
	    String id = request.getParameter("id");
	    String pw = request.getParameter("pw");

	    if (customerOrEmpSel == null || id == null || pw == null || id.isBlank() || pw.isBlank()) {
	        response.sendRedirect(request.getContextPath() + "/out/login?error=PARAM");
	        return;
	    }

	    try {
	        if ("customer".equals(customerOrEmpSel)) {
	            CustomerDao customerDao = new CustomerDao();
	            Customer login = customerDao.selectCustomerByLogin(id, pw);
	            if (login != null) {
	                HttpSession session = request.getSession();
	                session.setAttribute("loginCustomer", login);
	                response.sendRedirect(request.getContextPath() + "/customer/customerIndex");
	            } else {
	                response.sendRedirect(request.getContextPath() + "/out/login?error=INVALID");
	            }

	        } else if ("emp".equals(customerOrEmpSel)) {
	            EmpDao empDao = new EmpDao();
	            Emp emp = empDao.selectEmpByLogin(id, pw);   // ★ 반드시 검증
	            if (emp != null && emp.getActive() == 1) {   // 필요하면 활성 직원만
	                HttpSession session = request.getSession();
	                session.setAttribute("loginEmp", emp);
	                response.sendRedirect(request.getContextPath() + "/emp/empIndex");
	            } else {
	                response.sendRedirect(request.getContextPath() + "/out/login?error=INVALID");
	            }

	        } else {
	            response.sendRedirect(request.getContextPath() + "/out/login?error=ROLE");
	        }
	    } catch (Exception e) {
	        throw new ServletException(e);
	    }
	}
}