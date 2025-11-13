package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsDao {
	// 11개 chart 메서드

    /* ===================== 성별 ===================== */
	
	//성별 별 주문 수량 
	public List<Map<String, Object>> selectOrderCntByGender() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = """
			select t.g gender, count(*) cnt
			from
			(select c.gender g, o.order_code oc
			from customer c inner join orders o
			on c.customer_code = o.customer_code) t
			group by t.g
		""";
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("gender", rs.getString("gender"));
				map.put("cnt", rs.getInt("cnt"));
				list.add(map);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// 성별 별 주문매출
	public List<Map<String, Object>> selectOrderRevenueByGender() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = """
            select c.gender gender, sum(o.order_price) orderRevenue
            from orders o
            join customer c on c.customer_code = o.customer_code
            group by c.gender
            order by c.gender
        """;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("gender", rs.getString("gender"));
                m.put("orderRevenue", rs.getLong("orderRevenue"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return list;
    }
	
    /* -------- 월별 -------- */
	
	// 월별 주문수량 (비누적)
	public List<Map<String, Object>> selectOrderCntByYM(String fromYM, String toYM) {
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;

	    String sql = """
	        select to_char(createdate,'YYYY-MM') ym, count(*) orderCnt
	        from orders
	        where trunc(createdate,'MM') between to_date(?,'YYYY-MM') and to_date(?,'YYYY-MM')
	        group by to_char(createdate,'YYYY-MM')
	        order by ym
	    """;

	    try {
	        conn = DBConnection.getConnection();
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, fromYM);
	        stmt.setString(2, toYM);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            Map<String, Object> m = new HashMap<String, Object>();
	            m.put("ym", rs.getString("ym"));
	            m.put("orderCnt", rs.getInt("orderCnt"));
	            list.add(m);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (rs!=null) rs.close(); if (stmt!=null) stmt.close(); if (conn!=null) conn.close(); }
	        catch (SQLException e) { e.printStackTrace(); }
	    }
	    return list;
	}

	// 월별 주문수량 (누적)
	public List<Map<String, Object>> selectOrderTotalCntByYM(String fromYM, String toYM) {
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;

	    String sql = """
	        select t.ym ym,
	               sum(t.orderCnt) over(order by t.ym) totalOrderCnt
	        from (
	            select to_char(createdate,'YYYY-MM') ym, count(*) orderCnt
	            from orders
	            where trunc(createdate,'MM') between to_date(?,'YYYY-MM') and to_date(?,'YYYY-MM')
	            group by to_char(createdate,'YYYY-MM')
	        ) t
	        order by t.ym
	    """;

	    try {
	        conn = DBConnection.getConnection();
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, fromYM);
	        stmt.setString(2, toYM);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            Map<String, Object> m = new HashMap<String, Object>();
	            m.put("ym", rs.getString("ym"));
	            m.put("totalOrderCnt", rs.getInt("totalOrderCnt"));
	            list.add(m);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (rs!=null) rs.close(); if (stmt!=null) stmt.close(); if (conn!=null) conn.close(); }
	        catch (SQLException e) { e.printStackTrace(); }
	    }
	    return list;
	}

	// 월별 주문매출 (비누적)
	public List<Map<String, Object>> selectOrderRevenueByYM(String fromYM, String toYM) {
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;

	    String sql = """
	        select to_char(createdate,'YYYY-MM') ym, sum(order_price) orderRevenue
	        from orders
	        where trunc(createdate,'MM') between to_date(?,'YYYY-MM') and to_date(?,'YYYY-MM')
	        group by to_char(createdate,'YYYY-MM')
	        order by ym
	    """;

	    try {
	        conn = DBConnection.getConnection();
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, fromYM);
	        stmt.setString(2, toYM);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            Map<String, Object> m = new HashMap<String, Object>();
	            m.put("ym", rs.getString("ym"));
	            m.put("orderRevenue", rs.getLong("orderRevenue"));
	            list.add(m);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (rs!=null) rs.close(); if (stmt!=null) stmt.close(); if (conn!=null) conn.close(); }
	        catch (SQLException e) { e.printStackTrace(); }
	    }
	    return list;
	}

	// 월별 주문매출 (누적)
	public List<Map<String, Object>> selectOrderTotalRevenueByYM(String fromYM, String toYM) {
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;

	    String sql = """
	        select t.ym ym,
	               sum(t.orderRevenue) over(order by t.ym) totalOrderRevenue
	        from (
	            select to_char(createdate,'YYYY-MM') ym, sum(order_price) orderRevenue
	            from orders
	            where trunc(createdate,'MM') between to_date(?,'YYYY-MM') and to_date(?,'YYYY-MM')
	            group by to_char(createdate,'YYYY-MM')
	        ) t
	        order by t.ym
	    """;

	    try {
	        conn = DBConnection.getConnection();
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, fromYM);
	        stmt.setString(2, toYM);
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            Map<String, Object> m = new HashMap<String, Object>();
	            m.put("ym", rs.getString("ym"));
	            m.put("totalOrderRevenue", rs.getLong("totalOrderRevenue"));
	            list.add(m);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (rs!=null) rs.close(); if (stmt!=null) stmt.close(); if (conn!=null) conn.close(); }
	        catch (SQLException e) { e.printStackTrace(); }
	    }
	    return list;
	}
	
	 /* ===================== Top10 ===================== */
	
	// 고객별 주문횟수 1위 ~ 10위 : 막대 차트
	public List<Map<String, Object>> selectTop10CustomerByOrderCnt() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = """
                select
                    c.customer_id as customerId,
                    count(*)      as orderCnt
                from orders o
                join customer c on c.customer_code = o.customer_code
                group by c.customer_id
                order by orderCnt desc, c.customer_id asc
                fetch first 10 rows only
            """;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("customerId", rs.getString("customerId"));
                m.put("orderCnt", rs.getInt("orderCnt"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return list;
    }
	
	// 고객별 주문금액 1~10위
    public List<Map<String, Object>> selectTop10CustomerByOrderRevenue() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = """
                select
        				c.customer_id customerId
        				, sum(o.order_price) orderRevenue
                from orders o 
                join customer c on c.customer_code=o.customer_code
                group by c.customer_id
                order by orderCnt desc, c.customer_id asc
                fetch first 10 rows only
        """;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("customerId", rs.getString("customerId"));
                m.put("orderRevenue", rs.getLong("orderRevenue"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return list;
    }
    
    // 상품별 주문횟수 1위 ~ 10위 : 막대 차트
    public List<Map<String, Object>> selectTop10GoodsByOrderCnt() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = """
            select g.goods_name goodsName, count(*) orderCnt
            from orders o
            join goods g on g.goods_code = o.goods_code
            group by g.goods_name
            order by orderCnt desc
            fetch first 10 rows only
        """;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("goodsName", rs.getString("goodsName"));
                m.put("orderCnt", rs.getInt("orderCnt"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return list;
    }
    
    // 상품별 주문금액 1위 ~ 10위 : 막대 차트
    public List<Map<String, Object>> selectTop10GoodsByOrderRevenue() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = """
            select g.goods_name goodsName, sum(o.order_price) orderRevenue
            from orders o
            join goods g on g.goods_code = o.goods_code
            group by g.goods_name
            order by orderRevenue desc
            fetch first 10 rows only
        """;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("goodsName", rs.getString("goodsName"));
                m.put("orderRevenue", rs.getLong("orderRevenue"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return list;
    }
    
    //상품별 평균 리뷰평점 1위 ~ 10위 : 막대 차트
    
    public List<Map<String, Object>> selectTop10GoodsByAvgReviewScore() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String sql = """
            select g.goods_name goodsName, round(avg(r.review_score),2) avgReviewScore
            from review r
            join goods g on g.goods_code = r.goods_code
            group by g.goods_name
            order by avgReviewScore desc
            fetch first 10 rows only
        """;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("goodsName", rs.getString("goodsName"));
                m.put("avgReviewScore", rs.getBigDecimal("avgReviewScore"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return list;
    }
}
