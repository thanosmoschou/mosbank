package com.thanos.mosbank.editView;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.thanos.mosbank.db.DbSaver;
import com.thanos.mosbank.model.BankAccount;
import com.thanos.mosbank.model.Credentials;
import com.thanos.mosbank.model.Iban;
import com.thanos.mosbank.model.Transaction;
import com.thanos.mosbank.model.User;

//Singleton
public class ViewEditor 
{
	private static ViewEditor instance = null;
	@Autowired
	private DbSaver dbSaver;
	
	private ViewEditor()
	{
	}
	
	
	public static ViewEditor getInstance()
	{
		if(instance == null)
			instance = new ViewEditor();
		return instance;
	}
	
	
	public void putInfoToTheMainPageTemplate(int userId, Model model)
	{
		User user = dbSaver.fetchUserFromRepository(userId);
		String firstname = user.getFirstname();
		
		BankAccount bankAccount = dbSaver.fetchBankAccountFromRepositoryViaUser(user);
		int balance = bankAccount.getBalance();
		
		Iban userIban = dbSaver.fetchIbanFromRepositoryViaUser(user);
		
		List<Transaction> transactions = dbSaver.fetchAllUserTransactionsViaIban(userIban);
		
		model.addAttribute("user_id", userId);
		model.addAttribute("firstname", firstname);
		model.addAttribute("balance", balance);
		model.addAttribute("usertransactions", transactions);
	}
	
	
	public void putInfoToNewTransactionPageTemplate(int userId, Model model)
	{
		User user = dbSaver.fetchUserFromRepository(userId);
		Iban userIban = dbSaver.fetchIbanFromRepositoryViaUser(user);
		String ibanAsAString = userIban.getIban();
		
		model.addAttribute("user_id", userId);
		model.addAttribute("senderIban", ibanAsAString);
	}
	
	
	public void putInfoToMyAccountPageTemplate(int userId, Model model)
	{
		User user = dbSaver.fetchUserFromRepository(userId);
		Credentials userCred = dbSaver.fetchSingleCredentialsFromRepositoryPassUser(user);
				
		String firstname = user.getFirstname();
		String lastname = user.getLastname();
		String username = userCred.getUsername();
		String email = user.getEmail();
		String telephone = user.getTelephone();
		
		model.addAttribute("user_id", userId);
		model.addAttribute("firstname", firstname);
		model.addAttribute("lastname", lastname);
		model.addAttribute("username", username);
		model.addAttribute("email", email);
		model.addAttribute("telephone", telephone);
	}
	
	
	public void putInfoToCardPageTemplate(int userId, Model model)
	{
		User user = dbSaver.fetchUserFromRepository(userId);
		BankAccount account = dbSaver.fetchBankAccountFromRepositoryViaUser(user);
		int balance = account.getBalance();
		String firstname = user.getFirstname();
		
		model.addAttribute("firstname", firstname);
		model.addAttribute("balance", balance);
	}
}
