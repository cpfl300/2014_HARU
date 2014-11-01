package haru.dao;

import haru.config.Config;
import haru.model.Article;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Config.class, loader=AnnotationConfigContextLoader.class)
public class ArticleDaoTest {
	
	@Autowired
	private ArticleDao dao;
	
	@Mock
	DataSource dataSource;
	
	private Article article1;
	private Article article2;
	private Article article3;
	
	@Before
	public void setUp() {
		// setup fixture
		List<String> article1Imgs = new ArrayList<String>();
		article1Imgs.add("http://10.73.45.130:8080/images/test/article1-1.png");
		article1Imgs.add("http://10.73.45.130:8080/images/test/article1-2.png");
		article1Imgs.add("http://10.73.45.130:8080/images/test/article1-3.png");
		Article article1 = new Article("hotissue1", "journal1", "subject1", "section1", "date1", "content1", article1Imgs);
		
		List<String> article2Imgs = new ArrayList<String>();
		article2Imgs.add("http://10.73.45.130:8080/images/test/article2-1.png");
		article2Imgs.add("http://10.73.45.130:8080/images/test/article2-2.png");
		article2Imgs.add("http://10.73.45.130:8080/images/test/article2-3.png");
		Article article2 = new Article("hotissue2", "journal2", "subject2", "section2", "date2", "content2", article2Imgs);
		
		List<String> article3Imgs = new ArrayList<String>();
		article1Imgs.add("http://10.73.45.130:8080/images/test/article3-1.png");
		article1Imgs.add("http://10.73.45.130:8080/images/test/article3-2.png");
		article1Imgs.add("http://10.73.45.130:8080/images/test/article3-3.png");
		Article article3 = new Article("hotissue3", "journal3", "subject3", "section3", "date3", "content3", article3Imgs);
		
	}
	
	@Test
	public void add() {
		dao.add(article1);
		dao.add(article2);
		dao.add(article3);
	}
	
	
	/*
	@Test
	public void getAll() {
		
		//dao.deleteAll();

		List<Article> articles0 = dao.getAll();
		assertThat(articles0.size(), is(0));
		
		
		dao.add(article1);
		List<Article> articles1 = dao.getAll();
		assertThat(articles1.size(), is(1));
		//checkSameArticle(article1, articles1.get(0));		
		
		
	}
	*/

}






