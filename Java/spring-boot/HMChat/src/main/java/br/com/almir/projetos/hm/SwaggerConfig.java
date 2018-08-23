package br.com.almir.projetos.hm;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.almir.projetos.hm.controller"))
				.paths(PathSelectors.any()).build().apiInfo(getInfo());
	}

	private ApiInfo getInfo() {
		return new ApiInfo("HMChat", "", "1.0.0", "", new Contact("Almir Gabriel Santos", "https://github.com/santosmgbh", "santosmgbh@gmail.com"), "personal", "", Collections.emptyList());
	}
}