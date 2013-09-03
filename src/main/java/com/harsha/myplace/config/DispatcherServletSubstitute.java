package com.harsha.myplace.config;

import javax.validation.MessageInterpolator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.ResourceBundleLocator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.harsha.myplace.people.Person;
import com.harsha.myplace.people.PersonResourceAssembler;

@Configuration
@ComponentScan("com.harsha.myplace")
public class DispatcherServletSubstitute extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/resources/css/**").addResourceLocations("/resources/css/");
		registry.addResourceHandler("/resources/js/**").addResourceLocations("/resources/js/");
	}

	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
//	@Bean(name="assembler")
//	public PersonResourceAssembler getAssembler(){
//		return new PersonResourceAssembler();
//	}

	@Override
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
		localValidatorFactoryBean
				.setMessageInterpolator(getMessageInterpolator());
		return localValidatorFactoryBean;
	}

	private MessageInterpolator getMessageInterpolator() {
		ResourceBundleMessageInterpolator messageInterpolator = new org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator(
				getResourceBundleLocator());
		return messageInterpolator;
	}

	private ResourceBundleLocator getResourceBundleLocator() {
		MessageSourceResourceBundleLocator messageSourceResourceBundleLocator = new org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator(
				getMessageSource());
		return messageSourceResourceBundleLocator;
	}

	private MessageSource getMessageSource() {
		ResourceBundleMessageSource resourceBundleMessageSource = new org.springframework.context.support.ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("messages");
		return resourceBundleMessageSource;
	}
}
