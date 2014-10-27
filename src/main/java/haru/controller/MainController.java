package haru.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String showIndex(Model model) {
		
//		model.addAttribute("time", new Date());
//		model.addAttribute("message", "hello 에프티엘");
		
		return "issue";
	}

}