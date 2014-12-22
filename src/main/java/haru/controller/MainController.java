package haru.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import elixir.model.Hotissue;
import elixir.service.HotissueService;
import elixir.utility.ElixirUtils;

@Controller
public class MainController {
	
	private static final Logger log = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private HotissueService hotissueService;
	
	@RequestMapping("/")
	public String viewMain(Model model) throws IOException {
		
//		String imageUrl = "/images/test/main_issue.jpg";
		String imageUrl = "http://cfile2.uf.tistory.com/image/02327C40508EBEC10287B7";

		
//		imageIO Test
		BufferedImage resizeImage = null;
		String src = null;
		try {
			URL url = new URL(imageUrl);
			BufferedImage originImage = ImageIO.read(url);
			int type = originImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originImage.getType();
			resizeImage = new BufferedImage(360, 640, type);
			
			Graphics2D g = resizeImage.createGraphics();
			g.drawImage(originImage, 0, 0, 360, 640, null);
			g.dispose();
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(resizeImage, "JPEG", out);
			byte[] bytes = out.toByteArray();
			
			String base64bytes = Base64.encode(bytes);
			src = "data:image/jpeg;base64," +base64bytes;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
//		Date date = ElixirUtils.getNow();
		Date date = ElixirUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		String dateStr = "141206";
		
		List<Hotissue> hotissues = hotissueService.getByServiceDate(date);
		
		model.addAttribute("hotissues", hotissues);
		model.addAttribute("imageUrl", imageUrl);
		model.addAttribute("imageSrc", src);
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