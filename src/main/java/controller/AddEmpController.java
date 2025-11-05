package controller;

import java.io.IOException;

import dao.EmpDao;
import dto.Emp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/emp/addEmp")
public class AddEmpController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response
			) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/emp/addEmp.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response
			) throws ServletException, IOException {
		String empId   = request.getParameter("empId");
		String empPw   = request.getParameter("empPw");
		String empName   = request.getParameter("empName");
		String active   = request.getParameter("active");
		
		// 공백 방지
		if(empId==null || empPw==null || empName==null || active==null 
				|| empId.isBlank() || empPw.isBlank() || empName.isBlank()) {
			request.setAttribute("error", "모든 항목을 입력해 주세요.");
			request.getRequestDispatcher("/WEB-INF/view/emp/addEmp.jsp").forward(request, response);
			return;
		}
		
		try {
            EmpDao dao = new EmpDao();

            // ID 중복
            if (dao.existsEmpId(empId)) {
                request.setAttribute("error", "이미 사용중인 사원ID 입니다.");
                request.getRequestDispatcher("/WEB-INF/view/emp/addEmp.jsp").forward(request, response);
                return;
            }
		   // 저장
           Emp e = new Emp();
           e.setEmpId(empId);
           e.setEmpPw(empPw);
           e.setEmpName(empName);
           e.setActive(Integer.parseInt(active));
           
           int row = dao.insertEmp(e);
           if(row==1) {
        	   response.sendRedirect(request.getContextPath() + "/emp/empList");
           } else {
        	   request.setAttribute("error", "저장에 실패했습니다.");
        	   request.getRequestDispatcher("/WEB-INF/view/emp/addEmp.jsp").forward(request, response);
           }
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}	 
}
