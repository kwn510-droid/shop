// EmpActiveApi.java
package ajax;

import java.io.IOException;
import java.io.PrintWriter;
import dao.EmpDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/emp/active")
public class EmpActiveRestController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json; charset=UTF-8");

        String empCodeStr = req.getParameter("empCode");
        String currentActiveStr = req.getParameter("currentActive");

        try (PrintWriter out = resp.getWriter()) {
            if (empCodeStr == null || currentActiveStr == null) {
                out.write("{\"ok\":false,\"message\":\"PARAM_MISSING\"}");
                return;
            }

            int empCode = Integer.parseInt(empCodeStr);
            int currentActive = Integer.parseInt(currentActiveStr);
            int newActive = (currentActive == 1) ? 0 : 1;

            EmpDao dao = new EmpDao();
            int updated = dao.updateEmpActive(empCode, newActive);

            if (updated == 1) {
                String msg = (newActive == 1) ? "활성화 완료" : "비활성화 완료";
                out.write("{\"ok\":true,\"empCode\":" + empCode + ",\"active\":" + newActive + ",\"message\":\"" + msg + "\"}");
            } else {
                out.write("{\"ok\":false,\"message\":\"UPDATE_FAILED\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("{\"ok\":false,\"message\":\"SERVER_ERROR\"}");
        }
    }
}