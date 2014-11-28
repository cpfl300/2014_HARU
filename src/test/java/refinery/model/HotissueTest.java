package refinery.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class HotissueTest {
	
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
		makeJournalFixtures();
		makeSectionFixtures();
		
		hotissue1 = new Hotissue("hotissue1");
		hotissue2 = new Hotissue("hotissue2");
		hotissue3 = new Hotissue("hotissue3");
	}
	@Test
	public void hashcode() {
		Hotissue actual1 = new Hotissue("hotissue1");
		assertThat(actual1.hashCode(), is(hotissue1.hashCode()));
		
		Hotissue actual2 = new Hotissue("hotissue2");
		assertThat(actual2.hashCode(), is(hotissue2.hashCode()));
		
		Hotissue actual3 = new Hotissue("hotissue3");
		assertThat(actual3.hashCode(), is(hotissue3.hashCode()));
	}
	
	@Test
	public void hashcodeByName() {
		Hotissue actual1 = new Hotissue(1, "hotissue1");
		assertThat(actual1.hashCode(), is(hotissue1.hashCode()));
		
		Hotissue actual2 = new Hotissue(2, "hotissue2");
		assertThat(actual2.hashCode(), is(hotissue2.hashCode()));
		
		Hotissue actual3 = new Hotissue(3, "hotissue3");
		assertThat(actual3.hashCode(), is(hotissue3.hashCode()));
	}
	
	
	@Test
	public void notHashcode() {
		Hotissue actual1 = new Hotissue(1, "hotissue11");
		assertThat(actual1.hashCode(), not(is(hotissue1.hashCode())));
		
		Hotissue actual2 = new Hotissue(2, "hotissue22");
		assertThat(actual2.hashCode(), not(is(hotissue2.hashCode())));
		
		Hotissue actual3 = new Hotissue(3, "hotissue33");
		assertThat(actual3.hashCode(), not(is(hotissue3.hashCode())));
	}
	
	@Test
	public void calcScore() {
		List<Article> articles = makeScoredArticleFixtures();
		double expected = 0;
		for (Article a : articles) {
			expected += a.getScore();
		}
		expected /= 3;
		
		hotissue1.setArticles(articles);
		hotissue1.calcScore();
		double actual = hotissue1.getScore();
		assertThat(actual, is(expected));
		
	}
	
	@Test(expected=EmptyArticleListException.class)
	public void notCalcScore() {
		hotissue1.calcScore();
		
		assertThat(hotissue1.getScore(), is(0));
		
	}
	
	@Test
	public void addArticle() {
		List<Article> articles = makeArticleFixtures();
		
		for (Article a : articles) {
			hotissue1.addArticle(a);
		}
		
		assertThat(hotissue1.getArticles().size(), is(articles.size()));
		
	}
	
	
	@Test
	public void orderBy() {
		List<Article> articles = makeArticleFixtures();
		
		List<Hotissue> hotissues = Hotissue.orderByHotissue(articles);
		
		assertThat(hotissues.size(), is(3));
		
		for (Hotissue h : hotissues) {
			
			for (Article a : h.getArticles()) {
				assertThat(a.getHotissue().getName(), is(h.getName()));
			}
			
		}
		
	}
	
	
	private List<Article> makeArticleFixtures() {
		List<Article> articles = new ArrayList<Article>();
		
		articles.add(new Article(11, hotissue1, journal1, section1, "title1-1", "1111-01-01 01:11:11", "content1-1", 11000, 7100, 1.1));
		articles.add(new Article(12, hotissue1, journal1, section1, "title1-2", "1111-01-01 01:11:12", "content1-2", 12000, 7200, 1.2));
		articles.add(new Article(13, hotissue1, journal1, section1, "title1-3", "1111-01-01 01:11:13", "content1-3", 13000, 7300, 1.3));
		
		articles.add(new Article(21, hotissue2, journal2, section2, "title2-1", "1222-02-02 02:11:11", "content2-1", 21000, 8100, 2.1));
		articles.add(new Article(22, hotissue2, journal2, section2, "title2-2", "1222-02-02 02:11:12", "content2-2", 22000, 8200, 2.2));
		articles.add(new Article(23, hotissue2, journal2, section2, "title2-3", "1222-02-02 02:11:13", "content2-3", 23000, 8300, 2.3));
		
		articles.add(new Article(31, hotissue3, journal3, section3, "title3-1", "1333-03-03 03:11:11", "content3-1", 31000, 9100, 3.1));
		articles.add(new Article(32, hotissue3, journal3, section3, "title3-2", "1333-03-03 03:11:12", "content3-2", 32000, 9200, 3.2));
		articles.add(new Article(33, hotissue3, journal3, section3, "title3-3", "1333-03-03 03:11:13", "content3-3", 33000, 9300, 3.3));
		
		return articles;
	}
	
	private List<Article> makeScoredArticleFixtures() {

		List<Article> articles = new ArrayList<Article>();
		
		articles.add(new Article(1, 1.1));
		articles.add(new Article(2, 2.2));
		articles.add(new Article(3, 3.3));
		
		return articles;
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
	
}
