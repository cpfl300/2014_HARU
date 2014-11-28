package refinery.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class JournalTest {
	
	private Journal journal1;
	private Journal journal2;
	private Journal journal3;
	
	@Before
	public void setup() {
		journal1 = new Journal(84, "인벤", "스포츠/연예");
		journal2 = new Journal(10, "한국일보", "종합");
		journal3 = new Journal(23, "전자신문", "IT");
	}

	@Test
	public void hashcode() {
		Journal actual1 = new Journal("인벤");
		assertThat(actual1.hashCode(), is(journal1.hashCode()));
		
		Journal actual2 = new Journal("한국일보");
		assertThat(actual2.hashCode(), is(journal2.hashCode()));
		
		Journal actual3 = new Journal("전자신문");
		assertThat(actual3.hashCode(), is(journal3.hashCode()));
	}
	
	@Test
	public void hashcodeByName() {
		Journal actual1 = new Journal(84, "인벤", "스포츠/연예");
		assertThat(actual1.hashCode(), is(journal1.hashCode()));
		
		Journal actual2 = new Journal(10, "한국일보", "종합");
		assertThat(actual2.hashCode(), is(journal2.hashCode()));
		
		Journal actual3 = new Journal(23, "전자신문", "IT");
		assertThat(actual3.hashCode(), is(journal3.hashCode()));
	}
	
	
	@Test
	public void notHashcode() {
		Journal actual1 = new Journal(84, "인벤1", "스포츠/연예");
		assertThat(actual1.hashCode(), not(is(journal1.hashCode())));
		
		Journal actual2 = new Journal(10, "한국일보2", "종합");
		assertThat(actual2.hashCode(), not(is(journal2.hashCode())));
		
		Journal actual3 = new Journal(23, "전자신문3", "IT");
		assertThat(actual3.hashCode(), not(is(journal3.hashCode())));
	}

}
