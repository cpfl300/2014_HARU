package haru.dao;

import java.util.ArrayList;
import java.util.List;

import haru.model.Article;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDao {
	

	public void add(Article article) {
		
	}

	public int getCount() {
		
		return 0;
	}

	public Article findById(long id) {
		
		List<String> article1Imgs = new ArrayList<String>();
		article1Imgs.add("http://10.73.45.130:8080/images/test/article1-1.png");
		article1Imgs.add("http://10.73.45.130:8080/images/test/article1-2.png");
		article1Imgs.add("http://10.73.45.130:8080/images/test/article1-3.png");
		Article article1 = new Article(1, "hotissue1", "journal1", "subject1", "section1", "date1", "content1", article1Imgs);
		
		return article1;
	}

}
