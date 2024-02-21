package com.thanos.mosbank.validators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;

import com.thanos.mosbank.db.DbSaver;
import com.thanos.mosbank.model.BankAccount;
import com.thanos.mosbank.model.Card;
import com.thanos.mosbank.model.Iban;
import com.thanos.mosbank.model.Transaction;
import com.thanos.mosbank.model.User;
import com.thanos.mosbank.statusCodes.StatusCode;

public class AtmValidator 
{
	private static AtmValidator instance = null;
	
	@Autowired
	private DbSaver dbSaver;
	
	private AtmValidator()
	{
		
	}
	
	
	public static AtmValidator getInstance()
	{
		if(instance == null)
			instance = new AtmValidator();
		return instance;
	}
	
	
	public Card login(String number, String pin)
	{
		Card card = dbSaver.fetchCardFromRepository(number);
		
		if(card == null)
			return null;
		
		if(card.hasPin(pin))
			return card;
		
		return null;
	}
	
	//I did not create test for this method because I have created tests for all the methods that this method calls
	public int atmAction(String action, int amount, int userId)
	{
		User user = dbSaver.fetchUserFromRepository(userId);
		Iban iban = dbSaver.fetchIbanFromRepositoryViaUser(user); //it is not required here but I search user's transactions for my main page via iban
		BankAccount account = dbSaver.fetchBankAccountFromRepositoryViaUser(user);
		
		if(amount <= 0)
			return StatusCode.INVALID_AMOUNT_FOR_TRANSACTION;
		
		if(action.equals("Withdraw"))
		{
			if(account.hasEnoughBalance(amount))
			{
				account.reduceBalance(amount);
				dbSaver.storeBankAccountToRepository(account);
				
				Transaction atmTransaction = createAtmTransaction(iban, "Withdraw", amount);
				dbSaver.storeTransactionToRepository(atmTransaction);
				
				return StatusCode.SUCCESSFUL_TRANSACTION;
			}	
			else
				return StatusCode.NOT_ENOUGH_BALANCE;
		}
		else
		{
			account.increaseBalance(amount);
			dbSaver.storeBankAccountToRepository(account);
			
			Transaction atmTransaction = createAtmTransaction(iban, "Deposit", amount);
			dbSaver.storeTransactionToRepository(atmTransaction);
			
			return StatusCode.SUCCESSFUL_TRANSACTION;
			
		}
		
	}
	
	private Transaction createAtmTransaction(Iban iban, String description, int amount)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime currentDate = LocalDateTime.now();
		String formattedDate = dtf.format(currentDate);
		
		//Let's create a transaction for each user
		Transaction atmTransaction = Transaction.builder()
											    .trans_date(formattedDate)
											    .iban(iban)
											    .amount(amount)
											    .description_message(description)
											    .build();
		
		return atmTransaction;
	}
}
