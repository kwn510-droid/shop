package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Customer;
import dto.Outid;

public class CustomerDao {
	// 직원에 의해 강제탈퇴
	public void deleteCustomerByEmp(Outid outid){
		Connection conn = null;
		PreparedStatement stmtCustomer = null;
		PreparedStatement stmtOutid = null;
		String sqlCustomer = """
				delete from customer where customer_id = ?
				""";		
		String sqlOutid = """
				insert into outid(id, memo, createdate)
				values(?,?,?)
				""";	
		// JDBC Connection의 기본 commit설정값 auto commit = true : false 변경 후 transaction 적용
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);// 개발자가 commit / rollback 직접 구현이 필요
			stmtCustomer = conn.prepareStatement(sqlCustomer);
			// param 설정 ? : outid.getId();
			int row = stmtCustomer.executeUpdate(); //customer 삭제
			if(row == 1) {
				stmtOutid = conn.prepareStatement(sqlOutid);
				// param 설정 ? outid.getId(); ? outid.getMemo() ? sysdate
				stmtOutid.executeUpdate(); //outid 입력
			} else {
				throw new SQLException();
			}
			
			conn.commit();
		} catch(SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				stmtOutid.close();
				stmtCustomer.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	// 전체 고객 몇명인지 확인
	public int selectCustomerCount() throws Exception {
	    String sql = "SELECT COUNT(*) FROM CUSTOMER";
	    Connection conn = DBConnection.getConnection();
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    ResultSet rs = stmt.executeQuery();

	    int count = 0;
	    if (rs.next()) {
	        count = rs.getInt(1);
	    }

	    rs.close();
	    stmt.close();
	    conn.close();

	    return count;
	}
	
	// 직원 로그인시 전체 고객 리스트 확인
	public List<Customer> selectCustomerList(int currentPage, int rowPerPage) throws Exception {
	    int startRow = (currentPage - 1) * rowPerPage + 1; // 시작 rn
	    int endRow   = currentPage * rowPerPage;           // 끝 rn

	    String sql =
	        "SELECT customer_code, customer_id, customer_name, customer_phone, point, createdate " +
	        "FROM ( " +
	        "  SELECT ROW_NUMBER() OVER (ORDER BY customer_code DESC) AS rn, " +
	        "         customer_code, customer_id, customer_name, customer_phone, point, createdate " +
	        "  FROM CUSTOMER " +
	        ") " +
	        "WHERE rn BETWEEN ? AND ? " +
	        "ORDER BY rn";

	    List<Customer> list = new ArrayList<>();

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, startRow);
	        stmt.setInt(2, endRow);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Customer c = new Customer();
	                c.setCustomerCode(rs.getInt("customer_code"));
	                c.setCustomerId(rs.getString("customer_id"));
	                c.setCustomerName(rs.getString("customer_name"));
	                c.setCustomerPhone(rs.getString("customer_phone"));
	                c.setPoint(rs.getInt("point"));
	                c.setCreatedate(rs.getString("createdate"));
	                list.add(c);
	            }
	        }
	    }
	    return list;
	}
	
	// ID 사용가능 여부
	public boolean existsCustomerId(String customerId) throws Exception {
		String sql = "select count(*) from customer where customer_id = ?";
		Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = null;
		
		stmt.setString(1, customerId);
		rs = stmt.executeQuery();
		rs.next();
		boolean exists = rs.getInt(1) > 0;
		
		rs.close();
		stmt.close();
		conn.close();
		return exists;
		
		
	}
	
	//로그인
	public Customer selectCustomerByLogin(String customerId, String customerPw) throws Exception {
		String sql = """
			    SELECT customer_id, customer_name, point
			    FROM Customer
			    WHERE customer_id = ? AND customer_pw = ?
			""";
		
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Customer c = null;
        
        conn = DBConnection.getConnection();
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, customerId);
        stmt.setString(2, customerPw);
		
        rs = stmt.executeQuery();
        if(rs.next()) {
        	c = new Customer();
        	c.setCustomerId(rs.getString("customer_id"));
        	c.setCustomerName(rs.getString("customer_name"));
        	c.setPoint(rs.getInt("point"));   
        }  
        rs.close();
        stmt.close();
        conn.close();

		return c;
	}
}