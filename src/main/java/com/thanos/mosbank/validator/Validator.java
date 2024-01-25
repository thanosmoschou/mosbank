
package com.thanos.mosbank.validator;

import java.util.NoSuchElementException;

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
			return -1;
		}
	}
	
}
