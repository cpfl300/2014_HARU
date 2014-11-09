package haru.service;

import haru.dao.ArticleDao;
import haru.model.Article;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleDao dao;

	public List<Article> getHaruHotissue() {
		
		return dao.getLatestHotissues(getRandNum());
	}
	
	
	private int getRandNum() {
		Random random = new Random();
		
		return (random.nextInt(30) + 1);
		
	}

}
