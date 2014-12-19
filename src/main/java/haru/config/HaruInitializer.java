package haru.config;

import haru.filter.SiteMeshFilter;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class HaruInitializer implements WebApplicationInitializer {
	
	private static final String ON = "on";
	private static final String OFF = "off";
	
	private static final String ALL = "/*";
	private static final String ROOT = "/";
	private static final String HARU = "haru";

	@Override
	public void onStartup(ServletContext container) {
		// spring application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(HaruConfig.class);

		// root application context 라이프사이클 관리
		container.addListener(new ContextLoaderListener(rootContext));

		// spring application context의 dispatcher servlet
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(HaruServletConfig.class);

		// dispatcher servlet 등록
		ServletRegistration.Dynamic dispatcher = container.addServlet(HARU, new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping(ROOT);

		// filter
		container
			.addFilter("sitemeshFilter", SiteMeshFilter.class)
			.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, ALL);
		
		FilterRegistration charEncodingfilterReg = container.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
		charEncodingfilterReg.setInitParameter("encoding", "UTF-8");
		charEncodingfilterReg.setInitParameter("forceEncoding", "true");
		charEncodingfilterReg.addMappingForUrlPatterns(null, false, ALL);
		
		
		// context param
		container.setAttribute("debugger", OFF);
	}

}