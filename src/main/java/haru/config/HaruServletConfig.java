package haru.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages={"haru.controller", "haru.api.controller"})
@EnableWebMvc
public class HaruServletConfig extends WebMvcConfigurerAdapter{
	
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

//	@Bean
//	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
//		MappingJackson2HttpMessageConverter m = new MappingJackson2HttpMessageConverter();
//		List<MediaType> types = new ArrayList<MediaType>();
//		MediaType type = new MediaType("application/json");
//		types.add(type);
//		m.setSupportedMediaTypes(types);
//		return m;
//	}
//	
//	@Bean
//	public AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter() {
//		
//		AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
//		
//		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
//		HttpMessageConverter<?> ary[] = new HttpMessageConverter[converters.size()];
//		
//		adapter.setMessageConverters(converters.toArray(ary));
//		
//		return adapter;
//		
//	}

	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/static/images/");
	    registry.addResourceHandler("/scripts/**").addResourceLocations("/WEB-INF/scripts/");
	    registry.addResourceHandler("/stylesheets/**").addResourceLocations("/WEB-INF/stylesheets/");
	}
}
