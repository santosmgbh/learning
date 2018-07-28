package br.com.almir.projetos.hm;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
@ComponentScan
public class MvcConfig implements WebMvcConfigurer  {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/").setViewName("forward:/dist/index.html");
	}
	
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		System.out.println("addCorsMapping");
//		registry.addMapping("/**").allowedOrigins("*")
//        .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept")
//        .allowedMethods("GET", "POST", "OPTIONS")
//        .allowCredentials(true).maxAge(3600);
//	    registry.addMapping("/hmchat-api/**")
//        .allowedOrigins("*")
//        .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept")
//        .allowedMethods("GET", "POST", "OPTIONS")
//        .allowCredentials(true).maxAge(3600);
//	    registry.addMapping("/login")
//        .allowedOrigins("*")
//        .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept")
//        .allowedMethods("POST", "OPTIONS")
//        .allowCredentials(true).maxAge(3600);
//	}
	

}