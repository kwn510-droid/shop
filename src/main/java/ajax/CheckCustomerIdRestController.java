package ajax;

import java.io.IOException;
import java.io.PrintWriter;

import dao.CustomerDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/checkCustomerId")
public class CheckCustomerIdRestController extends HttpServlet {
	  @Override
	  protected void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {

		    String id = request.getParameter("id");
		    boolean exists = false;

		    try {
		      exists = new CustomerDao().existsCustomerId(id);
		    } catch (Exception e) {
		      e.printStackTrace(); // ★ 예외 내용 콘솔 출력

		      // 실패한 경우 클라이언트에 500 에러 전송 후 종료
		      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "DB 조회 오류");
		      return;
		    }

		    // 정상 응답
		    response.setContentType("application/json; charset=UTF-8");
		    try (PrintWriter out = response.getWriter()) {
		      out.print("{\"exists\":" + exists + "}");
		    }
		  }
	}