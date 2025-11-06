package controller;

import java.io.IOException;

import dao.CustomerDao;
import dto.Outid;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemoveCustomerByEmpController
 */
@WebServlet("/emp/removeCustomerByEmp")
public class RemoveCustomerByEmpController extends HttpServlet {
	private CustomerDao customerDao = new CustomerDao();
	
	// 강제 탈퇴 화면
	protected void doGet(HttpServletRequest request, HttpServletResponse response
			) throws ServletException, IOException {
		String id = request.getParameter("customerId");
		String memo = request.getParameter("memo");
		String cp = request.getParameter("currentPage");
		String rp = request.getParameter("rowPerPage");
		
		if (id == null || id.isBlank()) {
            throw new ServletException("customerId 파라미터가 없습니다.");
        }
        if (memo == null || memo.isBlank()) memo = "강제탈퇴";
        
        Outid outid = new Outid();
        outid.setId(id);
        outid.setMemo(memo);
        
        try {
            customerDao.deleteCustomerByEmp(outid); 
        } catch (Exception e) {
            throw new ServletException("강퇴 처리 중 오류", e);
        }
        
        // 강퇴된 회원 목록 페이지로 
        String next = request.getContextPath() + "/emp/outidList"
                + "?currentPage=" + ((cp != null && cp.matches("\\d+")) ? cp : "1")
                + "&rowPerPage=" + ((rp != null && rp.matches("\\d+")) ? rp : "10");

        response.sendRedirect(next);
    }

	// 강제 탈퇴 액션
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
