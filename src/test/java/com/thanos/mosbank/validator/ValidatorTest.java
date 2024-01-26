package com.thanos.mosbank.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thanos.mosbank.model.Credentials;
import com.thanos.mosbank.repos.CredentialsRepository;

@ExtendWith(MockitoExtension.class)
class ValidatorTest 
{
	@Mock
	private CredentialsRepository credRepo;
	@InjectMocks
	private Validator testValidator; //Spring injects a bean
	
	@Test
	void checkUsernameValidity()
	{
		Credentials c1 = new Credentials("thanos", "changeme", null); //I do not specify a user object
		String thisUsernameExists = "thanos";
		String thisUsernameDoesNotExist = "makis";
				
		credRepo.save(c1);
		when(credRepo.findById("thanos")).thenReturn(Optional.of(c1));
	
		assertTrue(testValidator.usernameAlreadyExists(thisUsernameExists));
		assertFalse(testValidator.usernameAlreadyExists(thisUsernameDoesNotExist));
	}
	
	@Test
	void checkPasswordValidity()
	{
		//Here I do not need Mocks
		String validPassword = "aaMM11@@";
		String validPassword2 = "1MMMaaa##$%^#@!";
		String invalidPassword = "MMMM11@@";
		String invalidPassword2 = "asb";
		String invalidPassword3 = "MMMaaa##$%^#@!"; //this does not have number
		
		assertTrue(testValidator.isPasswordValid(validPassword));
		assertTrue(testValidator.isPasswordValid(validPassword2));
		assertFalse(testValidator.isPasswordValid(invalidPassword));
		assertFalse(testValidator.isPasswordValid(invalidPassword2));
		assertFalse(testValidator.isPasswordValid(invalidPassword3));
	}
	
	@Test
	void checkEmailValidity()
	{
		String validEmail1 = "thanos@example.com";
		String validEmail2 = "than@exam.it";
		String validEmail3 = "t.h-an@ex.gr";
		String invalidEmail1 = "than@c.c";
		String invalidEmail2 = "than@.";
		String invalidEmail3 = "th$an@gmail.com"; //in my convention I do not want $
		
		assertTrue(testValidator.isEmailValid(validEmail1));
		assertTrue(testValidator.isEmailValid(validEmail2));
		assertTrue(testValidator.isEmailValid(validEmail3));
		assertFalse(testValidator.isEmailValid(invalidEmail1));
		assertFalse(testValidator.isEmailValid(invalidEmail2));
		assertFalse(testValidator.isEmailValid(invalidEmail3));
	}
	
	@Test
	void checkPhoneValidity()
	{
		String validPhone1 = "6944444444";
		String validPhone2 = "2310123654";
		String validPhone3 = "2105698741";
		String invalidPhone1 = "8754236589"; //Not a greek number despite the fact that it contains 10 digits
		String invalidPhone2 = "698888888"; //Seems greek but it has 9 digits instead of 10
		String invalidPhone3 = "23105478"; //Looks like a greek number but it has 8 digits
		
		assertTrue(testValidator.isPhoneValid(validPhone1));
		assertTrue(testValidator.isPhoneValid(validPhone2));
		assertTrue(testValidator.isPhoneValid(validPhone3));
		assertFalse(testValidator.isPhoneValid(invalidPhone1));
		assertFalse(testValidator.isPhoneValid(invalidPhone2));
		assertFalse(testValidator.isPhoneValid(invalidPhone3));
	}

}
