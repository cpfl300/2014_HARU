package haru.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import elixir.model.Article;
import elixir.model.Hotissue;
import elixir.service.ArticleService;
import elixir.service.HotissueService;
import elixir.utility.ElixirUtils;

@Controller
public class ArticleController {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
	
	private static final String IMAGE_URL_PATH = "/images/articles/";
	private static final String IMAGE_EXTENSION = ".jpg";
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private HotissueService hotissueService;
	
	@RequestMapping("article/{sequence}")
	public String viewArticle(@PathVariable int sequence, Model model) {		
		String articleImageUrl = getArticleImage();
		
//		Date date = ElixirUtils.getNow();
		Date now = ElixirUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		
		Article article = articleService.getBySequenceAndServiceDate(sequence, now);
		Hotissue hotissue = hotissueService.getById(article.getHotissue().getId());
		article.setHotissue(hotissue);
		String dateStr = "14112806";
		
		model.addAttribute("article", article);
		model.addAttribute("hotissue", hotissue);
		model.addAttribute("imgUrl", articleImageUrl);
		model.addAttribute("date", dateStr);

		return "article";
	}
	
	@RequestMapping("date/{date}/article/{sequence}")
	public String viewArticle(@PathVariable int sequence, @PathVariable String date, Model model) {
		
		log.debug("requestDate: " + date);
		
		String articleImageUrl = getArticleImage();
		
//		Date date = ElixirUtils.getNow();
		Date now = ElixirUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		
		Article article = articleService.getBySequenceAndServiceDate(sequence, now);
		Hotissue hotissue = hotissueService.getById(article.getHotissue().getId());
		article.setHotissue(hotissue);
		String dateStr = "14112806";
		
		model.addAttribute("article", article);
		model.addAttribute("hotissue", hotissue);
		model.addAttribute("imgUrl", articleImageUrl);
		model.addAttribute("date", dateStr);

		return "article";
	}

	protected String getArticleImage() {
		Random random = new Random();
		int pick = random.nextInt(12) + 1;
		
		return IMAGE_URL_PATH + pick + IMAGE_EXTENSION;
	}

}