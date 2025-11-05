package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 로그인 전 허용
 */
// @WebFilter("/out/*")
public class OutFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 요청 전
		System.out.println("pre OutFilter");
		HttpSession session = ((HttpServletRequest)request).getSession();
		if(session.getAttribute("loginEmp") != null || session.getAttribute("loginCustomer") != null) {
			((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath()+"/emp/empIndex");
			return;
		} else if(session.getAttribute("loginEmp") != null || session.getAttribute("loginCustomer") != null) {
			((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath()+"/customer/customerIndex");
		
		}	
		chain.doFilter(request, response);
		// 요청 후
	}

}
