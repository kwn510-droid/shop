package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.Goods;
import dto.GoodsImg;

public class GoodsDao {
	 //  전체 행 수 (soldout 제외)
    public int selectGoodsCount() throws Exception {
        String sql = """
            SELECT COUNT(*)
            FROM goods
            WHERE soldout IS NULL
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }
	
	// rowPerPage : goodsList(10개), customerIndex(20개)
	public List<Map<String, Object>> selectBestGoodsList() throws Exception {
	    List<Map<String, Object>> list = new ArrayList<>();

	    // 주문이 많은 상품 5개 + 대표이미지 조인
	    // ※ 주문 상태를 '주문완료'로 한정하려면: inner subquery에 WHERE order_state='주문완료' 추가
	    String sql = """
	    		WITH img1 AS (
	    		  SELECT goods_code, filename,
	    		         ROW_NUMBER() OVER (PARTITION BY goods_code ORDER BY createdate DESC) rn
	    		  FROM goods_img
	    		),
	    		tot AS (
	    		  SELECT goods_code, SUM(order_quantity) AS total_qty
	    		  FROM orders
	    		  -- WHERE order_state = '주문완료'
	    		  GROUP BY goods_code
	    		)
	    		SELECT g.goods_code AS goodsCode,
	    		       g.goods_name AS goodsName,
	    		       g.goods_price AS goodsPrice,
	    		       i.filename    AS filename,
	    		       t.total_qty   AS totalQty
	    		FROM tot t
	    		JOIN goods g   ON g.goods_code = t.goods_code
	    		LEFT JOIN img1 i ON i.goods_code = g.goods_code AND i.rn = 1
	    		ORDER BY t.total_qty DESC, g.goods_code DESC
	    		FETCH FIRST 5 ROWS ONLY
	    		""";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Map<String, Object> row = new HashMap<>();
	            row.put("filename",   rs.getString("filename"));
	            row.put("goodsCode",  rs.getInt("goodsCode"));
	            row.put("goodsName",  rs.getString("goodsName"));
	            row.put("goodsPrice", rs.getInt("goodsPrice"));
	            list.add(row);
	        }
	    }
	    return list;
	}
	
	// rowPerPage : /emp/goodsList(10개), /customer/customerIndex(20개)
	public List<Map<String, Object>> selectGoodsList(int beginRow, int rowPerPage) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = """
			select 
					gi.filename filename
					, g.goods_code goodsCode
					, g.goods_name goodsName
					, g.goods_price goodsPrice
			from goods g inner join goods_img gi
			on g.goods_code = gi.goods_code
			where g.soldout is null
			order by g.goods_code desc
			offset ? rows fetch next ? rows only
		""";
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("filename", rs.getString("filename"));
				m.put("goodsCode",  rs.getInt("goodsCode"));   // getInt 권장
				m.put("goodsName",  rs.getString("goodsName"));
				m.put("goodsPrice",  rs.getInt("goodsPrice"));
				list.add(m);
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
	
	// 상품등록 + 이미지 등록
	// 반환값은 실패시 false
	public boolean insertGoodsAndImg(Goods goods, GoodsImg img) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmtSeq = null; // select
		PreparedStatement stmtGoods = null; // insert
		PreparedStatement stmtImg = null; // insert
		ResultSet rs = null;
		
		String sqlSeq = """
			select seq_goods.nextval from dual
		""";
		
		String sqlGoods = """
			insert into goods(goods_code, goods_name, goods_price, emp_code, point_rate, soldout, createdate)
			values(?, ?, ?, ?, ?, null, sysdate)	
		""";
		
		String sqlImg = """
			insert into goods_img(goods_code, filename, origin_name, content_type, filesize, createdate)
			values(?, ?, ?, ?, ?, sysdate)
		""";
		
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // 단일 트랜잭션안에서 시퀀스 생성 -> 상품입력 -> 이미지입력
			
			// 1) seq_goods.nextval값을 먼저 생성 후 사용
			stmtSeq = conn.prepareStatement(sqlSeq);
			rs = stmtSeq.executeQuery();
			rs.next();
			int goodsCode = rs.getInt(1);
			
			// 2) goods 입력
			stmtGoods = conn.prepareStatement(sqlGoods);
			stmtGoods.setInt(1, goodsCode);
			stmtGoods.setString(2, goods.getGoodsName());
			stmtGoods.setInt(3, goods.getGoodsPrice());
			stmtGoods.setInt(4, goods.getEmpCode());
			stmtGoods.setDouble(5, goods.getPointRate());
			int row1 = stmtGoods.executeUpdate();
			// 상품입력에 실패하면
			if(row1 != 1) {
				conn.rollback(); 
				return result;
			}
			// 3) // 상품입력에 성공하면 img 입력
			stmtImg = conn.prepareStatement(sqlImg);
			stmtImg.setInt(1, goodsCode);
			stmtImg.setString(2, img.getFilename());
			stmtImg.setString(3, img.getOriginName());
			stmtImg.setString(4, img.getContentType());
			stmtImg.setLong(5, img.getFilesize());
			int row2 = stmtImg.executeUpdate();
			if(row2 != 1) {
				conn.rollback(); 
				return result;
				// throw new SQLException();
			}
			result = true; // 상품 & 이미지 입력성공
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}