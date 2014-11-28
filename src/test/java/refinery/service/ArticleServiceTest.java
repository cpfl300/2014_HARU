package refinery.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import refinery.dao.ArticleDao;
import refinery.dao.JournalDao;
import refinery.dao.SectionDao;
import refinery.model.Article;
import refinery.model.Hotissue;
import refinery.model.Journal;
import refinery.model.Section;
import refinery.utility.RefineryUtils;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {
	
	@InjectMocks
	private ArticleService articleService;
	
	@Mock
	private ArticleDao articleDaoMock;
	
	@Mock
	private JournalDao journalDaoMock;
	
	@Mock
	private SectionDao sectionDaoMock;
	
	@Mock
	private HotissueService hotissueServiceMock;
	
	
	private Article article1;
	private Journal journal1;
	private Section section1;
	private Hotissue hotissue1;
	
	private Article article2;
	private Journal journal2;
	private Section section2;
	private Hotissue hotissue2;
	
	private Article article3;
	private Journal journal3;
	private Section section3;
	private Hotissue hotissue3;
	
	private List<Article> articles;

	@Before
	public void setup() {
		makeJournalDaoMocks();
		makeSectionDaoMocks();
		
		article1 = new Article(hotissue1, journal1, section1, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);		
		article2 = new Article(hotissue2, journal2, section2, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		article3 = new Article(hotissue3, journal3, section3, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 10.1);
	}

	@Test
	public void has() {
		when(articleDaoMock.get(article1.hashCode())).thenReturn(article1);
		assertThat(articleService.has(article1.hashCode()), is(true));
		
		when(articleDaoMock.get(article2.hashCode())).thenReturn(article2);
		assertThat(articleService.has(article2.hashCode()), is(true));
		
		when(articleDaoMock.get(article3.hashCode())).thenReturn(article3);
		assertThat(articleService.has(article3.hashCode()), is(true));
	}
	
	@Test
	public void hasNot() {
		when(articleDaoMock.get(article1.hashCode())).thenThrow(EmptyResultDataAccessException.class);
		assertThat(articleService.has(article1.hashCode()), is(false));
		
		when(articleDaoMock.get(article2.hashCode())).thenThrow(EmptyResultDataAccessException.class);
		assertThat(articleService.has(article2.hashCode()), is(false));
		
		when(articleDaoMock.get(article3.hashCode())).thenThrow(EmptyResultDataAccessException.class);
		assertThat(articleService.has(article3.hashCode()), is(false));
	}
	
	
	@Test
	public void add() {
		int expectedArticle1Id = article1.hashCode();
		assertThat(articleService.add(article1), is(expectedArticle1Id));
		
		int expectedArticle2Id = article2.hashCode();
		assertThat(articleService.add(article2), is(expectedArticle2Id));
		
		int expectedArticle3Id = article3.hashCode();
		assertThat(articleService.add(article3), is(expectedArticle3Id));
	}
	
	@Test
	public void notAdd() {
		doThrow(DuplicateKeyException.class).when(articleDaoMock).add(any(Article.class));
		
		int expectedArticle1Id = article1.hashCode();
		assertThat(articleService.add(article1), is(expectedArticle1Id));
		
		int expectedArticle2Id = article2.hashCode();
		assertThat(articleService.add(article2), is(expectedArticle2Id));
		
		int expectedArticle3Id = article3.hashCode();
		assertThat(articleService.add(article3), is(expectedArticle3Id));
	}
	
	
	@Test
	public void delete() {
		makeHotissueServiceMocks();
		article1.setHotissue(hotissue1);
		article2.setHotissue(hotissue2);
		article3.setHotissue(hotissue3);
		
		when(articleDaoMock.get(article1.hashCode())).thenReturn(article1);		
		when(articleDaoMock.get(article2.hashCode())).thenReturn(article2);
		when(articleDaoMock.get(article3.hashCode())).thenReturn(article3);
		
		when(articleDaoMock.delete(article1.hashCode())).thenReturn(1);
		when(articleDaoMock.delete(article2.hashCode())).thenReturn(1);
		when(articleDaoMock.delete(article3.hashCode())).thenReturn(1);

		when(hotissueServiceMock.delete(hotissue1.getId())).thenReturn(1);
		when(hotissueServiceMock.delete(hotissue2.getId())).thenReturn(1);
		when(hotissueServiceMock.delete(hotissue3.getId())).thenReturn(1);
		
		assertThat(articleService.delete(article1.hashCode()), is(1));
		assertThat(articleService.delete(article1.hashCode()), is(1));
		assertThat(articleService.delete(article1.hashCode()), is(1));
	}
	
	@Test
	public void addArticles() {
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		when(articleDaoMock.addArticles(articles)).thenReturn(new int[] {1, 1, 1});
		
		int actualCount = articleService.addArticles(articles);
		
		assertThat(actualCount, is(3));
	}
	
	@Test
	public void addArticlesIncludedDuplicateKey() {
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article1);
		articles.add(article1);
		
		when(articleDaoMock.addArticles(articles)).thenReturn(new int[] {1, 0, 0});
		
		int actualCount = articleService.addArticles(articles);
		
		assertThat(actualCount, is(1));
	}
	
	@Test
	public void calcScore() {
		String[] dates = RefineryUtils.getServiceDatesByTime(2014, Calendar.DECEMBER, 7, 6);
		
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		when(articleDaoMock.getArticlesBetweenDates(dates[0], dates[1])).thenReturn(articles);
		when(articleDaoMock.updateScores(articles)).thenReturn(new int[] {1, 1, 1});
		assertThat(articleService.calcScore(dates[0], dates[1]), is(3));
	}
	
	@Test
	public void getArticlesBetweenDates() {
		String[] mornings = RefineryUtils.getServiceDatesByTime(2014, Calendar.DECEMBER, 7, 6);
		String[] afternoons = RefineryUtils.getServiceDatesByTime(2014, Calendar.DECEMBER, 7, 18);
		
		// fixtures
		List<Article> articles = new ArrayList<Article>();
		articles.add(new Article(1, hotissue1, journal1, section1, "title1", "2014-12-06 17:59:59", "content1", 10000, 11000));
		articles.add(new Article(2, hotissue2, journal2, section2, "title2", "2014-12-06 18:00:00", "content2", 20000, 12000));
		articles.add(new Article(3, hotissue3, journal3, section3, "title3", "2014-12-07 05:59:59", "content3", 30000, 13000));
		articles.add(new Article(4, hotissue1, journal1, section1, "title4", "2014-12-07 06:00:00", "content4", 40000, 14000));
		articles.add(new Article(5, hotissue2, journal2, section2, "title5", "2014-12-07 17:59:59", "content5", 50000, 15000));
		articles.add(new Article(6, hotissue3, journal3, section3, "title6", "2014-12-07 18:00:00", "content6", 60000, 16000));
		
		List<Article> morningArticles = Arrays.asList(new Article[] {articles.get(1), articles.get(2)});		
		List<Article> afternoonArticles = Arrays.asList(new Article[] {articles.get(3), articles.get(4)});


		when(articleDaoMock.getArticlesBetweenDates(mornings[0], mornings[1])).thenReturn(morningArticles);		
		when(articleDaoMock.getArticlesBetweenDates(afternoons[0], afternoons[1])).thenReturn(afternoonArticles);
		
		
		List<Article> actualMorningArticles = articleService.getArticlesBetweenDates(mornings[0], mornings[1]);
		assertThat(actualMorningArticles.size(), is(2));
		assertThat(actualMorningArticles.get(0).getId(), is(2));
		assertThat(actualMorningArticles.get(1).getId(), is(3));
		
		List<Article> actualAfternoonArticles = articleService.getArticlesBetweenDates(afternoons[0], afternoons[1]);
		assertThat(actualAfternoonArticles.size(), is(2));
		assertThat(actualAfternoonArticles.get(0).getId(), is(4));
		assertThat(actualAfternoonArticles.get(1).getId(), is(5));
	}


	@Test
	public void getByOrderedScore() {
		final int size = 2;
		
		article1.setScore(10.1);
		article2.setScore(20.1);
		article3.setScore(30.1);
		
		when(articleDaoMock.getByOrderedScore(size)).thenReturn(Arrays.asList(new Article[]{article3, article2}));
		
		List<Article> actualArticles = articleService.getByOrderedScore(size);
		assertThat(actualArticles.size(), is(size));
		assertThat(actualArticles.get(0).getScore(), is(30.1));
		assertThat(actualArticles.get(1).getScore(), is(20.1));
	}
	
	@Test
	public void getArticlesByServiceDate() {
		Date date = RefineryUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		String[] dates = RefineryUtils.getServiceFormattedDatesByDate(date);
		
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		when(articleDaoMock.getArticlesBetweenServiceDates(dates[0], dates[1])).thenReturn(articles);
		
		List<Article> actualArticles = articleService.getArticlesByServiceDate(date);
		assertThat(actualArticles.size(), is(articles.size()));

	}
	
	@Test
	public void getBySequenceAndServiceDate() {
		Date date = RefineryUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		String[] dates = RefineryUtils.getServiceFormattedDatesByDate(date);
		int sequence = 1;
		
		when(articleDaoMock.getBySequenceBetweenServiceDates(sequence, dates[0], dates[1])).thenReturn(article1);
		
		Article actualArticle = articleService.getBySequenceAndServiceDate(sequence, date);
		assertThat(actualArticle.getId(), is(article1.getId()));

	}


	private void makeHotissueServiceMocks() {
		hotissue1 = new Hotissue("hotissue1", "1001-01-01 01:11:11");
		hotissue1.setId(hotissue1.hashCode());
		
		hotissue2 = new Hotissue("hotissue2", "1002-02-02 02:11:11");
		hotissue2.setId(hotissue2.hashCode());
		
		hotissue3 = new Hotissue("hotissue3", "1003-03-03 03:11:11");
		hotissue3.setId(hotissue3.hashCode());
		
		when(hotissueServiceMock.add(hotissue1)).thenReturn(hotissue1.getId());
		when(hotissueServiceMock.add(hotissue2)).thenReturn(hotissue2.getId());
		when(hotissueServiceMock.add(hotissue3)).thenReturn(hotissue3.getId());
	}

	private void makeSectionDaoMocks() {
		section1 = new Section(1, "section_major1","section_minor1"); 
		section2 = new Section(2, "section_major2","section_minor2");
		section3 = new Section(3, "section_major3","section_minor3");
		
		when(sectionDaoMock.getByMinor(section1.getMinor())).thenReturn(section1);
		when(sectionDaoMock.getByMinor(section2.getMinor())).thenReturn(section2);
		when(sectionDaoMock.getByMinor(section3.getMinor())).thenReturn(section3);
	}

	private void makeJournalDaoMocks() {
		journal1 = new Journal(1, "journal1", "journal_section1");
		journal2 = new Journal(2, "journal2", "journal_section2");
		journal3 = new Journal(3, "journal3", "journal_section3");
		
		when(journalDaoMock.getByName(journal1.getName())).thenReturn(journal1);
		when(journalDaoMock.getByName(journal2.getName())).thenReturn(journal2);
		when(journalDaoMock.getByName(journal3.getName())).thenReturn(journal3);
	}
	
}
