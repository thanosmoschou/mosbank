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
 * Last Modification Date: 30/1/2024
 */

package com.thanos.mosbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thanos.mosbank.generators.CardCredentialsGenerator;
import com.thanos.mosbank.validators.AccountValidator;

@SpringBootApplication
public class MosbankApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(MosbankApplication.class, args);
	}
}
