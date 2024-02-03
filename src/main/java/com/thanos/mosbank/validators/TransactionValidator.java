package com.thanos.mosbank.validators;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;

import com.thanos.mosbank.db.DbSaver;
import com.thanos.mosbank.errorCodes.StatusCode;
import com.thanos.mosbank.model.BankAccount;
import com.thanos.mosbank.model.Iban;
import com.thanos.mosbank.model.Transaction;

//TEST FOR THIS

//Singleton
public class TransactionValidator
{
	private static TransactionValidator instance = null;
	
	@Autowired
	private DbSaver dbSaver;
	
	private TransactionValidator()
	{
	}
	
	public static TransactionValidator getInstance()
	{
		if(instance == null)
			instance = new TransactionValidator();
		return instance;
	}
	
	/*
	 * This method implements the transaction. Before the transaction
	 * it needs to check if the transaction can be done.
	 * 
	 * It checks if the receiver's iban exists. (The sender's iban cannot be edited
	 * so it is sure that it exists because it came out of the system).
	 * 
	 * Then it checks if the sender's balance is enough in order to make the transaction.
	 * 
	 * If all the requirements are met, then the transaction is done.
	 * 
	 * RETURN CODES:
	 * -20: Receiver's iban does not exist
	 * -30: Balance is not enough
	 * -400: Invalid transaction amount (negative or 0)
	 * 100: Transaction is successful.
	 */
	public int makeTransaction(String sIban, String rIban, int sAmount, String description)
	{
		if(sAmount <= 0)
			return StatusCode.INVALID_AMOUNT_FOR_TRANSACTION;
		
		if(!isReceiverIbanInDb(rIban))
			return StatusCode.INVALID_RECEIVER_IBAN;
		
		if(!isBalanceEnough(sIban, sAmount))
			return StatusCode.NOT_ENOUGH_BALANCE;
		
		BankAccount sBankAccount = dbSaver.fetchBankAccountFromRepositorySearchByIban(sIban);
		sBankAccount.reduceBalance(sAmount);
		
		BankAccount rBankAccount = dbSaver.fetchBankAccountFromRepositorySearchByIban(rIban);
		rBankAccount.increaseBalance(sAmount);
		
		//update bank accounts
		dbSaver.storeBankAccountToRepository(sBankAccount);
		dbSaver.storeBankAccountToRepository(rBankAccount);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime currentDate = LocalDateTime.now();
		String formattedDate = dtf.format(currentDate);
		
		//Transaction object need Iban object as attribute
		Iban senderIbanObject = dbSaver.fetchIbanFromRepository(sIban);
		Iban receiverIbanObject = dbSaver.fetchIbanFromRepository(rIban);
		
		//Let's create a transaction for each user
		Transaction senderTransaction = Transaction.builder()
											       .trans_date(formattedDate)
											       .iban(senderIbanObject)
											       .amount(sAmount)
											       .description_message(description)
											       .build();
		
		Transaction receiverTransaction = Transaction.builder()
													 .trans_date(formattedDate)
													 .iban(receiverIbanObject)
													 .amount(sAmount)
													 .description_message(description)
													 .build();
		
		dbSaver.storeTransactionToRepository(senderTransaction);
		dbSaver.storeTransactionToRepository(receiverTransaction);
											       
		return StatusCode.SUCCESSFUL_TRANSACTION;
	}
	
	public boolean isReceiverIbanInDb(String rIban)
	{
		Iban receiver = dbSaver.fetchIbanFromRepository(rIban);
		
		return receiver != null;
	}
	
	public boolean isBalanceEnough(String sIban, int amount)
	{
		BankAccount sendersBankAccount = dbSaver.fetchBankAccountFromRepositorySearchByIban(sIban);
		
		return sendersBankAccount.hasEnoughBalance(amount);
	}
}
