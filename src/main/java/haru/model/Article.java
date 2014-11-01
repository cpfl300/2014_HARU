package haru.model;

import java.util.List;

public class Article {
	
	private long id;
	private String hotIssue;
	private String subject;
	private String journal;
	private String section;
	private String date;
	private String content;
	private List<String> imgs;
	
	public Article(long id, String hotIssue, String subject, String journal, String section, String date, String content, List<String> imgs) {
		this.id = id;
		this.hotIssue = hotIssue;
		this.subject = subject;
		this.journal = journal;
		this.section = section;
		this.date = date;
		this.content = content;
		this.imgs = imgs;
	}

	public long getId() {
		return this.id;
	}

	public String getHotIssue() {
		return hotIssue;
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

	public String getDate() {
		return date;
	}

	public String getContent() {
		return content;
	}

	public List<String> getImgs() {
		return imgs;
	}

	
	
}
