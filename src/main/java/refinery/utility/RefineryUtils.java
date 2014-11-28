package refinery.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefineryUtils {
	private static final Logger log = LoggerFactory.getLogger(RefineryUtils.class);
	
	static final int HALF_A_DAY_TIME = 12;
	static final int MORNING_SERVICE_TIME = 6;
	static final int AFTERNOON_SERVICE_TIME = MORNING_SERVICE_TIME + HALF_A_DAY_TIME;
	
	static final String FORMAT_IN_MYSQL = "yyyy/MM/dd HH:mm:ss";
	static final String FORMAT_IN_ARTICLE = "yyyy-MM-dd HH:mm:ss";
	static final int OFFSET_HOURS_IN_SERVICE = -12;
	static final String KOREA_ZONE_ID = "Asia/Seoul";
	
	private static final TimeZone KOREA_ZONE = TimeZone.getTimeZone(KOREA_ZONE_ID);
	private static SimpleDateFormat DATE_FORMAT_IN_ARTILCE = new SimpleDateFormat(FORMAT_IN_ARTICLE);
	
	
	public static Date getDate(int year, int month, int day, int hour) {
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		calendar.set(year, month , day, hour, 0, 0);
		
		return calendar.getTime();
	}


	public static String getFormattedDate(int year, int month, int day, int hour) {
		
		return DATE_FORMAT_IN_ARTILCE.format(getDate(year, month, day, hour));
	}
	
	public static Date getNow() {
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		
		return new Date(calendar.getTimeInMillis());
	}


	public static String formatDate(Date date) {
		
		return DATE_FORMAT_IN_ARTILCE.format(date);
	}

	public static Date parseFormattedDate(String formattedDate) {
		Date date = null;
		
		try {
			date = DATE_FORMAT_IN_ARTILCE.parse(formattedDate);
			
		} catch (ParseException e) {
			// do nothing
		}
		
		return date;
	}

	public static Date nextServiceDate(Date date) {
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		calendar.setTime(date);
		
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		int hour = calendar.get(Calendar.HOUR);
		log.debug("hour: " + hour);
		
		int offset = 0;
		if (hour > AFTERNOON_SERVICE_TIME) {
			offset = HALF_A_DAY_TIME - (hour- AFTERNOON_SERVICE_TIME);
		} else if (hour <= AFTERNOON_SERVICE_TIME && hour > MORNING_SERVICE_TIME) {
			offset = AFTERNOON_SERVICE_TIME - hour;
		} else {
			offset = MORNING_SERVICE_TIME - hour;
		}
		
		calendar.add(Calendar.HOUR, offset);
		
		return calendar.getTime();
	}
	
	

	public static String[] getServiceDatesByTime(int year, int month, int day, int hour) {
		String[] dates = new String[2];
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		calendar.set(year, month , day, hour, 0, 0);
		
		dates[1] = DATE_FORMAT_IN_ARTILCE.format(calendar.getTime());
		
		calendar.add(Calendar.HOUR, OFFSET_HOURS_IN_SERVICE);
		dates[0] = DATE_FORMAT_IN_ARTILCE.format(calendar.getTime());
		
		return dates;
	}


	public static String[] getServiceFormattedDatesByDate(Date date) {
		String[] dates = new String[2];
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		calendar.setTime(date);
		
		dates[1] = DATE_FORMAT_IN_ARTILCE.format(calendar.getTime());
		
		calendar.add(Calendar.HOUR, OFFSET_HOURS_IN_SERVICE);
		dates[0] = DATE_FORMAT_IN_ARTILCE.format(calendar.getTime());
		
		return dates;
	}



	
}
