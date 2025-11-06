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
	// 탈퇴회원 목록 페이징 조회
	// 탈퇴회원 목록 페이징 조회 (Oracle 버전)
	public List<Outid> selectOutidList(int currentPage, int rowPerPage) throws Exception {
	    int startRow = (currentPage - 1) * rowPerPage + 1;
	    int endRow   = currentPage * rowPerPage;

	    String sql = """
	        SELECT id, memo, createdate
	        FROM (
	            SELECT ROW_NUMBER() OVER (ORDER BY createdate DESC) rn,
	                   id, memo, createdate
	            FROM outid
	        )
	        WHERE rn BETWEEN ? AND ?
	        ORDER BY rn
	    """;

	    List<Outid> list = new ArrayList<>();

	    try (
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)
	    ) {
	        stmt.setInt(1, startRow);
	        stmt.setInt(2, endRow);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Outid o = new Outid();
	                o.setId(rs.getString("id"));
	                o.setMemo(rs.getString("memo"));
	                o.setCreatedate(rs.getString("createdate"));
	                list.add(o);
	            }
	        }
	    }
	    return list;
	}
	
	// 탈퇴회원 총 인원수
	public int selectOutidCount() throws Exception {
	    String sql = "SELECT COUNT(*) FROM outid";
	    try (
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery()
	    ) {
	        rs.next();
	        return rs.getInt(1);
	    }
	}
	
	// 직원에 의해 강제탈퇴: OUTID 기록 + CUSTOMER 삭제 (둘 다 되면 커밋, 아니면 롤백)
	public void deleteCustomerByEmp(Outid outid){
		Connection conn = null;
		PreparedStatement stmtCustomer = null;
		PreparedStatement stmtOutid = null;
		String sqlCustomer = """
				delete from customer where customer_id = ?
				""";		
		String sqlOutid = """
			    insert into outid(id, memo, createdate)
			    values(?, ?, SYSDATE)
				""";
		// JDBC Connection의 기본 commit설정값 auto commit = true : false 변경 후 transaction 적용
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);// 개발자가 commit / rollback 직접 구현이 필요
			
			// customer 삭제
			stmtCustomer = conn.prepareStatement(sqlCustomer);
			stmtCustomer.setString(1, outid.getId()); // 파라미터 설정
			int row = stmtCustomer.executeUpdate();
			if(row != 1) {
				throw new SQLException("삭제할 고객이 존재하지 않음: " + outid.getId());
			}
			
			// outid 저장
			
			stmtOutid = conn.prepareStatement(sqlOutid);
			stmtOutid.setString(1, outid.getId()); // 파라미터 설정
			stmtOutid.setString(2,
		            (outid.getMemo() == null || outid.getMemo().isBlank()) 
	                ? "강제탈퇴" 
	                : outid.getMemo()
	        );
	        stmtOutid.executeUpdate();

	        conn.commit(); // 성공 시 commit
		
		} catch(SQLException e) {
			// 중간에 실패하면 rollback
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace(); // 
			}
			e.printStackTrace();
		} finally {
	        // 자원 정리
	        try {
	            if(stmtOutid != null) stmtOutid.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        try {
	            if(stmtCustomer != null) stmtCustomer.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        try {
	            if(conn != null) {
	                conn.setAutoCommit(true); // autoCommit 원상복구
	                conn.close();
	            }
	        } catch (SQLException e) {
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