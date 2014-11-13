package haru.controller;

import haru.model.Article;
import haru.service.ArticleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	@RequestMapping("")
	public String main(Model model) {
		
		return "article";
	}

}