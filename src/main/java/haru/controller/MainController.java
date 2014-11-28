package haru.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import refinery.model.Hotissue;
import refinery.service.HotissueService;
import refinery.utility.RefineryUtils;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private HotissueService hotissueService;
	
	@RequestMapping("/")
	public String main(Model model) {
		
		String imageUrl = "/images/test/main_issue.jpg";
		
//		Date date = RefineryUtils.getNow();
		Date date = RefineryUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		
		List<Hotissue> hotissues = hotissueService.getByServiceDate(date);
		
		model.addAttribute("hotissues", hotissues);
		model.addAttribute("imageUrl", imageUrl);
		
		return "main";
	}

}