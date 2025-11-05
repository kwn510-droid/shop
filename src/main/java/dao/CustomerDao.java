package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.Customer;

public class CustomerDao {
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
        	c.setPoint(rs.getInt("point"));   // ✅ 이 줄 추가
        }  
        rs.close();
        stmt.close();
        conn.close();

		return c;
	}
}