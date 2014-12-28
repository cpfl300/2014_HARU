package haru.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import elixir.model.Hotissue;
import elixir.service.HotissueService;
import elixir.utility.ElixirUtils;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private HotissueService hotissueService;
	
	@RequestMapping("/")
	public String viewMain(Model model) {
		
//		String imageUrl = "/images/test/main_issue.jpg";
		String imageUrl = "/images/mainImg.jpg";
		String blurImgUrl = "/images/blurImg.jpg";
		
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		Date date = ElixirUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		String dateStr = format.format(ElixirUtils.getNow());
		
		List<Hotissue> hotissues = hotissueService.getByServiceDate(date);
		
		model.addAttribute("hotissues", hotissues);
		model.addAttribute("imageUrl", imageUrl);
		model.addAttribute("blurImgUrl", blurImgUrl);
		model.addAttribute("date", dateStr);
		
		return "main";
	}

}