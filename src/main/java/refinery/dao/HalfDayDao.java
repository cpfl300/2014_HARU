package refinery.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import refinery.model.Article;


@Repository
public class HalfDayDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int[] addArticles(final List<Article> articles) {
		
		return this.jdbcTemplate.batchUpdate(
					"INSERT INTO half_day (timestamp, sequence, articles_id) VALUES (?, ?, ?)",
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Article article = articles.get(i);
							ps.setString(1, article.getTimestamp());
							ps.setInt(2, article.getSequence());
							ps.setInt(3, article.getId());
						}

						@Override
						public int getBatchSize() {
							
							return articles.size();
						}
						
					}
				
				);
	}

	public int getCount() {
		
		return this.jdbcTemplate.queryForInt("SELECT count(*) FROM half_day");
	}

	public int deleteAll() {
		
		return this.jdbcTemplate.update("DELETE FROM half_day");
	}
	
	
	
	

}
