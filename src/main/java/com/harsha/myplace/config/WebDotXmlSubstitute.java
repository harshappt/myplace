package com.harsha.myplace.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class WebDotXmlSubstitute extends AbstractDispatcherServletInitializer {

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext awac = new AnnotationConfigWebApplicationContext();
		awac.setConfigLocation("com.harsha.myplace.config.RootApplicationContext");
		return awac;
	}

	
	@Override
	protected WebApplicationContext createServletApplicationContext(){
		AnnotationConfigWebApplicationContext awac = new AnnotationConfigWebApplicationContext();
		awac.setConfigLocation("com.harsha.myplace.config.DispatcherServletSubstitute");
		return awac;
	}


	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

}
