package haru.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Article {	
	private String id;
	private String hotissue;
	private String subject;
	private String journal;
	private String section;
	private Date date;
	private String content;
	private List<String> imgs;
	
	public Article(String id, String hotissue, String subject, String journal, String section, String dateStr, String content, List<String> imgs) {
		this.id = id;
		this.hotissue = hotissue;
		this.subject = subject;
		this.journal = journal;
		this.section = section;
		this.date = parseDate(dateStr);
		this.content = content;
		this.imgs = imgs;
	}

	public Article() {
	}
	
	private Date parseDate(String dateStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			// 파싱 에러시 현재 시간 추가
			date = new Date();
		}
		
		return date;
	}


	public void setId(String id) {
		this.id = id;
	}

	public void setHotissue(String hotissue) {
		this.hotissue = hotissue;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public void setSection(String section) {
		this.section = section;
	}
	
	public void setDate(String dateStr) {
		this.date = parseDate(dateStr);
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}

	public String getId() {
		return this.id;
	}

	public String getHotissue() {
		return hotissue;
	}

	public String getSubject() {
		return subject;
	}

	public String getJournal() {
		return journal;
	}

	public String getSection() {
		return section;
	}

	public Date getDate() {
		return date;
	}
	
	public String getFormattedDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("formattedDate,  " + this.date);
		
		return dateFormat.format(this.date);
	}

	public String getContent() {
		return content;
	}

	public List<String> getImgs() {
		return imgs;
	}

	
	
}
