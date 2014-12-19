package haru.controller;

import haru.library.imageUtil.Image;
import haru.library.imageUtil.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import elixir.model.Hotissue;
import elixir.service.HotissueService;
import elixir.utility.ElixirUtils;

@Controller
public class MainController {
	
	private static final Logger log = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private HotissueService hotissueService;
	
	@RequestMapping("/")
	public String viewMain(Model model) {
		
//		String imageUrl = "/images/test/main_issue.jpg";
		String imageUrl = "http://1.bp.blogspot.com/-_eOqJw1i78U/T-nPui2ankI/AAAAAAAAAkk/vzNLfVgUWw4/s1600/dust-dirt-and-leaves-inkbluesky.png";

		//		이미지 라이브러리 적용 파트
		Image cropped = null;
		File f = new File("cropped.jpg");
		try {
			Image img = ImageLoader.fromUrl(imageUrl);
			img.getResizedToWidth(400).soften(0.3f).writeToJPG(f, 0.95f);
			System.out.println(f.getAbsolutePath());
//            cropped.writeToFile(new File("./static/images/cropped.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		Date date = ElixirUtils.getNow();
		Date date = ElixirUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		String dateStr = "141206";
		
		List<Hotissue> hotissues = hotissueService.getByServiceDate(date);
		
		model.addAttribute("hotissues", hotissues);
		model.addAttribute("imageUrl", imageUrl);
//		model.addAttribute("cropped", f.getAbsolutePath());
		model.addAttribute("date", dateStr);
		
		return "main";
	}
	
	@RequestMapping("date/{date}")
	public String viewMain(@PathVariable String date, Model model) {
		log.debug("requestDate: " + date);
		
		String imageUrl = "/images/test/main_issue.jpg";
		
//		Date date = ElixirUtils.getNow();
		Date now = ElixirUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		String dateStr = "14112806";
		
		List<Hotissue> hotissues = hotissueService.getByServiceDate(now);
		
		model.addAttribute("hotissues", hotissues);
		model.addAttribute("imageUrl", imageUrl);
		model.addAttribute("date", dateStr);
		
		return "main";
	}

}