package haru.controller;

import haru.model.Article;
import haru.service.ArticleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/")
	public String main(Model model) {
		
		String imageUrl = "/images/test/main_issue.jpg";
		
		List<Article> haruHotissues = articleService.getHaruHotissue();
		model.addAttribute("haruHotissues", haruHotissues);
		model.addAttribute("imageUrl", imageUrl);
		
		return "main";
	}

}