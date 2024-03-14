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

package com.thanos.mosbank.generators;

import java.util.Random;

public class IbanGenerator 
{
	private static final int IBAN_LENGTH = 20;
	
	//For simplicity let's assume that each IBAN is 20 digits long
	public static String generateIban()
	{
		String iban = "GR";
		Random rand = new Random();
		
		for(int i = 0; i < IBAN_LENGTH - 2; i++)
			iban += rand.nextInt(0, 10);
		
		return iban;
	}
}
