package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.Emp;

public class EmpDao {
	//로그인
		public Emp selectEmpByLogin(String empId, String empPw) throws Exception {
			String sql = """
				    SELECT emp_id, emp_name, 
				    FROM Emp
				    WHERE emp_id = ? AND emp_pw = ?
				""";
			
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        Emp e = null;
	        
	        conn = DBConnection.getConnection();
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, empId);
	        stmt.setString(2, empPw);
			
	        rs = stmt.executeQuery();
	        if(rs.next()) {
	        	e = new Emp();
	        	e.setEmpId(rs.getString("emp_id"));
	        	e.setEmpName(rs.getString("emp_name"));
	    
	        }  
	        rs.close();
	        stmt.close();
	        conn.close();

			return e;
		}
	}