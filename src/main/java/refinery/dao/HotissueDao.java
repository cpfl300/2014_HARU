package refinery.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;

@Repository
public class HotissueDao {
	
	private static final Logger log = LoggerFactory.getLogger(HotissueDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Hotissue> hotissueMapper = (rs, rowNum) -> {		
		Hotissue hotissue = new Hotissue();
		hotissue.setId(rs.getInt("id"));
		hotissue.setName(rs.getString("name"));
		hotissue.setTimestamp(rs.getString("timestamp"));
		hotissue.setScore(rs.getDouble("score"));

		return hotissue;	
	};
	
	
	private RowMapper<Hotissue> hotissueWithArticlesMapper = (rs, rowNum) -> {
		
		ResultSetExtractor<Hotissue> hotissueResultSetExtractor = new ResultSetExtractor<Hotissue>() {

			@Override
			public Hotissue extractData(ResultSet rs) throws SQLException, DataAccessException {
				Hotissue hotissue = new Hotissue();
				hotissue.setId(rs.getInt("hotissues.id"));
				hotissue.setName(rs.getString("hotissues.name"));
				hotissue.setTimestamp(rs.getString("hotissues.timestamp"));
				
				List<Article> articles = new ArrayList<Article>();
				do {
					Article article = new Article();
					Journal journal = new Journal(rs.getInt("articles.journals_id"));
					Section section = new Section(rs.getInt("articles.minor_sections_id"));
					
					article.setJournal(journal);
					article.setSection(section);
					
					article.setId(rs.getInt("articles.id"));
					article.setTitle(rs.getString("articles.title"));
					article.setDate(rs.getString("articles.date"));
					article.setContent(rs.getString("articles.content"));
					article.setHits(rs.getInt("articles.hits"));
					article.setCompletedReadingCount(rs.getInt("articles.completed_reading_count"));
					article.setTimestamp(rs.getString("articles.timestamp"));
					
					articles.add(article);
				} while(rs.next());
				
				hotissue.setArticles(articles);
				
				return hotissue;
			}
			
		};
		
		return hotissueResultSetExtractor.extractData(rs);
	};

	private RowMapper<Hotissue> hotissueWithArticleMapper = (rs, rowNum) -> {		
		Hotissue hotissue = new Hotissue();
		hotissue.setId(rs.getInt("hotissues.id"));
		hotissue.setName(rs.getString("hotissues.name"));
		hotissue.setTimestamp(rs.getString("hotissues.timestamp"));
		
		List<Article> articles = new ArrayList<Article>();
		Article article = new Article();
		Journal journal = new Journal(rs.getInt("articles.journals_id"));
		Section section = new Section(rs.getInt("articles.minor_sections_id"));
		
		article.setJournal(journal);
		article.setSection(section);
		
		article.setId(rs.getInt("articles.id"));
		article.setTitle(rs.getString("articles.title"));
		article.setDate(rs.getString("articles.date"));
		article.setContent(rs.getString("articles.content"));
		article.setHits(rs.getInt("articles.hits"));
		article.setCompletedReadingCount(rs.getInt("articles.completed_reading_count"));
		article.setTimestamp(rs.getString("articles.timestamp"));
		article.setScore(rs.getDouble("articles.score"));
		
		articles.add(article);		
		hotissue.setArticles(articles);

		return hotissue;	
	};
	
	
	
	public int deleteAll() {
		
		return this.jdbcTemplate.update("delete from hotissues");

	}

	public int getCount() {
		
		return this.jdbcTemplate.queryForInt("select count(*) from hotissues");

	}

	public void add(Hotissue hotissue) {
		this.jdbcTemplate.update(
					"insert into hotissues (id, name, score) values (?, ?, ?)",
					hotissue.getId(),
					hotissue.getName(),
					hotissue.getScore()
				);
	}

	public Hotissue get(int id) {
		
		log.debug("hotissue id: " + id);
		
		return this.jdbcTemplate.queryForObject(
					"select * from hotissues where id = ?",
					new Object[]{id},
					this.hotissueMapper
				);
	}

	public int lastInsertId() {
		
		return this.jdbcTemplate.queryForInt("select last_insert_id()"); 
	}

	public Hotissue getWithArticlesById(int hotissueId) {
		
		return this.jdbcTemplate.queryForObject(
					"SELECT * FROM hotissues INNER JOIN articles ON articles.hotissues_id = hotissues.id WHERE hotissues.id = ?",
					new Object[]{hotissueId},
					this.hotissueWithArticlesMapper
				);
			
	}
	

	public Hotissue getByName(String name) {
		
		return this.jdbcTemplate.queryForObject(
					"SELECT * FROM hotissues WHERE name = ?",
					new Object[]{name},
					this.hotissueMapper
				);
				
				
	}

	public int delete(int hotissueId) {
		
		return this.jdbcTemplate.update(
					"DELETE FROM hotissues WHERE id = ?",
					new Object[] {hotissueId}
				);
		
		
	}

	public List<Hotissue> getLatestHotissues(int count) {
		
		return this.jdbcTemplate.query(
					"SELECT * FROM hotissues ORDER BY timestamp DESC LIMIT ?",
					new Object[] {count},
					hotissueMapper
				);
		
	}

	public void addWithTimestamp(Hotissue hotissue) {
		this.jdbcTemplate.update(
				"insert into hotissues (id, name, timestamp) values (?, ?, ?)",
				hotissue.getId(),
				hotissue.getName(),
				hotissue.getTimestamp()
			);
	}

	public int[] addHotissues(final List<Hotissue> hotissues) {
		
		return this.jdbcTemplate.batchUpdate(
					"INSERT IGNORE INTO hotissues (id, name, timestamp) VALUES (?, ?, ?)",
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							
							Hotissue hotissue = hotissues.get(i);
							ps.setInt(1, hotissue.getId());
							ps.setString(2, hotissue.getName());
							ps.setString(3, hotissue.getTimestamp());
						}

						@Override
						public int getBatchSize() {
							
							return hotissues.size();
						}
						
					}
				
				);
		
	}

	public List<Hotissue> getByOrderedScore(int size) {
		
		return this.jdbcTemplate.query(
					"SELECT * FROM hotissues ORDER BY score DESC LIMIT ?",
					new Object[] {size},
					this.hotissueMapper
				);
	}

	public List<Hotissue> getWithArticlesByOrderedScore(int size) {
		
		return this.jdbcTemplate.query(
				"SELECT * FROM " 
					+ "(SELECT * FROM hotissues ORDER BY score DESC LIMIT ?) AS hotissues "
				+ "INNER JOIN "
					+ "(SELECT " 
						+ "id, title, date, content, timestamp, journals_id, hotissues_id, minor_sections_id, "
						+ "hits, completed_reading_count, MAX(score) AS score "
					+ "FROM articles GROUP BY hotissues_id ORDER BY score DESC) AS articles "
				+ "ON hotissues.id = articles.hotissues_id ORDER BY hotissues.score DESC",
				new Object[]{size},
				this.hotissueWithArticleMapper
			);
	}

	public int[] updateScores(final List<Hotissue> hotissues) {
		
		return this.jdbcTemplate.batchUpdate(
				"UPDATE hotissues SET score = ? WHERE id = ?",
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Hotissue hotissue = hotissues.get(i);
						ps.setDouble(1, hotissue.getScore());
						ps.setInt(2, hotissue.getId());
					}

					@Override
					public int getBatchSize() {
						
						return hotissues.size();
					}
					
				}
			);
	}

	public List<Hotissue> getBetweenServiceDates(String from, String to) {
		
		return this.jdbcTemplate.query(
					"SELECT half_day.sequence AS sequence, hotissues.id, hotissues.name, hotissues.timestamp, hotissues.score FROM "
						+ "(SELECT articles.hotissues_id AS hotissues_id, half_day.sequence AS sequence FROM "
							+ "(SELECT * FROM half_day WHERE timestamp BETWEEN ? AND ?) AS half_day "
						+ "INNER JOIN articles "
						+ "ON half_day.articles_id = articles.id "
						+ "ORDER BY half_day.sequence) AS half_day "
					+ "INNER JOIN hotissues "
					+ "ON half_day.hotissues_id = hotissues.id",
					new Object[] {from, to},
					new RowMapper<Hotissue>() {

						@Override
						public Hotissue mapRow(ResultSet rs, int rowNum) throws SQLException {
							Hotissue hotissue = new Hotissue();
							hotissue.setId(rs.getInt("id"));
							hotissue.setName(rs.getString("name"));
							hotissue.setTimestamp(rs.getString("timestamp"));
							hotissue.setScore(rs.getDouble("score"));
							hotissue.setSequence(rs.getInt("sequence"));
							
							return hotissue;
							
						}
							
					}
				);
		
	}
}
