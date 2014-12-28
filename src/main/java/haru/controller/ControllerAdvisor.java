package haru.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerAdvisor {
	
	private static final Logger log = LoggerFactory.getLogger(ControllerAdvisor.class);

	@ExceptionHandler(NoHandlerFoundException.class)
	public String handle(Exception ex) {
		
		log.error(ex.getMessage());

		return "redirect:/";
	}
}