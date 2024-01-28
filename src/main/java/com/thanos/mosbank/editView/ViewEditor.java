package com.thanos.mosbank.editView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.thanos.mosbank.db.DbSaver;
import com.thanos.mosbank.model.BankAccount;
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
		
		BankAccount bankAccount = dbSaver.fetchBankAccountFromDbViaUser(user);
		int balance = bankAccount.getBalance();
		
		model.addAttribute("userid", userId);
		model.addAttribute("firstname", firstname);
		
	}
}
