package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Emp;

public class EmpDao {
	public boolean existsEmpId(String empId) throws SQLException {
	    String sql = "SELECT COUNT(*) FROM emp WHERE emp_id = ?";

	    Connection conn = DBConnection.getConnection();
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1, empId);

	    ResultSet rs = stmt.executeQuery();  
	    boolean exists = false;

	    if (rs.next()) {                      
	        exists = rs.getInt(1) > 0;        
	    }

	    rs.close();
	    stmt.close();
	    conn.close();

	    return exists;
	}
	
	public int insertEmp(Emp e) throws SQLException {
		String sql = """
				insert into emp(emp_code, emp_id, emp_pw, emp_name, active, createdate)
				values (seq_emp.nextval, ?, ?, ?, ?, sysdate)
				""";
		
		Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, e.getEmpId());
		stmt.setString(2, e.getEmpPw());
		stmt.setString(3, e.getEmpName());
		stmt.setInt(4, e.getActive());
		
		int row = stmt.executeUpdate();
		
		stmt.close();
		conn.close();
		
		return row;
		
		
	}
	
	public int updateEmpActive(int empCode, int newActive) throws SQLException {
		String sql = "update emp set active = ? where emp_code = ?";
		
		Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setInt(1, newActive);
		stmt.setInt(2, empCode);
		
	
		int row = stmt.executeUpdate();
		
		stmt.close();
		conn.close();
		
		return row;
}
	
	public int countEmp() throws SQLException {
	    String sql = "SELECT COUNT(*) FROM emp";
	    Connection conn = DBConnection.getConnection();
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();

	    rs.next();
	    int cnt = rs.getInt(1);

	    // 자원 정리 (수동으로 close)
	    rs.close();
	    ps.close();
	    conn.close();

	    return cnt;
	}
	
	//사원 목록
	public List<Emp> selectEmpListByPage(int beginRow, int rowPerPage) throws SQLException {
	    String sql = """
	        SELECT empCode, empId, empName, active, createdate
	        FROM (
	            SELECT innerT.*, ROWNUM AS rn
	            FROM (
	                SELECT 
	                    emp_code AS empCode,
	                    emp_id   AS empId,
	                    emp_name AS empName,
	                    active,
	                    createdate
	                FROM emp
	                ORDER BY emp_code
	            ) innerT
	            WHERE ROWNUM <= ?
	        )
	        WHERE rn > ?
	    """;

	    List<Emp> list = new ArrayList<>();
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        int endRow = beginRow + rowPerPage; // 끝 경계
	        stmt.setInt(1, endRow);             // ROWNUM 상한
	        stmt.setInt(2, beginRow);           // 하한(beginRow)

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Emp e = new Emp();
	                e.setEmpCode(rs.getInt("empCode"));
	                e.setEmpId(rs.getString("empId"));
	                e.setEmpName(rs.getString("empName"));
	                e.setActive(rs.getInt("active"));
	                e.setCreatedate(rs.getString("createdate"));
	                list.add(e);
	            }
	        }
	    }
	    return list;
	}
	
	//로그인
	public Emp selectEmpByLogin(String empId, String empPw) throws Exception {
	    String sql = """
	        SELECT emp_code, emp_id, emp_name, active, createdate
	        FROM emp
	        WHERE emp_id = ? AND emp_pw = ?
	    """;

	    Emp e = null;
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, empId);
	        stmt.setString(2, empPw);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                e = new Emp();
	                e.setEmpCode(rs.getInt("emp_code"));
	                e.setEmpId(rs.getString("emp_id"));
	                e.setEmpName(rs.getString("emp_name"));
	                e.setActive(rs.getInt("active"));
	                e.setCreatedate(rs.getString("createdate"));
	            }
	        }
	    }
	    return e;
	}
}