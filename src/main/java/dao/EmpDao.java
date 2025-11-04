package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Emp;

public class EmpDao {
	//사원 목록
	public List<Emp> selectEmpListByPage(int beginRow, int rowPerPage) throws SQLException {
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		String sql = """
					select emp_code empCode, emp_id empId, emp_name empName, active, createdate
					from emp 
					order by emp_code
					offset 0 rows fetch next ? rows only;
		""";
		// offset 10 rows fetch next 10 rows only : 10행 다음부터 10개의 행을 가져옴
		conn = DBConnection.getConnection();
        stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, beginRow);
        stmt.setInt(2, rowPerPage);
		rs = stmt.executeQuery();
		List<Emp> list = new ArrayList<>();
		while(rs.next()) {
			Emp e = new Emp();
			e.setEmpCode(rs.getInt("empCode"));
			e.setEmpId(rs.getString("empId"));
			e.setEmpName(rs.getString("empName"));
			e.setActive(rs.getInt("active"));
			e.setCreatedate(rs.getString("createdate"));
			list.add(e);
			
		}
		return list;
	}
	
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