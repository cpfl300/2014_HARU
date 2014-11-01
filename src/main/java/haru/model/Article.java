package haru.model;

import java.util.List;

public class Article {
	
	private String hotIssue;
	private String subject;
	private String journal;
	private String section;
	private String date;
	private String content;
	private List<String> imgs;
	
	public Article(String hotIssue, String subject, String journal, String section, String date, String content, List<String> imgs) {
		this.hotIssue = hotIssue;
		this.subject = subject;
		this.journal = journal;
		this.section = section;
		this.date = date;
		this.content = content;
		this.imgs = imgs;
	}

}
