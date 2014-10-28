package haru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages={"haru.controller"})
@EnableWebMvc
public class Config {
	
	@Bean
    public ViewResolver viewResolver() {
		
		// viewResolver를 freemarker로 지정
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setOrder(1);
        viewResolver.setCache(true);
        viewResolver.setSuffix(".ftl");
        viewResolver.setContentType("text/html; charset=UTF-8");
        viewResolver.setExposeSpringMacroHelpers(true);
        
        return viewResolver;
    }
	
	@Bean
	public FreeMarkerConfigurer freeMarkerConfig() {

	    FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
	    configurer.setTemplateLoaderPath("/WEB-INF/pages");

	    return configurer;

	}
}
