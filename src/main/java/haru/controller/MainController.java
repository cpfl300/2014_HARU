package haru.controller;

import haru.dao.ArticleDao;
import haru.model.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@Autowired
	private ArticleDao articleDao;
	
	@RequestMapping("/")
	public String main(Model model) {
		
		Article article = articleDao.get("article1");
		model.addAttribute("article", article);
		
		return "main";
	}

}