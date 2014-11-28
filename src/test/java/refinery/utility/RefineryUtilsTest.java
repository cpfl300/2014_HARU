package refinery.utility;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;

public class RefineryUtilsTest {


	
	@Test
	public void getFormattedDate() {
		String actualDate1 = RefineryUtils.getFormattedDate(2014, Calendar.DECEMBER, 7, 6);
		String actualDate2 = RefineryUtils.getFormattedDate(2014, Calendar.DECEMBER, 7, 18);
		
		assertThat(actualDate1, is("2014-12-07 06:00:00"));
		assertThat(actualDate2, is("2014-12-07 18:00:00"));
		
	}
	
	@Test
	public void getDate() {
		Date actualDate1 = RefineryUtils.getDate(2014, Calendar.DECEMBER, 7, 6);
		Date actualDate2 = RefineryUtils.getDate(2014, Calendar.DECEMBER, 7, 18);
		
		TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
		Calendar expectedCal1 = Calendar.getInstance(zone);
		expectedCal1.set(2014,  Calendar.DECEMBER, 7, 6, 0, 0);
		
		Calendar expectedCal2 = Calendar.getInstance(zone);
		expectedCal2.set(2014,  Calendar.DECEMBER, 7, 18, 0, 0);
		
		assertThat(actualDate1, is(expectedCal1.getTime()));
		assertThat(actualDate2, is(expectedCal2.getTime()));
	}
	
	@Test
	public void formatDate() {
		Calendar calendar = Calendar.getInstance();
		Date today = new Date(calendar.getTimeInMillis());
		
		String actualFormattedDate = RefineryUtils.formatDate(today);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String expectedFOrmattedDate = format.format(today);
		
		assertThat(actualFormattedDate, is(expectedFOrmattedDate));
		
	}
	
	
	@Test
	public void parseFormattedDate() {
		Calendar calendar = Calendar.getInstance();
		Date expectedDate = new Date(calendar.getTimeInMillis());
		String formattedDate = RefineryUtils.formatDate(expectedDate);
		
		Date actualDate = RefineryUtils.parseFormattedDate(formattedDate);
		assertThat(actualDate.getDate(), is(expectedDate.getDate()));
		
	}

	
	
	@Test
	public void nextServiceDate() {
		List<Date> dates = new ArrayList<Date>();
		dates.add(RefineryUtils.getDate(2014, Calendar.DECEMBER, 7, 5));
		dates.add(RefineryUtils.getDate(2014, Calendar.DECEMBER, 7, 6));
		dates.add(RefineryUtils.getDate(2014, Calendar.DECEMBER, 7, 17));
		dates.add(RefineryUtils.getDate(2014, Calendar.DECEMBER, 7, 18));
		dates.add(RefineryUtils.getDate(2014, Calendar.DECEMBER, 7, 19));
		
		List<String> actualDates = new ArrayList<String>();
		
		for (Date date : dates) {
			Date next = RefineryUtils.nextServiceDate(date);
			actualDates.add(RefineryUtils.formatDate(next));
		}
		
		assertThat(actualDates.get(0), is("2014-12-07 06:00:00"));
		assertThat(actualDates.get(1), is("2014-12-07 06:00:00"));
		assertThat(actualDates.get(2), is("2014-12-07 18:00:00"));
		assertThat(actualDates.get(3), is("2014-12-07 18:00:00"));
		assertThat(actualDates.get(4), is("2014-12-08 06:00:00"));
		
	}
	
	@Test
	public void getServicFormattedDatesByDate() {
		Date date1 = RefineryUtils.getDate(2014, Calendar.DECEMBER, 7, 6);
		Date date2 = RefineryUtils.getDate(2014, Calendar.DECEMBER, 7, 18);
		
		String[] actualDates1 = RefineryUtils.getServiceFormattedDatesByDate(date1);
		String[] actualDates2 = RefineryUtils.getServiceFormattedDatesByDate(date2);
		
		assertThat(actualDates1[0], is("2014-12-06 18:00:00"));
		assertThat(actualDates1[1], is("2014-12-07 06:00:00"));
		assertThat(actualDates2[0], is("2014-12-07 06:00:00"));
		assertThat(actualDates2[1], is("2014-12-07 18:00:00"));
	}
	
	@Test
	public void getServiceDatesByTime() {
		String[] actualDates1 = RefineryUtils.getServiceDatesByTime(2014, Calendar.DECEMBER, 7, 6);
		String[] actualDates2 = RefineryUtils.getServiceDatesByTime(2014, Calendar.DECEMBER, 7, 18);
		
		assertThat(actualDates1[0], is("2014-12-06 18:00:00"));
		assertThat(actualDates1[1], is("2014-12-07 06:00:00"));
		assertThat(actualDates2[0], is("2014-12-07 06:00:00"));
		assertThat(actualDates2[1], is("2014-12-07 18:00:00"));
	}

}
