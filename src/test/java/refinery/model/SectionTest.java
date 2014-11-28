package refinery.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SectionTest {
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
	public void hashcode() {
		Section actual1 = new Section("북한");
		assertThat(actual1.hashCode(), is(section1.hashCode()));
		
		Section actual2 = new Section("금융");
		assertThat(actual2.hashCode(), is(section2.hashCode()));
		
		Section actual3 = new Section("언론");
		assertThat(actual3.hashCode(), is(section3.hashCode()));
	}
	
	@Test
	public void hashcodeByMinor() {
		Section actual1 = new Section(3, "정치", "북한");
		assertThat(actual1.hashCode(), is(section1.hashCode()));
		
		Section actual2 = new Section(10, "경제", "금융");
		assertThat(actual2.hashCode(), is(section2.hashCode()));
		
		Section actual3 = new Section("언론");
		assertThat(actual3.hashCode(), is(section3.hashCode()));
	}
	
	@Test
	public void notHashcode() {
		Section actual1 = new Section(3, "정치", "북한1");
		assertThat(actual1.hashCode(), not(is(section1.hashCode())));
		
		Section actual2 = new Section(10, "경제", "금융2");
		assertThat(actual2.hashCode(), not(is(section2.hashCode())));
		
		Section actual3 = new Section(23, "사회", "언론3");
		assertThat(actual3.hashCode(), not(is(section3.hashCode())));
	}

}
