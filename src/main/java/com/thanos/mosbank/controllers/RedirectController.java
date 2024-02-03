/*
 * Author: Thanos Moschou
 * Description: This is a banking system using Spring Boot
 * 
 * Last Modification Date: 3/1/2024
 */

package com.thanos.mosbank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thanos.mosbank.alerts.Alerts;
import com.thanos.mosbank.editView.ViewEditor;
import com.thanos.mosbank.errorCodes.StatusCode;
import com.thanos.mosbank.validators.AccountValidator;
import com.thanos.mosbank.validators.TransactionValidator;

@Controller
public class RedirectController 
{
	private AccountValidator accountValidator;
	private TransactionValidator transactionValidator;
	private ViewEditor viewEditor;
	
	private int userId;
	
	public RedirectController(AccountValidator accountValidator, TransactionValidator transactionValidator, ViewEditor viewEditor)
	{
		this.accountValidator = accountValidator;
		this.transactionValidator = transactionValidator;
		this.viewEditor = viewEditor;
		this.userId = 0;
	}
	
	@RequestMapping(path = "/")
	public String showIndexPage()
	{
		return showLoginPage();
	}
	
	@RequestMapping(path = "/login")
	public String showLoginPage()
	{
		//Thymeleaf does not require .html at the end.
		//It searches for templates under src/main/resources/templates
		//Keep in mind that if you decorated an html file with a css file
		//or you have a js script, these files are static files
		//and they are located inside src/main/resources/static
		return "html/login"; 
	}
	
	@RequestMapping(path = "/signup")
	public String showSignupPage()
	{
		return "html/signup";
	}
	
	@RequestMapping(path = "/transaction/{userid}")
	public String showNewTransactionPage(@PathVariable String userid, Model model)
	{
		this.userId = Integer.parseInt(userid);
		viewEditor.putInfoToNewTransactionPageTemplate(userId, model);
		return "html/newtransaction";
	}
	
	@RequestMapping(path = "/account/{userid}")
	public String showMyAccountPage(@PathVariable String userid, Model model)
	{
		this.userId = Integer.parseInt(userid);
		viewEditor.putInfoToMyAccountPageTemplate(userId, model);
		return "html/myaccount";
	}
	
	@RequestMapping(path = "/validateLogin", method = RequestMethod.POST)
	public String login(@RequestBody MultiValueMap<String, String> values, Model model)
	{
		//values.get("key") returns a list that contains my value. This list has
		//only one element so with get(0) I can get it.
		String username = values.get("username").get(0);
		String password = values.get("password").get(0);
		
		//TESTING PURPOSES
		this.userId = 3;
		return goToMainPage(model);
		
		//the return value is the user's id if user exists, otherwise it contains an error code
		
		/*int returnedValue = accountValidator.login(username, password);
		
		if(returnedValue == StatusCode.WRONG_USERNAME)
			return goToSomethingWentWrongPage(Alerts.WRONG_USERNAME_MESSAGE, "login", model);
		else if(returnedValue == StatusCode.WRONG_PASSWORD)
			return goToSomethingWentWrongPage(Alerts.WRONG_PASSWORD_MESSAGE, "login", model);
		else
		{
			this.userId = returnedValue;
			return goToMainPage(model);
		}
		*/
		
	}
	
	@RequestMapping(path = "/validateSignUp", method = RequestMethod.POST)
	public String signUp(@RequestBody MultiValueMap<String, String> values, Model model)
	{
		String firstname = values.get("firstname").get(0);
		String lastname = values.get("lastname").get(0);
		String username = values.get("username").get(0);
		String password = values.get("password").get(0);
		String email = values.get("email").get(0);
		String telephone = values.get("telephone").get(0);
		
		//the return value is the user's id if user exists, otherwise it contains an error code
		int returnedValue = accountValidator.signUp(firstname, lastname, username, password, email, telephone);
		
		if(returnedValue == StatusCode.USERNAME_ALREADY_EXISTS)
			return goToSomethingWentWrongPage(Alerts.USERNAME_ALREADY_EXISTS_MESSAGE, "signup", model);
		else if(returnedValue == StatusCode.INVALID_PASSWORD)
			return goToSomethingWentWrongPage(Alerts.PASSWORD_DOES_NOT_MEET_CRITERIA_MESSAGE, "signup", model);
		else if(returnedValue == StatusCode.INVALID_EMAIL)
			return goToSomethingWentWrongPage(Alerts.INVALID_EMAIL_MESSAGE, "signup", model);
		else if(returnedValue == StatusCode.INVALID_PHONE_NUMBER)
			return goToSomethingWentWrongPage(Alerts.INVALID_PHONE_NUMBER_MESSAGE, "signup", model);
		else
		{
			this.userId = returnedValue;
			return goToMainPage(model);
		}
	}	
	
