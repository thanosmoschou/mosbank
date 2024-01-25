/*
 * Author: Thanos Moschou
 * Description: This is a banking system using Spring Boot
 * 
 * Last Modification Date: 3/1/2024
 */

package com.thanos.mosbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thanos.mosbank.validator.Validator;

@SpringBootApplication
public class MosbankApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(MosbankApplication.class, args);
	}

}
