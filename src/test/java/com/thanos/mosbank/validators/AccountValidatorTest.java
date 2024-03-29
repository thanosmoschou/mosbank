package com.thanos.mosbank.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thanos.mosbank.db.DbSaver;
import com.thanos.mosbank.model.Credentials;
import com.thanos.mosbank.repos.CredentialsRepository;
import com.thanos.mosbank.repos.UserRepository;
import com.thanos.mosbank.validators.AccountValidator;

@ExtendWith(MockitoExtension.class)
class AccountValidatorTest 
{
	@Mock
	private DbSaver dbSaver;
	@InjectMocks
	private AccountValidator testValidator; //Spring injects a bean
	
	@Test
	void thisUsernameExistsTest()
	{
		Credentials c1 = new Credentials("thanos", "changeme", null); //I do not specify a user object
		String thisUsernameExists = "thanos";
				
		dbSaver.storeCredentialsToRepository("thanos", "changeme", null);;
		when(dbSaver.fetchSingleCredentialsFromRepository("thanos")).thenReturn(Optional.of(c1).get());
	
		assertTrue(testValidator.usernameAlreadyExists(thisUsernameExists));
	}
	
	@Test
	void thisUsernameDoesNotExistTest()
	{		
		Credentials c1 = new Credentials("thanos", "changeme", null); //I do not specify a user object
		String thisUsernameDoesNotExist = "makis";
				
		dbSaver.storeCredentialsToRepository("thanos", "changeme", null);;
		when(dbSaver.fetchSingleCredentialsFromRepository("makis")).thenThrow(NoSuchElementException.class);
	
		//the repo will throw an exception and usernameAlreadyExists method will catch it and return false as the result
		assertFalse(testValidator.usernameAlreadyExists(thisUsernameDoesNotExist));
	}
	
	@Test
	void validPassword1()
	{
		//Here I do not need Mocks
		String validPassword = "aaMM11@@";
		
		assertTrue(testValidator.isPasswordValid(validPassword));
	}
	
	@Test
	void validPassword2()
	{
		//Here I do not need Mocks
		String validPassword2 = "1MMMaaa##$%^#@!";

		assertTrue(testValidator.isPasswordValid(validPassword2));
	}
	
	@Test
	void invalidPassword1()
	{
		//Here I do not need Mocks
		String invalidPassword = "MMMM11@@";
	
		assertFalse(testValidator.isPasswordValid(invalidPassword));
	}
	
	@Test
	void invalidPassword2()
	{
		//Here I do not need Mocks
		String invalidPassword2 = "asb";
		
		assertFalse(testValidator.isPasswordValid(invalidPassword2));
	}
	
	@Test
	void invalidPassword3()
	{
		//Here I do not need Mocks
		String invalidPassword3 = "MMMaaa##$%^#@!"; //this does not have number
		
		assertFalse(testValidator.isPasswordValid(invalidPassword3));
	}
	
	@Test
	void validEmail1()
	{
		String validEmail1 = "thanos@example.com";
		
		assertTrue(testValidator.isEmailValid(validEmail1));
	}
	
	@Test
	void validEmail2()
	{
		String validEmail2 = "than@exam.it";
		
		assertTrue(testValidator.isEmailValid(validEmail2));
	}
	
	@Test
	void validEmail3()
	{
		String validEmail3 = "t.h-an@ex.gr";
	
		assertTrue(testValidator.isEmailValid(validEmail3));
	}
	
	@Test
	void invalidEmail1()
	{
		String invalidEmail1 = "than@c.c";
	
		assertFalse(testValidator.isEmailValid(invalidEmail1));
	}
	
	@Test
	void invalidEmail2()
	{
		String invalidEmail2 = "than@.";
		
		assertFalse(testValidator.isEmailValid(invalidEmail2));
	}
	
	@Test
	void invalidEmail3()
	{
		String invalidEmail3 = "th$an@gmail.com"; //in my convention I do not want $
		
		assertFalse(testValidator.isEmailValid(invalidEmail3));
	}
	
	@Test
	void validPhone1()
	{
		String validPhone1 = "6944444444";
		
		assertTrue(testValidator.isPhoneValid(validPhone1));
	}
	
	@Test
	void validPhone2()
	{
		String validPhone2 = "2310123654";
		
		assertTrue(testValidator.isPhoneValid(validPhone2));
	}
	
	@Test
	void validPhone3()
	{
		String validPhone3 = "2105698741";
	
		assertTrue(testValidator.isPhoneValid(validPhone3));
	}
	
	@Test
	void invalidPhone1()
	{
		String invalidPhone1 = "8754236589"; //Not a greek number despite the fact that it contains 10 digits
		
		assertFalse(testValidator.isPhoneValid(invalidPhone1));
	}
	
	@Test
	void invalidPhone2()
	{
		String invalidPhone2 = "698888888"; //Seems greek but it has 9 digits instead of 10
		
		assertFalse(testValidator.isPhoneValid(invalidPhone2));
	}
	
	@Test
	void invalidPhone3()
	{
		String invalidPhone3 = "23105478"; //Looks like a greek number but it has 8 digits
		
		assertFalse(testValidator.isPhoneValid(invalidPhone3));
	}
	
	@Test
	void testIfNewPasswordIsValid1()
	{
		//valid password and the 2 fields are the same -> Result should be true
		String valid1 = "aaSS11@@";
		String valid2 = "aaSS11@@";
		
		assertTrue(testValidator.isNewPasswordValid(valid1, valid2));
	}
	
	@Test
	void testIfNewPasswordIsValid2()
	{
		//valid passwords and the 2 fields are not the same -> Result should be false
		String valid1 = "aaSS11@@";
		String valid2 = "aaSS11@@2";
		
		assertFalse(testValidator.isNewPasswordValid(valid1, valid2));
	}
	
	@Test
	void testIfNewPasswordIsValid3()
	{
		//invalid passwords and the 2 fields are the same -> Result should be false
		String invalid1 = "aaSS@@";
		String invalid2 = "aaSS@@";
		
		assertFalse(testValidator.isNewPasswordValid(invalid1, invalid2));
	}
	
	@Test
	void testIfNewPasswordIsValid4()
	{
		//invalid passwords and the 2 fields are not the same -> Result should be false
		String invalid1 = "aaSS@@";
		String invalid2 = "aaS@@";
		
		assertFalse(testValidator.isNewPasswordValid(invalid1, invalid2));
	}
}
