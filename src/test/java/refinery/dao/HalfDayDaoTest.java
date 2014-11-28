package refinery.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import refinery.config.RefineryConfig;
import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;
import refinery.service.ArticleService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RefineryConfig.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class HalfDayDaoTest {
	
	@Autowired
	private HalfDayDao halfDayDao;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleDao articleDao;
	
	private List<Article> articles;
	
	private Article article1;
	private Article article2;
	private Article article3;
	
	
	@Before
	public void setup() {
		article1 = new Article(new Hotissue("hotissue1"), new Journal("국민일보"), new Section("청와대"), "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		article2 = new Article(new Hotissue("hotissue2"), new Journal("국민일보"), new Section("청와대"), "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		article3 = new Article(new Hotissue("hotissue3"), new Journal("국민일보"), new Section("청와대"), "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 10.1);
	}
	
	@Test
	public void getCount() {
		assertThat(halfDayDao.getCount(), is(0));
		
		prepareArticles();
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		assertThat(articleDao.getCount(), is(3));
		
		int[] affectedRows = halfDayDao.addArticles(articles);
		assertThat(getCount(affectedRows), is(3));
	}
	
	@Test
	public void deleteAll() {
		assertThat(halfDayDao.getCount(), is(0));
		
		prepareArticles();
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		assertThat(articleDao.getCount(), is(3));
		
		int[] affectedRows = halfDayDao.addArticles(articles);
		assertThat(getCount(affectedRows), is(3));
		
		assertThat(halfDayDao.deleteAll(), is(3));
		assertThat(halfDayDao.getCount(), is(0));
		
	}
	
	@Test
	public void addArticles() {
		prepareArticles();
		
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		assertThat(articleDao.getCount(), is(3));
		
		int[] affectedRows = halfDayDao.addArticles(articles);
		assertThat(getCount(affectedRows), is(3));
	}


	private void prepareArticles() {
		int article1Id = articleService.add(article1);
		int article2Id = articleService.add(article2);
		int article3Id = articleService.add(article3);
		
		article1 = new Article(article1Id, 1, "2014-12-07 05:50:00");
		article2 = new Article(article2Id, 2, "2014-12-07 05:50:00");
		article3 = new Article(article3Id, 3, "2014-12-07 05:50:00");
	}


	private int getCount(int[] affectedRows) {
		int size = 0;
		
		for (int row : affectedRows) {
			size += row;
		}
		
		return size;
	}
	
	

}
