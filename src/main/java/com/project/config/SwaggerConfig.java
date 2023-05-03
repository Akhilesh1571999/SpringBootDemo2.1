package com.project.config;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Employee's api's")
				.apiInfo(ApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.project.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo ApiInfo() {
		return new ApiInfoBuilder().title("Employee api's details")
				.description("spring rest api's docs")
				.version("1.0")
				.build();
	}
	
}
