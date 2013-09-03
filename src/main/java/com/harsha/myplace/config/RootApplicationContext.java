package com.harsha.myplace.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ImportResource(value = "classpath:/META-INF/spring/mongo-persistence.xml")
@EnableWebMvc
public class RootApplicationContext extends WebMvcConfigurerAdapter {

}
   