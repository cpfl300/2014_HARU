package haru.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import haru.config.TestConfig;
import haru.model.Article;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class, loader=AnnotationConfigContextLoader.class)
public class ArticleDaoTest {
	
	@Autowired
	private ArticleDao articleDao;
	
	private Article article1;
	private Article article2;
	private Article article3;
	
	@Before
	public void setUp() {
		// setup fixture
		List<String> article1Imgs = new ArrayList<String>();
		article1Imgs.add("/images/test/article1-1.png");
		article1Imgs.add("/images/test/article1-2.png");
		article1Imgs.add("/images/test/article1-3.png");
		article1 = new Article("article1", "hotissue1", "journal1", "subject1", "section1", "2014-11-01 06:32:00", "content1", article1Imgs);
		
		List<String> article2Imgs = new ArrayList<String>();
		article2Imgs.add("/images/test/article2-1.png");
		article2Imgs.add("/images/test/article2-2.png");
		article2Imgs.add("/images/test/article2-3.png");
		article2 = new Article("article2", "hotissue2", "journal2", "subject2", "section2", "2014-11-02 06:32:00", "content2", article2Imgs);
		
		List<String> article3Imgs = new ArrayList<String>();
		article3Imgs.add("/images/test/article3-1.png");
		article3Imgs.add("/images/test/article3-2.png");
		article3Imgs.add("/images/test/article3-3.png");
		article3 = new Article("article3", "hotissue3", "journal3", "subject3", "section3", "2014-11-03 06:32:00", "content3", article3Imgs);
		
	}
	
	@Test
	public void count() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		assertThat(articleDao.getCount(), is(1));
		
		articleDao.add(article2);
		assertThat(articleDao.getCount(), is(2));
		
		articleDao.add(article3);
		assertThat(articleDao.getCount(), is(3));
	}
	
	@Test
	public void addAndGet() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		articleDao.add(article2);
		articleDao.add(article3);
		assertThat(articleDao.getCount(), is(3));
		
		Article articleGet1 = articleDao.get("article1");
		checkSameArticle(article1, articleGet1);
		
		Article articleGet2 = articleDao.get("article2");
		checkSameArticle(article2, articleGet2);
		
		Article articleGet3 = articleDao.get("article3");
		checkSameArticle(article3, articleGet3);
	}
	
	@Test
	public void getIssue() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		articleDao.add(article2);
		
		Article articleGet1 = articleDao.getHotissue("article1");
		assertThat(articleGet1.getId(), is(article1.getId()));
		assertThat(articleGet1.getHotissue(), is(article1.getHotissue()));
		
		Article articleGet2 = articleDao.getHotissue("article2");
		assertThat(articleGet2.getId(), is(article2.getId()));
		assertThat(articleGet2.getHotissue(), is(article2.getHotissue()));
	}
	
	@Test
	public void getLatestIssues() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		List<Article> article1_1 = articleDao.getLatestHotissues(1);
		assertThat(article1_1.size(), is(1));
		assertThat(article1_1.get(0).getId(), is(article1.getId()));
		
		
		articleDao.add(article2);
		List<Article> article2_1 = articleDao.getLatestHotissues(1);
		assertThat(article2_1.size(), is(1));
		assertThat(article2_1.get(0).getId(), is(article1.getId()));
		
		List<Article> article2_2 = articleDao.getLatestHotissues(2);
		assertThat(article2_2.size(), is(2));
		assertThat(article2_2.get(1).getId(), is(article2.getId()));
		
		
		articleDao.add(article3);
		List<Article> article3_1 = articleDao.getLatestHotissues(1);
		assertThat(article3_1.size(), is(1));
		assertThat(article3_1.get(0).getId(), is(article1.getId()));
		
		List<Article> article3_2 = articleDao.getLatestHotissues(2);
		assertThat(article3_2.size(), is(2));
		assertThat(article3_2.get(1).getId(), is(article2.getId()));
		
		List<Article> article3_3 = articleDao.getLatestHotissues(3);
		assertThat(article3_3.size(), is(3));
		assertThat(article3_3.get(2).getId(), is(article3.getId()));
	}
	
	@Test
	public void getAll() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));

		List<Article> articles0 = articleDao.getAll();
		assertThat(articles0.size(), is(0));
		
		// article1
		articleDao.add(article1);
		List<Article> articles1 = articleDao.getAll();
		assertThat(articles1.size(), is(1));
		checkSameArticle(article1, articles1.get(0));
		
		// article3
		articleDao.add(article3);
		List<Article> articles2 = articleDao.getAll();
		assertThat(articles2.size(), is(2));
		checkSameArticle(article1, articles2.get(0));
		checkSameArticle(article3, articles2.get(1));
		
		// article2
		articleDao.add(article2);
		List<Article> articles3 = articleDao.getAll();
		assertThat(articles3.size(), is(3));
		checkSameArticle(article1, articles3.get(0));
		checkSameArticle(article2, articles3.get(1));
		checkSameArticle(article3, articles3.get(2));
	}
	
	private void checkSameArticle(Article article1, Article article2) {
		assertThat(article1.getId(), is(article2.getId()));
		assertThat(article1.getHotissue(), is(article2.getHotissue()));
		assertThat(article1.getSubject(), is(article2.getSubject()));
		assertThat(article1.getSection(), is(article2.getSection()));
		assertThat(article1.getJournal(), is(article2.getJournal()));
		assertThat(article1.getDate(), is(article2.getDate()));
		assertThat(article1.getContent(), is(article2.getContent()));
		//assertThat(article1.getImgs(), (Matcher) hasItems(article2));
	}
}






