package restapi;

import java.io.IOException;
import java.io.PrintWriter;

import dao.CustomerDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/rest/checkCustomerId")
public class CheckCustomerIdRest extends HttpServlet {
	  @Override
	  protected void doGet(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, IOException {
	    String id = request.getParameter("id");
	    boolean exists = false;
	    try { exists = new CustomerDao().existsCustomerId(id); } catch (Exception ignore) {}

	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json");
	    try (PrintWriter out = response.getWriter()) {
	      out.print("{\"exists\":" + exists + "}");
	    }
	  }
	}