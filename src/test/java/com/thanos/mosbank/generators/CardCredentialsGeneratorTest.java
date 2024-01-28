package com.thanos.mosbank.generators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CardCredentialsGeneratorTest 
{
	@Test
	void thisCardIsValid()
	{
		String validNumber = "4556383361319387";
		
		assertTrue(CardCredentialsGenerator.isCardNumberValid(validNumber));
	}
	
	@Test
	void thisCardIsInvalid()
	{
		String invalidNumber = "4963604178888081";
		
		assertFalse(CardCredentialsGenerator.isCardNumberValid(invalidNumber));
	}
}
