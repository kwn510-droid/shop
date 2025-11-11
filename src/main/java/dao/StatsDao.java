package dao;

import java.util.Map;

public class StatsDao {
	// 11개 chart 메서드
	
	public Map<String, Object> selectOrderTotalCntByYM(String fromYM, String toYM) {
		 String sql = """
		            select  t.ym
		            		,sum(t.cnt) over(order by t.ym asc)
		            from
		                (select to_char(createdate, 'YYYY-MM') ym, count(*) cnt
		                 from orders
		                 where createdate between to_date('2025-01-01', 'YYYY-MM-DD')
		                 and to_date('2025-12-31', 'YYYY-MM-DD')
		                 group by to_char(createdate, 'YYYY-MM')) t;
		            """;
		
		return null;
	} 
}
