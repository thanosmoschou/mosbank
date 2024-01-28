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
import com.thanos.mosbank.validator.Validator;

@Controller
public class RedirectController 
{
	private Validator validator;
	private ViewEditor viewEditor;
	
	public RedirectController(Validator validator, ViewEditor viewEditor)
	{
		this.validator = validator;
		this.viewEditor = viewEditor;
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
	public String showNewTransactionPage(@PathVariable String userid)
	{
		return "html/newtransaction";
	}
	
	@RequestMapping(path = "/account/{userid}")
	public String showMyAccountPage(@PathVariable String userid)
	{
		return "html/myaccount";
	}
	
	@RequestMapping(path = "/validateLogin", method = RequestMethod.POST)
	public String login(@RequestBody MultiValueMap<String, String> values, Model model)
	{
		//values.get("key") returns a list that contains my value. This list has
		//only one element so with get(0) I can get it.
		String username = values.get("username").get(0);
		String password = values.get("password").get(0);
		
		//the return value is the user's id if user exists, otherwise it contains an error code
		int returnedValue = validator.login(username, password);
		
		if(returnedValue == StatusCode.WRONG_USERNAME)
		{
			model.addAttribute("errorMessage", Alerts.WRONG_USERNAME_MESSAGE);
			model.addAttribute("backUrl", "login"); //this creates a hyperlink to go back to login page
			return "html/somethingwentwrong";
		}
		else if(returnedValue == StatusCode.WRONG_PASSWORD)
		{
			model.addAttribute("errorMessage", Alerts.WRONG_PASSWORD_MESSAGE);
			model.addAttribute("backUrl", "login");
			return "html/somethingwentwrong";
		}
		else
		{
			viewEditor.putInfoToTheMainPageTemplate(returnedValue, model);
			return "html/mainpage";
		}
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
		int returnedValue = validator.signUp(firstname, lastname, username, password, email, telephone);
		
		if(returnedValue == StatusCode.USERNAME_ALREADY_EXISTS)
		{
			model.addAttribute("errorMessage", Alerts.USERNAME_ALREADY_EXISTS_MESSAGE);
			model.addAttribute("backUrl", "signup");
			return "html/somethingwentwrong";
		}
		else if(returnedValue == StatusCode.INVALID_PASSWORD)
		{
			model.addAttribute("errorMessage", Alerts.PASSWORD_DOES_NOT_MEET_CRITERIA_MESSAGE);
			model.addAttribute("backUrl", "signup");
			return "html/somethingwentwrong";
		}
		else if(returnedValue == StatusCode.INVALID_EMAIL)
		{
			model.addAttribute("errorMessage", Alerts.INVALID_EMAIL_MESSAGE);
			model.addAttribute("backUrl", "signup");
			return "html/somethingwentwrong";
		}
		else if(returnedValue == StatusCode.INVALID_PHONE_NUMBER)
		{
			model.addAttribute("errorMessage", Alerts.INVALID_PHONE_NUMBER_MESSAGE);
			model.addAttribute("backUrl", "signup");
			return "html/somethingwentwrong";
		}
		else
		{
			return "html/mainpage";
		}
	}
	
	
}
