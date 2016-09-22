package com.curcico.jproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import com.curcico.jproject.core.wrapper.GridWrapper;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2; 

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
          .apiInfo(getApiInfo())
          .genericModelSubstitutes(ResponseEntity.class)
          .genericModelSubstitutes(GridWrapper.class)
          .select()                                  
          	.apis(RequestHandlerSelectors.any())              
          	.paths(PathSelectors.any())
          	.build();                                           
    }

    public ApiInfo getApiInfo() {
    	Contact contact = new Contact(	"Alejandro Daniel Curci", 
    									"https://github.com/acurci/", 
    									"acurci@gmail.com");
    	
    	ApiInfoBuilder apiBuilder = new ApiInfoBuilder();
    	return apiBuilder
    			.title("Jproject Template App & API REST")
   				.description("This REST API example allows the user to manage domain objects. \n")   				.contact(contact)
    			.version("v1")
    			.build();
    }
}
