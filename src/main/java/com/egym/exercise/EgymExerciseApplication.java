package com.egym.exercise;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class EgymExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(EgymExerciseApplication.class, args);
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource());
		return localValidatorFactoryBean;
	}
	
	@Bean(name = "errorProperties")
	public Properties getErrorProperties() throws IOException {
		Properties properties = new Properties();
		try(InputStream inputStream = EgymExerciseApplication.class.getClassLoader().getResourceAsStream("error-messages.properties")) {
			properties.load(inputStream);
		}
		return properties;
	}
	
	@Bean
    public OpenAPI openAPI() {
		return new OpenAPI().components(new Components()).info(new Info().version("1.0.0").title("eGym Coding Task API"));
    }

}
