/*
 * Author: Thanos Moschou
 * Description: This is a banking system using Spring Boot.
 * 
 * 
 * Spring Data Jpa uses underscore notation if an attribute
 * is bigger than 1 word. So in my classes' attributes I use this notation.
 * Keep in mind that in my method parameters and local variables I use lowerCamelcase.
 * 
 * 
 * Last Modification Date: 8/3/2024
 */

package com.thanos.mosbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thanos.mosbank.db.DbSaver;
import com.thanos.mosbank.editView.ViewEditor;
import com.thanos.mosbank.validators.AccountValidator;
import com.thanos.mosbank.validators.AtmValidator;
import com.thanos.mosbank.validators.TransactionValidator;

@Configuration
public class AppConfig 
{
	@Bean
	public AccountValidator accountValidator()
	{
		return AccountValidator.getInstance();
	}
	
	@Bean
	public TransactionValidator transactionValidator()
	{
		return TransactionValidator.getInstance();
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
	
	@Bean
	public AtmValidator atmValidator()
	{
		return AtmValidator.getInstance();
	}
}
