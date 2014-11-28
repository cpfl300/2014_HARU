package refinery.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hotissue {
	
	private int id;
	private String name;
	private String timestamp;
	private List<Article> articles;
	private double score;
	private int sequence;
	
	public Hotissue() {
		
	}
	
	public Hotissue(int id) {
		this(id, null, null);
	}
	
	public Hotissue(String name) {
		this(0, name, null);
	}
	
	public Hotissue(int id, String name) {
		this(id, name, null);
	}

	public Hotissue(int id, String name, String timestamp) {
		this.id = id;
		this.name = name;
		this.timestamp = timestamp;
	}
	
	public Hotissue(int id, String name, String timestamp, int sequence) {
		this.id = id;
		this.name = name;
		this.timestamp = timestamp;
		this.sequence = sequence;
	}

	public Hotissue(String name, String timestamp) {
		this(0, name, timestamp);
	}

	public Hotissue(int id, double score) {
		this.id = id;
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
	public void addArticle(Article article) {
		
		if (articles == null) {
			articles = new ArrayList<Article>();
		}
		
		articles.add(article);
	}


	public void calcScore() {
		
		if (articles == null) throw new EmptyArticleListException("article list is null");
		
		double score = 0;
		for (Article a : articles) {
			score += a.getScore();
		}
		
		this.score = score/articles.size();
		
	}
	
	
	private String usableDateStr(String dateStr) {
		
		return dateStr.substring(0, dateStr.lastIndexOf("."));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotissue other = (Hotissue) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return "Hotissue [id=" + id + ", name=" + name + ", timestamp=" + timestamp + ", articles=" + articles + ", score=" + score + "]";
	}

	
	public static List<Hotissue> orderByHotissue(List<Article> articles) {
		// 자칫 많아질 수 있는 key검색을 O(1)로 계산위해 map 사용
		Map<Hotissue, Hotissue> hotissueMap = new HashMap<Hotissue, Hotissue>();
		
		for (Article article : articles) {
			Hotissue hotissue = article.getHotissue();
			if (!hotissueMap.containsKey(hotissue)) {
				hotissueMap.put(hotissue, null);
			}
			
			hotissue.addArticle(article);
		}
		
		return new ArrayList<Hotissue>(hotissueMap.keySet());
	}
	
	
}