	@RequestMapping(path = "/validateNewTransaction", method = RequestMethod.POST)
	public String newTransaction(@RequestBody MultiValueMap<String, String> values, Model model)
	{
		//Validate the transaction
		String senderIban = values.get("sIban").get(0);
		String receiverIban = values.get("rIban").get(0);
		int amount = Integer.parseInt(values.get("sAmount").get(0));
		String description = values.get("description").get(0);
		
		int transactionStatusCode = transactionValidator.makeTransaction(senderIban, receiverIban, amount, description);
		
		if(transactionStatusCode == StatusCode.INVALID_AMOUNT_FOR_TRANSACTION)
			return goToSomethingWentWrongPage(Alerts.INVALID_AMOUNT_FOR_TRANSACTION_MESSAGE, "transaction/" + this.userId, model);	
		else if(transactionStatusCode == StatusCode.INVALID_RECEIVER_IBAN)
			return goToSomethingWentWrongPage(Alerts.INVALID_RECEIVER_IBAN_MESSAGE, "transaction/" + this.userId, model);	
		else if(transactionStatusCode == StatusCode.NOT_ENOUGH_BALANCE)
			return goToSomethingWentWrongPage(Alerts.NOT_ENOUGH_BALANCE_MESSAGE, "transaction/" + this.userId, model);	
		else
		{
			model.addAttribute("statusMessage", Alerts.SUCCESSFUL_TRANSACTION_MESSAGE);
			model.addAttribute("mainPageUrl", "mainpage");
			return "html/success";
		}
	}
	
	@RequestMapping(path = "/mainpage")
	public String goToMainPage(Model model)
	{
		if(this.userId != 0)
		{
			viewEditor.putInfoToTheMainPageTemplate(this.userId, model);
			return "html/mainpage";
		}
		else
		{
			model.addAttribute("errorMessage", Alerts.USER_DID_NOT_LOGGED_IN_MESSAGE);
			model.addAttribute("backUrl", "login"); //this creates a hyperlink to go back to login
			return "html/somethingwentwrong";
		}
	}
	
	@RequestMapping(path = "/validateMyAccountEdit", method = RequestMethod.POST)
	public String editAccount(@RequestBody MultiValueMap<String, String> values, Model model)
	{
		String firstname = values.get("firstnameInput").get(0);
		String lastname = values.get("lastnameInput").get(0);
		String username = values.get("usernameInput").get(0);
		String newPassword = values.get("passwordInput").get(0);
		String newPasswordRetype = values.get("passwordInputRetype").get(0);
		String email = values.get("emailInput").get(0);
		String telephone = values.get("telephoneInput").get(0);
		
		//the return value is the user's id if user exists, otherwise it contains an error code
		int returnedValue = accountValidator.editAccount(userId, firstname, lastname, username, newPassword, newPasswordRetype, email, telephone);
		
		if(returnedValue == StatusCode.USERNAME_ALREADY_EXISTS)
			return goToSomethingWentWrongPage(Alerts.USERNAME_ALREADY_EXISTS_MESSAGE, "account/" + this.userId, model);
		else if(returnedValue == StatusCode.NEW_PASSWORD_IS_INVALID)
			return goToSomethingWentWrongPage(Alerts.NEW_PASSWORD_IS_INVALID_MESSAGE, "account/" + this.userId, model);
		else if(returnedValue == StatusCode.INVALID_EMAIL)
			return goToSomethingWentWrongPage(Alerts.INVALID_EMAIL_MESSAGE, "account/" + this.userId, model);
		else if(returnedValue == StatusCode.INVALID_PHONE_NUMBER)
			return goToSomethingWentWrongPage(Alerts.INVALID_PHONE_NUMBER_MESSAGE, "account/" + this.userId, model);
		else
		{
			model.addAttribute("statusMessage", Alerts.SUCCESSFUL_ACCOUNT_EDIT_MESSAGE);
			model.addAttribute("mainPageUrl", "mainpage");
			return "html/success";
		}
	}
	
	private String goToSomethingWentWrongPage(String errorMessage, String backUrl, Model model)
	{
		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("backUrl", backUrl); 
		return "html/somethingwentwrong";
	}
}
