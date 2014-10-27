package haru.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AritlceController {
	
	@RequestMapping("/article")
	public String articleView() {
		
		return "article";
	}
}
