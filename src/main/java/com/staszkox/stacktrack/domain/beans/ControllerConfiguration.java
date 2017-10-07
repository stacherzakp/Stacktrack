package com.staszkox.stacktrack.domain.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class ControllerConfiguration
{
	@Bean
    public MultipartResolver multipartResolver() 
    {
		return new CommonsMultipartResolver();
    }
	
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() 
	{
	    return new MethodValidationPostProcessor();
	}
}
