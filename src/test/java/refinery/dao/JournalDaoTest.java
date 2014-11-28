package refinery.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import refinery.config.RefineryConfig;
import refinery.model.Journal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RefineryConfig.class, loader=AnnotationConfigContextLoader.class)
public class JournalDaoTest {
	
	@Autowired
	private JournalDao dao;
	private Journal journal1;
	private Journal journal2;
	private Journal journal3;
	
	
	@Before
	public void setup() {
		// fixtures
		journal1 = new Journal(84, "인벤", "스포츠/연예");
		journal2 = new Journal(10, "한국일보", "종합");
		journal3 = new Journal(23, "전자신문", "IT");
	}
	
	@Test
	public void get() {
		Journal actualJournal1 = dao.get(84);
		assertSameJournal(actualJournal1, journal1);
		
		Journal actualJournal2 = dao.get(10);
		assertSameJournal(actualJournal2, journal2);
		
		Journal actualJournal3 = dao.get(23);
		assertSameJournal(actualJournal3, journal3);
	}
	
	@Test
	public void getByName() {
		Journal actualJournal1 = dao.getByName("인벤");
		assertSameJournal(actualJournal1, journal1);
		
		Journal actualJournal2 = dao.getByName("한국일보");
		assertSameJournal(actualJournal2, journal2);
		
		Journal actualJournal3 = dao.getByName("전자신문");
		assertSameJournal(actualJournal3, journal3);
	}

	private void assertSameJournal(Journal actual, Journal expected) {
		assertThat(actual.getId(), not(is(0)));
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getName(), is(expected.getName()));
		assertThat(actual.getSection(), is(expected.getSection()));
		
	}

}
