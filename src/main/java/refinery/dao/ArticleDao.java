package refinery.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;


@Repository
public class ArticleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Article> articleMapper = (rs, rowNum) -> {		
		Article article = new Article();
		Hotissue hotissue = new Hotissue(rs.getInt("hotissues_id"));
		Journal journal = new Journal(rs.getInt("journals_id"));
		Section section = new Section(rs.getInt("minor_sections_id"));
		
		article.setHotissue(hotissue);
		article.setJournal(journal);
		article.setSection(section);
		
		article.setId(rs.getInt("id"));
		article.setTitle(rs.getString("title"));
		article.setDate(rs.getString("date"));
		article.setContent(rs.getString("content"));
		article.setHits(rs.getInt("hits"));
		article.setCompletedReadingCount(rs.getInt("completed_reading_count"));
		article.setScore(rs.getDouble("score"));
		article.setTimestamp(rs.getString("timestamp"));
		
		return article;
	};

	public int getCount() {
		
		return this.jdbcTemplate.queryForInt("select count(*) from articles");
		
	}

	public int deleteAll() {
		
		return this.jdbcTemplate.update("delete from articles");

	}

	public void add(Article article) {
		
		this.jdbcTemplate.update(
				"insert into articles(id, hotissues_id, title, journals_id, minor_sections_id, date, content, hits, completed_reading_count, score) values (?,?,?,?,?,?,?,?,?,?)",
				article.getId(),
				article.getHotissue().getId(),
				article.getTitle(),
				article.getJournal().getId(),
				article.getSection().getId(),
				article.getDate(),
				article.getContent(),
				article.getHits(),
				article.getCompletedReadingCount(),
				article.getScore()
		);
		
	}

	public Article get(int id) {
		
		return this.jdbcTemplate.queryForObject (
					"SELECT * FROM articles WHERE id = ?",
					new Object[]{id},
					this.articleMapper
				);
	}

	public int delete(int id) {
		
		return this.jdbcTemplate.update(
					"DELETE FROM articles WHERE id = ?",
					new Object[] {id}
				);
		
	}

	public int[] addArticles(final List<Article> articles) {
		
		int[] updateCounts = this.jdbcTemplate.batchUpdate(
					"INSERT IGNORE INTO articles(id, hotissues_id, title, journals_id, minor_sections_id, date, content, hits, completed_reading_count) VALUES (?,?,?,?,?,?,?,?,?)",
					new BatchPreparedStatementSetter() {
	
						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Article article = articles.get(i);
							ps.setInt(1, article.getId());
							ps.setInt(2, article.getHotissue().getId());
							ps.setString(3, article.getTitle());
							ps.setInt(4, article.getJournal().getId());
							ps.setInt(5, article.getSection().getId());
							ps.setString(6, article.getDate());
							ps.setString(7, article.getContent());
							ps.setInt(8, article.getHits());
							ps.setInt(9, article.getCompletedReadingCount());
						}
	
						@Override
						public int getBatchSize() {
							
							return articles.size();
						}
						
					}
				);
		
		return updateCounts;
		
	}

	public int[] updateScores(final List<Article> articles) {
		
		return this.jdbcTemplate.batchUpdate(
					"UPDATE articles SET score = ? WHERE id = ?",
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Article article = articles.get(i);
							ps.setDouble(1, article.getScore());
							ps.setInt(2, article.getId());
						}

						@Override
						public int getBatchSize() {
							
							return articles.size();
						}
						
					}
				);
	}

	public List<Article> getArticlesBetweenDates(String from, String to) {
		
		return this.jdbcTemplate.query(
					"SELECT * FROM articles WHERE (date BETWEEN ? AND ?)",
					new Object[] {from, to},
					this.articleMapper
				);
	}

	public List<Article> getByOrderedScore(int size) {
		
		return this.jdbcTemplate.query(
					"SELECT * FROM articles ORDER BY score DESC LIMIT ?",
					new Object[] {size},
					this.articleMapper
				);
				
	}

	public List<Article> getArticlesBetweenServiceDates(String from, String to) {
		
		return this.jdbcTemplate.query(
					"SELECT "
					+ "articles.id, articles.title, articles.date, articles.content, articles.timestamp, "
					+ "articles.journals_id, articles.hotissues_id, articles.minor_sections_id, "
					+ "articles.hits, articles.completed_reading_count, articles.score "
					+ "FROM (SELECT * FROM half_day WHERE timestamp BETWEEN ? AND ?) AS half_day "
					+ "INNER JOIN articles "
					+ "ON half_day.articles_id = articles.id "
					+ "ORDER BY half_day.sequence",
					
					new Object[] {from, to},
					this.articleMapper
				);
	}

	public Article getBySequenceBetweenServiceDates(int sequence, String from, String to) {
		
		return this.jdbcTemplate.queryForObject(
				"SELECT "
				+ "articles.id, articles.title, articles.date, articles.content, articles.timestamp, "
				+ "articles.journals_id, articles.hotissues_id, articles.minor_sections_id, "
				+ "articles.hits, articles.completed_reading_count, articles.score "
				+ "FROM (SELECT * FROM half_day WHERE timestamp BETWEEN ? AND ? AND sequence = ?) AS half_day "
				+ "INNER JOIN articles "
				+ "ON half_day.articles_id = articles.id "
				+ "ORDER BY half_day.sequence",
				
				new Object[] {from, to, sequence},
				this.articleMapper
			);
	}
}
