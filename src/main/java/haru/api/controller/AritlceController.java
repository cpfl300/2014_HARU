package haru.api.controller;

import haru.dao.ArticleDao;
import haru.model.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1")
public class AritlceController {
	
	@Autowired
	private ArticleDao articleDao;
	
	@RequestMapping("/article/{issue_num}/{article_num}")
	public @ResponseBody Article getArticle(@PathVariable("issue_num") String issue_num, @PathVariable("article_num") String article_num) {
		System.out.println("INN");
//		Article article = articleDao.get("article1");
//		model.addAttribute("article", article);
		
		Article article = new Article();
		
		return article;
	}
}
