package com.thanos.mosbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thanos.mosbank.validator.Validator;

@Configuration
public class AppConfig 
{
	@Bean
	public Validator validator()
	{
		return Validator.getInstance();
	}
}
