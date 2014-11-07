package haru.controller;

import haru.model.Issue;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
	
	@RequestMapping("/")
	public String main(Model model) {
		
		Issue issue = new Issue("test issue title");
		model.addAttribute("issue", issue);
		
		return "main";
	}

}