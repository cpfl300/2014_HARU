package refinery.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Article {
	
	private static final Logger log = LoggerFactory.getLogger(Article.class);
	
	private int id;
	private Hotissue hotissue;
	private Journal journal;
	private Section section;
	private String title;
	private String content;
	private String date;
	private int hits;
	private int completedReadingCount;
	private double score;
	private String timestamp;
	private int sequence;


	public Article(int id, Hotissue hotissue, Journal journal, Section section, String title, String date, String content, int hits,
			int completedReadingCount, double score) {
		this.id = id;
		this.hotissue = hotissue;
		this.journal = journal;
		this.section = section;
		this.title = title;
		this.date = date;
		this.content = content;
		this.hits = hits;
		this.completedReadingCount = completedReadingCount;
		this.score = score;
	}

	public Article(Hotissue hotissue, Journal journal, Section section, String title, String date, String content, int hits,
			int completedReadingCount, double score) {
		this(0, hotissue, journal, section, title, date, content, hits, completedReadingCount, score);
		
	}
	
	public Article(Hotissue hotissue, Journal journal, Section section, String title, String date, String content, int hits,
			int completedReadingCount) {
		this(0, hotissue, journal, section, title, date, content, hits, completedReadingCount, 0);
	}
	
	public Article(Hotissue hotissue, Journal journal, Section section, String title, String date) {
		this(0, hotissue, journal, section, title, date, null, 0, 0, 0);
	}
	
	public Article(int id, int sequence, String timestamp) {
		this.id = id;
		this.sequence = sequence;
		this.timestamp = timestamp;
	}
	
	public Article() {

	}


	public Article(int id, Hotissue hotissue, Journal journal, Section section, String title, String date, String content, int hits,
			int completedReadingCount) {
		this(id, hotissue, journal, section, title, date, content, hits, completedReadingCount, 0);
	}

	public Article(int id, double score) {
		this.id = id;
		this.score = score;
	}

	public Article(int id, String timestamp, int sequence) {
		this.id = id;
		this.timestamp = timestamp;
		this.sequence = sequence;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Hotissue getHotissue() {
		return hotissue;
	}

	public void setHotissue(Hotissue hotissue) {
		this.hotissue = hotissue;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		if (date.lastIndexOf('.') != -1) {
			this.date = usableDateStr(date);
			return;
		}
		
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getCompletedReadingCount() {
		return completedReadingCount;
	}

	public void setCompletedReadingCount(int completedReadingCount) {
		this.completedReadingCount = completedReadingCount;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		if (timestamp.lastIndexOf('.') != -1) {
			this.timestamp = usableDateStr(timestamp);
			return;
		}
		
		this.timestamp = timestamp;
	}

	
	private String usableDateStr(String dateStr) {
		
		return dateStr.substring(0, dateStr.lastIndexOf("."));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((hotissue == null) ? 0 : hotissue.hashCode());
		result = prime * result + ((journal == null) ? 0 : journal.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	
	
	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	public void clacScore() {
		this.score = (double) this.completedReadingCount / this.hits;
		
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (hotissue == null) {
			if (other.hotissue != null)
				return false;
		} else if (!hotissue.equals(other.hotissue))
			return false;
		if (journal == null) {
			if (other.journal != null)
				return false;
		} else if (!journal.equals(other.journal))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", hotissue=" + hotissue + ", journal=" + journal + ", section=" + section + ", title=" + title + ", content="
				+ content + ", date=" + date + ", hits=" + hits + ", completedReadingCount=" + completedReadingCount + ", score=" + score
				+ ", timestamp=" + timestamp + ", order=" + sequence + "]";
	}

	public static List<Article> asList(List<Hotissue> hotissues) {
		List<Article> articles = new ArrayList<Article>();
		
		for (Hotissue hotissue : hotissues) {
			articles.addAll(hotissue.getArticles());
			
		}
		
		return articles;
	}

	public static List<Article> asListWithSequenceIncludeTimestamp(List<Hotissue> hotissues, String timestamp) {
		List<Article> articles = new ArrayList<Article>();
		int sequence = 1;
		
		for (Hotissue hotissue : hotissues) {
			for (Article a : hotissue.getArticles()) {
				articles.add(new Article(a.getId(), timestamp, sequence++));
			}
		}
		
		return articles;
	}
	
}
