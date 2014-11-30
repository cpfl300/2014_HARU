package haru.controller;

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
	public String main(Model model) {
		
		String imageUrl = "/images/test/main_issue.jpg";
		
//		Date date = ElixirUtils.getNow();
		Date date = ElixirUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		
		List<Hotissue> hotissues = hotissueService.getByServiceDate(date);
		
		model.addAttribute("hotissues", hotissues);
		model.addAttribute("imageUrl", imageUrl);
		
		return "main";
	}

}