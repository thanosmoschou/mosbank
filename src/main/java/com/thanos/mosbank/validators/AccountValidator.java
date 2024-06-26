/*
 * Author: Thanos Moschou
 * Description: This is a banking system using Spring Boot.
 *
 * Last Modification Date: 8/3/2024
 */

package com.thanos.mosbank.validators;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.thanos.mosbank.db.DbSaver;
import com.thanos.mosbank.generators.CardCredentialsGenerator;
import com.thanos.mosbank.generators.IbanGenerator;
import com.thanos.mosbank.model.Credentials;
import com.thanos.mosbank.model.User;
import com.thanos.mosbank.repos.CredentialsRepository;
import com.thanos.mosbank.repos.UserRepository;
import com.thanos.mosbank.statusCodes.StatusCode;

//Singleton design pattern
public class AccountValidator 
{
	private static AccountValidator instance = null;
	@Autowired
	private DbSaver dbSaver;
	
	private AccountValidator()
	{
	}
	
	public static AccountValidator getInstance()
	{
		if(instance == null)
			instance = new AccountValidator();
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
			usersCredentials = dbSaver.fetchSingleCredentialsFromRepository(username);
			
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
		
		dbSaver.storeUserToRepository(firstname, lastname, email, telephone);
		
		//Now I want to get the user created but I want him to have his id being set,
		//so I need to retrieve him from the db.
		//The user is last in the table because it has the bigger id number
		//until a new user registers.
		//id is the primary key. This is why I take the last record of the table
		//simple as that.
		//If username is primary key in some other table, I need to traverse
		//the whole table because it is ordered in lexical order.
		List<User> allUsers = dbSaver.fetchAllUsersFromRepository();
		User createdUser = allUsers.get(allUsers.size() - 1);
	
		String generatedCardNumber = CardCredentialsGenerator.generateCardNumber();
		String cardPin = CardCredentialsGenerator.generateCardPin();
		String expDate = CardCredentialsGenerator.generateExpireDate();
		String cvv = CardCredentialsGenerator.generateCVV();
		String ibanNumber = IbanGenerator.generateIban();
		
		dbSaver.storeCardToRepository(generatedCardNumber, cardPin, expDate, cvv, createdUser);
		dbSaver.storeCredentialsToRepository(username, password, createdUser);
		dbSaver.storeIbanToRepository(ibanNumber, createdUser);
		dbSaver.storeBankAccountToRepositoryForUser(createdUser);
		
		return createdUser.fetchUserId();
	}
	
	/*
	 * The following edit account method is pretty similar to sign up method.
	 * It returns the same error codes except invalid password. It returns
	 * a new error code for the password change.
	 * 
	 * I want the 2 inputs of the new password to contain the same value
	 * and meet the password criteria as well.
	 * 
	 * NEW ERROR CODES:
	 * -500: New password does not meet my criteria
	 * 
	 * Otherwise it returns an id which is the id of the user just updated.
	 */
	public int editAccount(int userId, String firstname, String lastname, String username, String newPassword, String newPasswordRetype, String email, String telephone) 
	{
		User user = dbSaver.fetchUserFromRepository(userId);
		Credentials userCred = dbSaver.fetchSingleCredentialsFromRepositoryPassUser(user);
		String oldPassword = userCred.getPassword();
		String oldUsername = userCred.getUsername();
		
		//This checks if the username exists and is reserved by another person
		//I need to check if it is reserved by another person
		//because when I do not want to change the username, it would
		//check if the username already exists and if yes then it would 
		//encounter an error. But the username is reserved by my user and
		//I do not want to show an error.
		if(usernameAlreadyExists(username) && !userCred.hasUsername(username))
			return StatusCode.USERNAME_ALREADY_EXISTS;
		
		if(!newPassword.isEmpty() && !newPasswordRetype.isEmpty() && !isNewPasswordValid(newPassword, newPasswordRetype))
			return StatusCode.NEW_PASSWORD_IS_INVALID;
		
		if(!isEmailValid(email))
			return StatusCode.INVALID_EMAIL;
		
		if(!isPhoneValid(telephone))
			return StatusCode.INVALID_PHONE_NUMBER;
		
		//Hibernate does not allow modifying the primary key of an entity
		//so I will delete it and create a new one
		//Maybe user did not change the username nor password. I always delete 
		//the credentials entity from my db and create a new one.
		dbSaver.deleteCredentialsObjectFromRepository(userCred);
		
		Credentials updatedCred = Credentials.builder()
											 .username(username)
											 .password(oldPassword)
											 .user(user)
											 .build();
		
		//It is possible that user did not enter a new password so I need to check it
		if(!newPassword.isEmpty())
			updatedCred.setPassword(newPassword);
		
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setEmail(email);
		user.setTelephone(telephone);
		
		dbSaver.storeUserToRepository(user);
		dbSaver.storeCredentialsToRepository(updatedCred);
		
		return user.fetchUserId();
	}
	
	public boolean usernameAlreadyExists(String username)
	{
		Credentials cred = null;
		
		try
		{
			cred = dbSaver.fetchSingleCredentialsFromRepository(username);
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
	
	public boolean isNewPasswordValid(String newPassword, String newPasswordRetype) 
	{
		return isPasswordValid(newPasswordRetype) && isPasswordValid(newPasswordRetype) && (newPassword.equals(newPasswordRetype));
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
