package refinery.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import refinery.utility.RefineryUtils;

public class ArticleTest {
	private static final String FACK_STR = "-----";
	
	private List<Article> articles;
	
	private Article article1;
	private Article article2;
	private Article article3;
	
	private Journal journal1;
	private Journal journal2;
	private Journal journal3;
	
	private Section section1;
	private Section section2;
	private Section section3;
	
	private Hotissue hotissue1;
	private Hotissue hotissue2;
	private Hotissue hotissue3;
	
	
	@Before
	public void setup() {
		makeFixtures();
	}

	
	@Test
	public void hashcode() {
		Article actual1 = new Article(
				new Hotissue(hotissue1.getName()),
				new Journal(journal1.getName()),
				new Section(section1.getMinor()),
				article1.getTitle(),
				article1.getDate());
		assertThat(actual1.hashCode(), is(article1.hashCode()));
		
		Article actual2 = new Article(
				new Hotissue(hotissue2.getName()),
				new Journal(journal2.getName()),
				new Section(section2.getMinor()),
				article2.getTitle(),
				article2.getDate());
		assertThat(actual2.hashCode(), is(article2.hashCode()));
		
		Article actual3 = new Article(
				new Hotissue(hotissue3.getName()), 
				new Journal(journal3.getName()), 
				new Section(section3.getMinor()), 
				article3.getTitle(), 
				article3.getDate());
		assertThat(actual3.hashCode(), is(article3.hashCode()));
	}
	
	@Test
	public void notHashcode() {
		Article actual1_1 = new Article(
				new Hotissue(hotissue1.getName() + FACK_STR), 
				new Journal(journal1.getName()), 
				new Section(section1.getMinor()), 
				article1.getTitle(), 
				article1.getDate());
		assertThat(actual1_1.hashCode(), not(is(article1.hashCode())));
		
		Article actual1_2 = new Article(
				new Hotissue(hotissue1.getName()), 
				new Journal(journal1.getName() + FACK_STR), 
				new Section(section1.getMinor()), 
				article1.getTitle(), 
				article1.getDate());
		assertThat(actual1_2.hashCode(), not(is(article1.hashCode())));
		
		Article actual1_3 = new Article(
				new Hotissue(hotissue1.getName()), 
				new Journal(journal1.getName()), 
				new Section(section1.getMinor() + FACK_STR), 
				article1.getTitle(), 
				article1.getDate());
		assertThat(actual1_3.hashCode(), not(is(article1.hashCode())));
		
		
		Article actual2 = new Article(
				new Hotissue(hotissue2.getName()), 
				new Journal(journal2.getName()), 
				new Section(section2.getMinor()), 
				article2.getTitle() + FACK_STR, 
				article2.getDate());
		assertThat(actual2.hashCode(), not(is(article2.hashCode())));
		
		Article actual3 = new Article(
				new Hotissue(hotissue3.getName()), 
				new Journal(journal3.getName()), 
				new Section(section3.getMinor()), 
				article3.getTitle(), 
				article3.getDate() + FACK_STR);
		assertThat(actual3.hashCode(), not(is(article3.hashCode())));
	}
	
	
	@Test
	public void calcScore() {
		
		for (Article article : articles) {
			
			article.clacScore();
			double actualScore = article.getScore();
			double expectedScore = (double) article.getCompletedReadingCount() / article.getHits();
			
			assertThat(actualScore, is(expectedScore));
		}
	}
	
	@Test
	public void asList() {
		List<Hotissue> hotissues = new ArrayList<Hotissue>();
		hotissue1.addArticle(article1);
		hotissue2.addArticle(article2);
		hotissue3.addArticle(article3);
		hotissues.add(hotissue1);
		hotissues.add(hotissue2);
		hotissues.add(hotissue3);
		
		List<Article> actualArticles = Article.asList(hotissues);
		
		for (int i=0; i<3; i++) {
			Article actual = actualArticles.get(i);
			assertThat(actual, is(articles.get(i)));
		}
		
	}
	
	@Test
	public void asListWithSequenceIncludeTimestamp() {
		List<Hotissue> hotissues = new ArrayList<Hotissue>();
		hotissue1.addArticle(article1);
		hotissue2.addArticle(article2);
		hotissue3.addArticle(article3);
		hotissues.add(hotissue1);
		hotissues.add(hotissue2);
		hotissues.add(hotissue3);
		
		String timestamp = RefineryUtils.getFormattedDate(2014, Calendar.DECEMBER, 7, 6);
		
		List<Article> actualArticles = Article.asListWithSequenceIncludeTimestamp(hotissues, timestamp);
		
		for (int i=0; i<3; i++) {
			Article actual = actualArticles.get(i);
			Article expected = articles.get(i);
			assertThat(actual.getId(), is(expected.getId()));
			assertThat(actual.getSequence(), is(i+1));
			assertThat(actual.getTimestamp(), is(timestamp));
		}
	}
	
	
	private void makeFixtures() {
		articles = new ArrayList<Article>();
		
		journal1 = new Journal(84, "인벤", "스포츠/연예");
		section1 = new Section(3, "정치", "북한");
		hotissue1 = new Hotissue(1, "hotissue1");
		article1 = new Article(hotissue1, journal1, section1, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000);
		
		
		journal2 = new Journal(10, "한국일보", "종합");
		section2 = new Section(10, "경제", "금융");
		hotissue2 = new Hotissue(2, "hotissue2");
		article2 = new Article(hotissue2, journal2, section2, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000);
		
		
		journal3 = new Journal(23, "전자신문", "IT");
		section3 = new Section(23, "사회", "언론");
		hotissue3 = new Hotissue(3, "hotissue3");
		article3 = new Article(hotissue3, journal3, section3, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000);
		
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
	}


}
