package com.texoit.api.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConf {
	
	private static final String BASE_PACKAGE = "com.texoit.api";
	private static final String TITLE = "Texo It - API TEST";
	private static final String DESCRIPTION = "Texo It - REST API";
	private static final String VERSION = "1.0.0";
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
					.build()
					.apiInfo(metaData())
					.useDefaultResponseMessages(false)
			        .globalResponseMessage(RequestMethod.GET, responseMessage())
			        .globalResponseMessage(RequestMethod.POST, responseMessage())
			        .globalResponseMessage(RequestMethod.PUT, responseMessage())
			        .globalResponseMessage(RequestMethod.DELETE, responseMessage());
	}
	
	private ApiInfo metaData() {
		return new ApiInfoBuilder().title(TITLE).description(DESCRIPTION).version(VERSION).build();
	}
	
	private List<ResponseMessage> responseMessage()
	{
	    return new ArrayList<ResponseMessage>() {{
	    	add(new ResponseMessageBuilder()
	            .code(200)
	            .message("Success !")
	            .build());
	    	add(new ResponseMessageBuilder()
	            .code(204)
	            .message("No Content!")
	            .build());
	        add(new ResponseMessageBuilder()
	            .code(500)
	            .message("500 Internal Server Error!")
	            .responseModel(new ModelRef("Error"))
	            .build());
	        add(new ResponseMessageBuilder()
	            .code(400)
	            .message("Bad Request!")
	            .build());
	    }};
	}
}
