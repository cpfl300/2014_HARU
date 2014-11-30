package haru.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import elixir.model.Article;
import elixir.model.Hotissue;
import elixir.service.ArticleService;
import elixir.service.HotissueService;
import elixir.utility.ElixirUtils;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	private static final String IMAGE_URL_PATH = "/images/articles/";
	private static final String IMAGE_EXTENSION = ".jpg";
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private HotissueService hotissueService;
	
	@RequestMapping("/{sequence}")
	public String main(@PathVariable int sequence, Model model) {
		
		String articleImageUrl = getArticleImage();
		
//		Date date = ElixirUtils.getNow();
		Date date = ElixirUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		
		Article article = articleService.getBySequenceAndServiceDate(sequence, date);
		Hotissue hotissue = hotissueService.getById(article.getHotissue().getId());
		article.setHotissue(hotissue);
		
		model.addAttribute("article", article);
		model.addAttribute("hotissue", hotissue);
		model.addAttribute("imgUrl", articleImageUrl);

		return "article";
	}

	private String getArticleImage() {
		Random random = new Random();
		int pick = random.nextInt(12) + 1;
		
		return IMAGE_URL_PATH + pick + IMAGE_EXTENSION;
	}

}