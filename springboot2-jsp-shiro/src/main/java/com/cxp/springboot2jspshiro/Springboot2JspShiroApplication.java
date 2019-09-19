package com.cxp.springboot2jspshiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Springboot2JspShiroApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Springboot2JspShiroApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Springboot2JspShiroApplication.class);
	}
}
