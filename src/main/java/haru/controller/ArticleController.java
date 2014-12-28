package haru.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
@RequestMapping("/articles")
public class ArticleController {
	
	private static final String IMAGE_URL_PATH = "/images/articles/";
	private static final String IMAGE_EXTENSION = ".jpg";
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private HotissueService hotissueService;

	@RequestMapping("/{date}/{sequence}")
	public String viewArticle(@PathVariable int sequence, @PathVariable String date, Model model) {

		Date now = ElixirUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		String dateStr = format.format(ElixirUtils.getNow());
		
		Article article = articleService.getBySequenceAndServiceDate(sequence, now);
		Hotissue hotissue = hotissueService.getById(article.getHotissue().getId());
		article.setHotissue(hotissue);
		String articleImageUrl = getArticleImage(article);
		
		model.addAttribute("article", article);
		model.addAttribute("hotissue", hotissue);
		model.addAttribute("imgUrl", articleImageUrl);
		model.addAttribute("date", dateStr);

		return "article";
	}
	

	protected String getArticleImage(Article article) {
		int id = article.getId();
		String imageName = "";
		
		if (id < 0) {
			id = Math.abs(id);
			imageName = imageName + "m";
		}
		
		imageName = imageName + id;
		
		return IMAGE_URL_PATH + imageName + IMAGE_EXTENSION;
	}

}