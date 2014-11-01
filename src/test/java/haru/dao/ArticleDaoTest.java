package haru.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;
import haru.config.Config;
import haru.model.Article;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hamcrest.Matcher;
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
		article1 = new Article(1, "hotissue1", "journal1", "subject1", "section1", "date1", "content1", article1Imgs);
		
		List<String> article2Imgs = new ArrayList<String>();
		article2Imgs.add("http://10.73.45.130:8080/images/test/article2-1.png");
		article2Imgs.add("http://10.73.45.130:8080/images/test/article2-2.png");
		article2Imgs.add("http://10.73.45.130:8080/images/test/article2-3.png");
		article2 = new Article(2, "hotissue2", "journal2", "subject2", "section2", "date2", "content2", article2Imgs);
		
		List<String> article3Imgs = new ArrayList<String>();
		article1Imgs.add("http://10.73.45.130:8080/images/test/article3-1.png");
		article1Imgs.add("http://10.73.45.130:8080/images/test/article3-2.png");
		article1Imgs.add("http://10.73.45.130:8080/images/test/article3-3.png");
		article3 = new Article(3, "hotissue3", "journal3", "subject3", "section3", "date3", "content3", article3Imgs);
		
	}
	
	@Test
	public void addAndGet() {
		dao.add(article1);
		dao.add(article2);
		assertThat(dao.getCount(), is(2));
		
		Article articleGet1 = dao.findById(article1.getId());
		checkSameArticle(article1, articleGet1);
		
		Article articleGet2 = dao.findById(article2.getId());
		checkSameArticle(article2, articleGet2);
	}

	
	
	private void checkSameArticle(Article article1, Article article2) {
		assertThat(article1.getId(), is(article2.getId()));
		assertThat(article1.getHotIssue(), is(article2.getHotIssue()));
		assertThat(article1.getSubject(), is(article2.getSubject()));
		assertThat(article1.getSection(), is(article2.getSection()));
		assertThat(article1.getJournal(), is(article2.getJournal()));
		assertThat(article1.getDate(), is(article2.getDate()));
		assertThat(article1.getContent(), is(article2.getContent()));
		assertThat(article1.getImgs(), (Matcher) hasItems(article2));
	}

}






