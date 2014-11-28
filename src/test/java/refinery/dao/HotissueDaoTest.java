package refinery.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
public class HotissueDaoTest {
	
	private static final Logger log = LoggerFactory.getLogger(HotissueDaoTest.class);
	
	@Autowired
	private HotissueDao hotissueDao;
	
	@Autowired
	private ArticleDao articleDao;
	
	private Hotissue hotissue1;
	private Hotissue hotissue2;
	private Hotissue hotissue3;
	
	private Journal journal;
	private Section section;
	
	private List<Hotissue> hotissues;
	
	@Before
	public void setup() {
		
		hotissue1 = new Hotissue(1, "hotissue1", "1111-01-01 01:11:11");
		hotissue2 = new Hotissue(2, "hotissue2", "1222-02-02 02:11:11");
		hotissue3 = new Hotissue(3, "hotissue3", "1333-03-03 03:11:11");
		
		journal = new Journal(84);
		section = new Section(3);
	}
	
	@Test
	public void deleteAll() {
		hotissueDao.deleteAll();
		
	}
	
	@Test
	public void getCount() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
	}
	
	@Test
	public void add() {
		prepareHotissueDao();
		
		hotissueDao.add(hotissue1);
		assertThat(hotissueDao.getCount(), is(1));
		
		hotissueDao.add(hotissue2);
		assertThat(hotissueDao.getCount(), is(2));
		
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
	}
	
	@Test
	public void addHotissue() {
		prepareHotissueDao();
		
		hotissues = new ArrayList<Hotissue>();
		hotissues.add(hotissue1);
		hotissues.add(hotissue2);
		hotissues.add(hotissue3);
		
		hotissueDao.addHotissues(hotissues);
		assertThat(hotissueDao.getCount(), is(3));
	
	}
	
	@Test
	public void addHotissueIncludedDucplicateKey() {
		prepareHotissueDao();
		
		hotissues = new ArrayList<Hotissue>();
		hotissues.add(hotissue1);
		hotissues.add(hotissue1);
		hotissues.add(hotissue1);
		
		int actualCounts[] = hotissueDao.addHotissues(hotissues);
		int actualCount = 0;
		for (int affectedRow : actualCounts) {
			actualCount += affectedRow;
		}

		assertThat(actualCount, is(1));
	}
	
	
	@Test
	public void getWithArticlesById() {
		prepareHotissueDao();
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);

		Article article1 = new Article(1, hotissue1, journal, section, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		Article article2 = new Article(2, hotissue1, journal, section, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		Article article3 = new Article(3, hotissue1, journal, section, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 10.1);
		
		articleDao.add(article1);
		articleDao.add(article2);
		articleDao.add(article3);
		assertThat(articleDao.getCount(), is(3));
		
		Hotissue actualHotissue = hotissueDao.getWithArticlesById(hotissue1.getId());
		assertThat(actualHotissue.getArticles().size(), is(3));
	}
	
	
	@Test
	public void getWithArticlesByOrderedScore() {
		final int size = 2;
		
		prepareHotissueDao();
		hotissue1.setScore(10.1);
		hotissue2.setScore(20.1);
		hotissue3.setScore(30.1);
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);

		Article article11 = new Article(11, hotissue1, journal, section, "title11", "1111-01-01 01:11:11", "content11", 11000, 7100, 10.1);
		Article article12 = new Article(12, hotissue1, journal, section, "title12", "1111-01-01 01:11:12", "content12", 12000, 7200, 20.1);
		Article article21 = new Article(21, hotissue2, journal, section, "title21", "1222-02-02 02:11:11", "content21", 21000, 8100, 10.1);
		Article article22 = new Article(22, hotissue2, journal, section, "title22", "1222-02-02 02:11:12", "content22", 22000, 8200, 20.1);
		Article article31 = new Article(31, hotissue3, journal, section, "title31", "1333-03-03 03:11:11", "content31", 31000, 9100, 10.1);
		Article article32 = new Article(32, hotissue3, journal, section, "title32", "1333-03-03 03:11:12", "content32", 32000, 9200, 20.1);
		
		articleDao.add(article11);
		articleDao.add(article12);
		articleDao.add(article21);
		articleDao.add(article22);
		articleDao.add(article31);
		articleDao.add(article32);
		
		assertThat(articleDao.getCount(), is(6));
		assertThat(hotissueDao.getCount(), is(3));
		
		List<Hotissue> actualHotissues = hotissueDao.getWithArticlesByOrderedScore(size);
		
		assertThat(actualHotissues.size(), is(size));
		assertThat(actualHotissues.get(0).getArticles().get(0).getScore(), is(20.1));
		assertThat(actualHotissues.get(1).getArticles().get(0).getScore(), is(20.1));
		
	}
	
	
	@Test
	public void addAndGet() {
		prepareHotissueDao();
		
		hotissueDao.add(hotissue1);
		Hotissue actualHotissue1 = hotissueDao.get(1);
		assertSameHotissue(actualHotissue1, hotissue1);
		
		hotissueDao.add(hotissue2);
		Hotissue actualHotissue2 = hotissueDao.get(2);
		assertSameHotissue(actualHotissue2, hotissue2);
		
		hotissueDao.add(hotissue3);
		Hotissue actualHotissue3 = hotissueDao.get(3);
		assertSameHotissue(actualHotissue3, hotissue3);
	}
	
	@Test
	public void get() {
		prepareHotissueDao();
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		Hotissue actualHotissue1 = hotissueDao.get(hotissue1.getId());
		assertSameHotissue(actualHotissue1, hotissue1);
		
		Hotissue actualHotissue2 = hotissueDao.get(hotissue2.getId());
		assertSameHotissue(actualHotissue2, hotissue2);
		
		Hotissue actualHotissue3 = hotissueDao.get(hotissue3.getId());
		assertSameHotissue(actualHotissue3, hotissue3);
	}


	@Test
	public void getByOrderedScore() {
		prepareHotissueDao();
		
		hotissue1.setScore(10.1);
		hotissue2.setScore(20.1);
		hotissue3.setScore(30.1);
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		
		final int size = 2;
		List<Hotissue> actualHotissues = hotissueDao.getByOrderedScore(size);
		
		assertThat(actualHotissues.size(), is(size));
		assertThat(actualHotissues.get(0).getScore(), is(30.1));
		assertThat(actualHotissues.get(1).getScore(), is(20.1));
	}
	
	@Test
	public void addWithTimestamp() {
		prepareHotissueDao();
		
		hotissueDao.addWithTimestamp(hotissue1);
		hotissueDao.addWithTimestamp(hotissue2);
		hotissueDao.addWithTimestamp(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		Hotissue actualHotissue1 = hotissueDao.get(hotissue1.getId());
		assertSameHotissue(actualHotissue1, hotissue1);
		
		Hotissue actualHotissue2 = hotissueDao.get(hotissue2.getId());
		assertSameHotissue(actualHotissue2, hotissue2);
		
		Hotissue actualHotissue3 = hotissueDao.get(hotissue3.getId());
		assertSameHotissue(actualHotissue3, hotissue3);
		
	}
	
	@Test
	public void getLatestHotissues() {
		prepareHotissueDao();
		
		hotissueDao.addWithTimestamp(hotissue1);
		hotissueDao.addWithTimestamp(hotissue2);
		hotissueDao.addWithTimestamp(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		List<Hotissue> actualHotissues = hotissueDao.getLatestHotissues(3);
		assertSameHotissue(actualHotissues.get(0), hotissue3);
		assertSameHotissue(actualHotissues.get(1), hotissue2);
		assertSameHotissue(actualHotissues.get(2), hotissue1);
		
	}
	
	@Test
	public void getByName() {
		prepareHotissueDao();
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		Hotissue actualHotissue1 = hotissueDao.getByName(hotissue1.getName());
		assertSameHotissue(actualHotissue1, hotissue1);
		
		Hotissue actualHotissue2 = hotissueDao.getByName(hotissue2.getName());
		assertSameHotissue(actualHotissue2, hotissue2);
		
		Hotissue actualHotissue3 = hotissueDao.getByName(hotissue3.getName());
		assertSameHotissue(actualHotissue3, hotissue3);
		
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void notGetByName() {
		prepareHotissueDao();
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		Hotissue fakeHotissue = new Hotissue("face hotissue");
		Hotissue actualHotissue = hotissueDao.getByName(fakeHotissue.getName());
		assertSameHotissue(actualHotissue, fakeHotissue);
	}
	
	@Test
	public void delete() {
		prepareHotissueDao();
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		assertThat(hotissueDao.delete(1), is(1));
		assertThat(hotissueDao.getCount(), is(2));
		
		assertThat(hotissueDao.delete(2), is(1));
		assertThat(hotissueDao.getCount(), is(1));
		
		assertThat(hotissueDao.delete(3), is(1));
		assertThat(hotissueDao.getCount(), is(0));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void notDelete() {
		prepareHotissueDao();
		
		hotissueDao.add(hotissue1);
		
		Journal journal = new Journal(84);
		Section section = new Section(3);
		Hotissue hotissue = new Hotissue(1);
		
		Article article = new Article(1, hotissue, journal, section, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		articleDao.add(article);
		
		hotissueDao.delete(1);
	}
	
	@Test
	public void updateScores() {
		prepareHotissueDao();
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		
		List<Hotissue> updatedHotissues = new ArrayList<Hotissue>();
		updatedHotissues.add(new Hotissue(hotissue1.getId(), 11.1));
		updatedHotissues.add(new Hotissue(hotissue2.getId(), 22.2));
		updatedHotissues.add(new Hotissue(hotissue3.getId(), 33.3));
		
		int[] state = hotissueDao.updateScores(updatedHotissues);
		
		assertThat(getCount(state), is(3));
		assertThat(hotissueDao.get(hotissue1.getId()).getScore(), is(updatedHotissues.get(0).getScore()));
		assertThat(hotissueDao.get(hotissue2.getId()).getScore(), is(updatedHotissues.get(1).getScore()));
		assertThat(hotissueDao.get(hotissue3.getId()).getScore(), is(updatedHotissues.get(2).getScore()));
		
	}
	
//	@Test
//	public void getBetweenServiceDates() {
//		Date date = RefineryUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
//		String[] dates = RefineryUtils.getServiceFormattedDatesByDate(date);
//		
//		List<Hotissue> actualHotissues = hotissueDao.getBetweenServiceDates(dates[0], dates[1]);
//		
//		for(Hotissue h : actualHotissues) {
//			log.debug("id: " + h.getId());
//		}
//
//	}
	

	private void assertSameHotissue(Hotissue actual, Hotissue expected) {
		assertThat(actual.getName(), is(expected.getName()));
		
		if(expected.getTimestamp().charAt(0) != '1') {
			assertThat(actual.getTimestamp(), is(expected.getTimestamp()));
		}
	}
	
	private void prepareHotissueDao() {
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
	}
	
	private int getCount(int[] affectedRows) {
		int size = 0;
		
		for (int row : affectedRows) {
			size += row;
		}
		
		return size;
	}

}
