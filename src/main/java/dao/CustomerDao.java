package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.Customer;

public class CustomerDao {
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
