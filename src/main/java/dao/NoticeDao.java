package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Notice;

public class NoticeDao {
	// /emp/noticeList
	public List<Notice> selectNoticeList(int beginRow, int rowPerPage) {
		List<Notice> list = new ArrayList<Notice>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = """
			SELECT
				notice_code noticeCode
				, notice_title noticeTitle
				, createdate
			FROM notice
			ORDER BY notice_code DESC
			OFFSET ? ROWS FETCH NEXT ? ROWS ONLY	
		""";
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Notice n = new Notice();
				n.setNoticeCode(rs.getInt("noticeCode"));
				n.setNoticeTitle(rs.getString("noticeTitle"));
				n.setCreatedate(rs.getString("createdate"));
				list.add(n);
			}
		} catch(Exception e1) {
			// conn.rollback();
			e1.printStackTrace(); // 콘솔에 예외메세지 출력
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	
	public int selectCount() {
		int count = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = """
			select count(*) from notice	
		""";
		try {
			conn = DBConnection.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch(Exception e1) {
			// conn.rollback();
			e1.printStackTrace(); // 콘솔에 예외메세지 출력
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return count;
	}
	
	// /emp/addNotice
	public int insertNotice(Notice n) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = """
		""";
		try {
			
		} catch(Exception e1) {
			// conn.rollback();
			e1.printStackTrace();
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return row;
	}
	
	// /emp/noticeOne
	public Notice selectNoticeOne(int noticeCode) {
		Notice resultNotice = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = """
		""";
		try {
			
		} catch(Exception e1) {
			// conn.rollback();
			e1.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return resultNotice;
	}
	
	// /emp/removeNotice
	public int deleteNotice(Notice n) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = """
		""";
		try {
			
		} catch(Exception e1) {
			// conn.rollback();
			e1.printStackTrace();
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return row;
	}
	// /emp/modifyNotice
	// selectNoticeOne(Notice)
	public int updateNotice(Notice n) {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = """
		""";
		try {
			
		} catch(Exception e1) {
			// conn.rollback();
			e1.printStackTrace();
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return row;
	}
}