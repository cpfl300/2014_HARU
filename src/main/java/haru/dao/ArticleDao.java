package haru.dao;

import haru.model.Article;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository(value="articleDao")
public class ArticleDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Article> articleMapper = new RowMapper<Article> () {

		@Override
		public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
			Article article = new Article();
			article.setId(rs.getString("id"));
			article.setHotissue(rs.getString("hotissue"));
			article.setSubject(rs.getString("subject"));
			article.setJournal(rs.getString("journal"));
			article.setSection(rs.getString("section"));
			article.setDate(rs.getString("date"));
			article.setContent(rs.getString("content"));
			
			return article;
		}
		
	};
	

	public void add(Article article) {
		this.jdbcTemplate.update(
				"insert into articles(id, hotissue, subject, journal, section, date, content) values (?,?,?,?,?,?,?)",
				article.getId(),
				article.getHotissue(),
				article.getSubject(),
				article.getJournal(),
				article.getSection(),
				article.getDate(),
				article.getContent()
		);
	}

	public int getCount() {
		
		return this.jdbcTemplate.queryForInt(
				"select count(*) from articles");
	}

	public Article get(String id) {
		
		return this.jdbcTemplate.queryForObject(
					"select * from articles where id = ?",
					new Object[]{id},
					this.articleMapper
				);
	}

	
	public void deleteAll() {
		this.jdbcTemplate.update("delete from articles");
	}

	public List<Article> getAll() {
		return this.jdbcTemplate.query(
					"select * from articles",
					this.articleMapper
				);
	}
	

}
