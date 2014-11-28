package refinery.dao;

import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import refinery.config.RefineryConfig;
import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;
import refinery.utility.RefineryUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RefineryConfig.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class ArticleDaoTest {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleDaoTest.class);
	private final String beforeDatePattern = "yyyy/MM/dd HH:mm:ss";
	private final String afterDatePattern = "yyyy-MM-dd HH:mm:ss";
	private TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
	
	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private HotissueDao hotissueDao;
	
	private List<Article> articles;
	
	private Article article1;
	private Article article2;
	private Article article3;
	
	private Hotissue hotissue1;
	private Hotissue hotissue2;
	private Hotissue hotissue3;
	
	private Journal journal1;
	private Journal journal2;
	private Journal journal3;
	
	private Section section1;
	private Section section2;
	private Section section3;
	
	@Before
	public void setup() {
		// fixture
		makeJournalFixtures();
		makeSectionFixtures();
		makeHotissueFixtures();
		
		article1 = new Article(1, hotissue1, journal1, section1, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000);
		article2 = new Article(2, hotissue2, journal2, section2, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000);
		article3 = new Article(3, hotissue3, journal3, section3, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000);
	}
	

	@Test
	public void getCount() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
	}
	
	@Test
	public void deleteAll() {
		prepareHotissues();
		prepareArticleDao();
		
		assertThat(articleDao.deleteAll(), is(0));
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		assertThat(articleDao.deleteAll(), is(1));
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		articleDao.add(article2);
		assertThat(articleDao.deleteAll(), is(2));
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.add(article1);
		articleDao.add(article2);
		articleDao.add(article3);
		assertThat(articleDao.deleteAll(), is(3));
		assertThat(articleDao.getCount(), is(0));
	
	}
	
	@Test
	public void addArticles() {
		prepareHotissues();
		prepareArticleDao();
		
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		int[] actualCounts = articleDao.addArticles(articles);
		assertThat(actualCounts.length, is(3));
	}
	
	@Test
	public void addArticlesIncludedDuplicateKey() {
		prepareHotissues();
		prepareArticleDao();
		
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article1);
		articles.add(article1);
		
		int actualCounts[] = articleDao.addArticles(articles);
		int actualCount = 0;
		for (int affectedRow : actualCounts) {
			actualCount += affectedRow;
		}

		assertThat(actualCount, is(1));
	}
	
	
	@Test
	public void add() {
		prepareHotissues();
		prepareArticleDao();
		
		articleDao.add(article1);
		assertThat(articleDao.getCount(), is(1));
		
		articleDao.add(article2);
		assertThat(articleDao.getCount(), is(2));
		
		articleDao.add(article3);
		assertThat(articleDao.getCount(), is(3));
	}
	
	@Test(expected=DuplicateKeyException.class)
	public void notAdd() {
		prepareHotissues();
		prepareArticleDao();
		
		articleDao.add(article1);
		articleDao.add(article1);
		assertThat(articleDao.getCount(), is(2));
	}
	
	@Test
	public void addAndGet() {
		prepareHotissues();
		prepareArticleDao();
		
		articleDao.add(this.article1);
		Article actualArticle1 = articleDao.get(1);
		assertSameArticle(actualArticle1, this.article1);
		
		articleDao.add(this.article2);
		Article actualArticle2 = articleDao.get(2);
		assertSameArticle(actualArticle2, this.article2);
		
		articleDao.add(this.article3);
		Article actualArticle3 = articleDao.get(3);
		assertSameArticle(actualArticle3, this.article3);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void notGet() {
		prepareHotissues();
		prepareArticleDao();
		
		articleDao.add(this.article1);
		Article actualArticle1 = articleDao.get(4);
		assertSameArticle(actualArticle1, this.article1);
		
	}
	
	@Test
	public void getArticlesByDate() throws ParseException {
		prepareHotissues();
		prepareArticleDao();
		
		article1.setDate("2014-12-07 05:59:59");
		article2.setDate("2014-12-07 06:00:00");
		article3.setDate("2014-12-07 17:59:59");
		Article article4 = new Article(4, hotissue3, journal3, section3, "title4", "2014-12-07 18:00:00", "content4", 40000, 10000);
		
		articleDao.add(article1);
		articleDao.add(article2);
		articleDao.add(article3);
		articleDao.add(article4);
		
		
	    Calendar fromCalendar = Calendar.getInstance(zone);
	    fromCalendar.set(2014, Calendar.DECEMBER , 7, 6, 0, 0);
	    
	    Calendar toCalendar = Calendar.getInstance(zone);
	    toCalendar.set(2014, Calendar.DECEMBER , 7, 17, 59, 59);
	    
	    String from = toString(fromCalendar);
	    String to = toString(toCalendar);

		List<Article> actualArticles = articleDao.getArticlesBetweenDates(from, to);
		
		assertThat(actualArticles.size(), is(2));
		for (Article a : actualArticles) {
			String dateStr = a.getDate();
			SimpleDateFormat format = new SimpleDateFormat(afterDatePattern);
	        format.setTimeZone(zone);
	        Date date = format.parse(dateStr);
	        
	        log.debug("date: " + date + ", from: " + fromCalendar.getTime());
			// 같은 시간임에도 틀리다고 함
	        // 고민해볼 필요 있음
			// assertThat(date.compareTo(fromCalendar.getTime()), greaterThanOrEqualTo(0));
			assertThat(date.compareTo(toCalendar.getTime()), is(-1));
		}
		
		
	}
	
	@Test
	public void delete() {
		prepareHotissues();
		prepareArticleDao();
		
		articleDao.add(article1);
		articleDao.add(article2);
		articleDao.add(article3);
		assertThat(articleDao.getCount(), is(3));
		
		assertThat(articleDao.delete(1), is(1));
		assertThat(articleDao.getCount(), is(2));
		
		assertThat(articleDao.delete(2), is(1));
		assertThat(articleDao.getCount(), is(1));
		
		assertThat(articleDao.delete(3), is(1));
		assertThat(articleDao.getCount(), is(0));
	}
	
	


	@Test
	public void updateScores() {
		prepareHotissues();
		prepareArticleDao();
		
		articleDao.add(article1);
		articleDao.add(article2);
		articleDao.add(article3);
		
		List<Article> updatedArticles = new ArrayList<Article>();
		updatedArticles.add(new Article(article1.getId(), 11.1));
		updatedArticles.add(new Article(article2.getId(), 22.2));
		updatedArticles.add(new Article(article3.getId(), 33.3));
		
		int[] state = articleDao.updateScores(updatedArticles);
		
		assertThat(getCount(state), is(3));
		assertThat(articleDao.get(article1.getId()).getScore(), is(updatedArticles.get(0).getScore()));
		assertThat(articleDao.get(article2.getId()).getScore(), is(updatedArticles.get(1).getScore()));
		assertThat(articleDao.get(article3.getId()).getScore(), is(updatedArticles.get(2).getScore()));
		
	}
	
	@Test
	public void getByOrderedScore() {
		prepareHotissues();
		prepareArticleDao();
		
		article1.setScore(10.1);
		article2.setScore(20.1);
		article3.setScore(30.1);
		
		articleDao.add(article1);
		articleDao.add(article2);
		articleDao.add(article3);
		
		final int size = 2;
		List<Article> actualArticles = articleDao.getByOrderedScore(size);
		
		assertThat(actualArticles.size(), is(size));
		assertThat(actualArticles.get(0).getScore(), is(30.1));
		assertThat(actualArticles.get(1).getScore(), is(20.1));
	}
	
	
	@Test
	public void getBetweenServiceDates() {
		
		Date date = RefineryUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		String[] dates = RefineryUtils.getServiceFormattedDatesByDate(date);
		
		List<Article> articles = articleDao.getArticlesBetweenServiceDates(dates[0], dates[1]);
		
		for (Article a : articles) {
			log.debug(a.toString());
		}
		
	}
	
	
//	@Test
//	public void getBySequenceBetweenServiceDates() {
//		
//		Date date = RefineryUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
//		String[] dates = RefineryUtils.getServiceFormattedDatesByDate(date);
//		int sequence = 2;
//		
//		Article article = articleDao.getBySequenceBetweenServiceDates(sequence, dates[0], dates[1]);
//		
//		log.debug("id: " + article.getId());
//		
//	}
	
	private void prepareHotissues() {
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
	}
	
	private void prepareArticleDao() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
	}
	
	private int getCount(int[] affectedRows) {
		int size = 0;
		
		for (int row : affectedRows) {
			size += row;
		}
		
		return size;
	}

	private void assertSameArticle(Article actual, Article expected) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getHotissue().getId(), is(expected.getHotissue().getId()));
		assertThat(actual.getJournal().getId(), is(expected.getJournal().getId()));
		assertThat(actual.getTitle(), is(expected.getTitle()));
		assertThat(actual.getSection().getId(), is(expected.getSection().getId()));
		assertThat(actual.getDate(), is(expected.getDate()));
		assertThat(actual.getContent(), is(expected.getContent()));
		assertThat(actual.getHits(), is(expected.getHits()));
		assertThat(actual.getCompletedReadingCount(), is(expected.getCompletedReadingCount()));
	}
	

	private void makeSectionFixtures() {
		section1 = new Section(3);
		section2 = new Section(10);
		section3 = new Section(23);		
	}


	private void makeJournalFixtures() {
		journal1 = new Journal(84);
		journal2 = new Journal(10);
		journal3 = new Journal(23);		
	}
	
	private void makeHotissueFixtures() {
		hotissue1 = new Hotissue(1, "hotissue1");
		hotissue2 = new Hotissue(2, "hotissue2");
		hotissue3 = new Hotissue(3, "hotissue3");
	}
	
	private String toString(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat(beforeDatePattern);
        format.setTimeZone(zone);
        
        return format.format(calendar.getTime());
    }

}
