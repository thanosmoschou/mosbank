
package com.thanos.mosbank.validator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.thanos.mosbank.StatusCode;
import com.thanos.mosbank.model.Credentials;
import com.thanos.mosbank.repos.CredentialsRepository;

//Singleton design pattern
public class Validator 
{
	@Autowired
	private CredentialsRepository credentialsRepo;
	private static Validator instance = null;
	
	private Validator()
	{
	}
	
	public static Validator getInstance()
	{
		if(instance == null)
			instance = new Validator();
		return instance;
	}
	
	
	/* A credentials object is an object that represents the credentials
	 * of a single user. Check schema.sql to see the table.
	 *
	 * RETURN CODES:
	 * -1: USER DOES NOT EXIST
	 * 0: WRONG PASSWORD
	 * Any other number will be the user's id
	 */
	public int login(String username, String password)
	{
		//Return the row that has the value of username.
		//Represent the row as a credentials object.
		
		Credentials usersCredentials = null;
		try
		{
			usersCredentials = credentialsRepo.findById(username).get();
			
			if(usersCredentials == null)
				return StatusCode.WRONG_USERNAME;
			
			if(usersCredentials.hasPassword(password))
				return usersCredentials.fetchUserId();
			
			return StatusCode.WRONG_PASSWORD;
		}
		catch(NoSuchElementException e)
		{
			return StatusCode.WRONG_USERNAME;
		}
	}
	
	
	/*
	 * This validates that all fields are valid.
	 * The html checks if the fields are empty or not. 
	 * It only makes a post request if all fields are filled. 
	 * 
	 * I check for username in order to make sure that each username 
	 * is unique in our system. 
	 * 
	 * I check the password in order to meet some criteria. 
	 * 
	 * I check for email to see if it is valid. 
	 * A valid email for my system is something that has only one @ and it
	 * ends with a domain such as .com etc.
	 * 
	 * Finally I check for telephone in order to see if it is 10 digits long
	 * and assure that is a greek number.
	 * 
	 * If everything goes well it stores the user in the db. It also calls
	 * methods for creating his iban and his card number.
	 * 
	 * ERROR CODES:
	 * -5: Username exists
	 * -4: Password does not meet my criteria
	 * -3: Email is invalid
	 * -2: Phone is not valid
	 * 
	 * Otherwise it returns an id which is the id of the user just created.
	 */
	public int signUp(String firstname, String lastname, String username, String password, String email, String telephone)
	{
		
		if(usernameAlreadyExists(username))
			return StatusCode.USERNAME_ALREADY_EXISTS;
		
		if(!isPasswordValid(password))
			return StatusCode.INVALID_PASSWORD;
		
		if(!isEmailValid(email))
			return StatusCode.INVALID_EMAIL;
		
		if(!isPhoneValid(telephone))
			return StatusCode.INVALID_PHONE_NUMBER;
			
		//create iban and card and store the user to the db.
		//Create a user first, store the user, create a credentials object and store it.
		//Create a card and store it. Finally create a bank account
		return 0;
	}
	
	public boolean usernameAlreadyExists(String username)
	{
		Credentials cred = null;
		
		try
		{
			cred = credentialsRepo.findById(username).get();
		}
		catch(NoSuchElementException e)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean isPasswordValid(String password)
	{
		/*
		 * (?=.*[0-9]) this means that I want any character (specified by the .) multiple times (specified by
		 * the *) and these characters to belong in my class. This means that I want at least one digit
		 * 
		 * .{8,} means that I want any character 8 or more times. 
		 * 
		 * (?!.* ) means that spaces are not allowed
		 */		
		return Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?!.* ).{8,}$", password);
	}
	
	public boolean isEmailValid(String email)
	{
		//I want emails that have at least one character from a-z or A-Z or 0-9
		//then have @ symbol, at least one character from the same ranges as before,
		//a single . character and after the . symbol 2 or 3 letters.
		
		return Pattern.matches("[a-zA-Z0-9.-]+@[a-zA-Z0-9]+[.]{1}[a-z]{2,3}", email);
	}
	
	public boolean isPhoneValid(String phone)
	{
		//phone is a 10 digit Greek number
		//69... or 2...
		//for example 6944444444 or 2101111111 or 2310222222
		// \d means any digit but I specify the \ with \\ because \ is an escape character
		// | means or
		
		return phone.matches("69\\d{8}|2\\d{9}");
	}
	
	
}
