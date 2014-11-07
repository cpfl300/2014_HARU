package haru.api.controller;

import haru.dao.ArticleDao;
import haru.model.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class AritlceApiController {
	
	@Autowired
	private ArticleDao articleDao;
	
	@RequestMapping("/article")
	public String getArticle(Model model) {
		
		Article article = articleDao.get("article1");
		model.addAttribute("article", article);
		
		return "article";
	}
}
