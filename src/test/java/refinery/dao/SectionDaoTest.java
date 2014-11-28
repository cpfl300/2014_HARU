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
import refinery.model.Section;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RefineryConfig.class, loader=AnnotationConfigContextLoader.class)
public class SectionDaoTest {
	
	@Autowired
	private SectionDao dao;
	private Section section1;
	private Section section2;
	private Section section3;
	
	@Before
	public void setup() {
		section1 = new Section(3, "정치", "북한");
		section2 = new Section(10, "경제", "금융");
		section3 = new Section(23, "사회", "언론");
	}
	
	@Test
	public void get() {
		Section actualSection1 = dao.get(3);
		assertSameSection(actualSection1, section1);
		
		Section actualSection2 = dao.get(10);
		assertSameSection(actualSection2, section2);
		
		Section actualSection3 = dao.get(23);
		assertSameSection(actualSection3, section3);
	}
	
	@Test
	public void getByMinor() {
		Section actualSection1 = dao.getByMinor("북한");
		assertSameSection(actualSection1, section1);
		
		Section actualSection2 = dao.getByMinor("금융");
		assertSameSection(actualSection2, section2);
		
		Section actualSection3 = dao.getByMinor("언론");
		assertSameSection(actualSection3, section3);
	}

	private void assertSameSection(Section actual, Section expected) {
		assertThat(actual.getId(), not(is(0)));
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getMajor(), is(expected.getMajor()));
		assertThat(actual.getMinor(), is(expected.getMinor()));
		
	}

}
