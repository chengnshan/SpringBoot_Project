package com.cxp.springboot2jspshiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.cxp.springboot2jspshiro.mapper")
@ImportResource(value = {"classpath:spring/spring-shiro.xml"})
public class Springboot2JspShiroApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Springboot2JspShiroApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Springboot2JspShiroApplication.class);
	}
}
