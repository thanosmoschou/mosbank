package com.thanos.mosbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thanos.mosbank.db.DbSaver;
import com.thanos.mosbank.editView.ViewEditor;
import com.thanos.mosbank.validator.Validator;

@Configuration
public class AppConfig 
{
	@Bean
	public Validator validator()
	{
		return Validator.getInstance();
	}
	
	@Bean
	public DbSaver dbSaver()
	{
		return DbSaver.getInstance();
	}
	
	@Bean
	public ViewEditor viewEditor()
	{
		return ViewEditor.getInstance();
	}
}
