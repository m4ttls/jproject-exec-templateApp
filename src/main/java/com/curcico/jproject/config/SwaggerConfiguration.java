package com.curcico.jproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.curcico.jproject.core.wrapper.GridWrapper;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.schema.AlternateTypeRules.*;

import javax.lang.model.type.WildcardType;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	  @Autowired
	  private TypeResolver typeResolver;

	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
          .apiInfo(getApiInfo())
          .forCodeGeneration(true)
          .genericModelSubstitutes(ResponseEntity.class)
          .alternateTypeRules(
        		  newRule(typeResolver.resolve(ResponseEntity.class), typeResolver.resolve(WildcardType.class)),
        		  newRule(typeResolver.resolve(ResponseEntity.class, typeResolver.resolve(GridWrapper.class, WildcardType.class)),
        				  typeResolver.resolve(WildcardType.class))
		          )
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
    			.title("Jproject Example API")
   				.description("This REST API example allows the user to manage domain objects. \n\n"
   						+ "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur erat velit, cursus vitae justo vitae, vulputate sollicitudin nisi. In egestas sit amet justo sed bibendum. Quisque ante dolor, semper vitae urna ut, dignissim malesuada ex. Sed facilisis pellentesque eros, a fringilla enim aliquam nec. Cras sit amet ex feugiat, blandit purus at, aliquet eros. Pellentesque quis sodales tortor. Duis ut ex nisi. Praesent quis metus turpis. Mauris cursus tempor lectus non hendrerit. Maecenas tortor enim, dictum accumsan laoreet et, pretium a nunc.\n")
   				.contact(contact)
    			.version("v1")
    			.build();
    }
}
