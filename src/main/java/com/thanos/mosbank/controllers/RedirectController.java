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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thanos.mosbank.StatusCode;
import com.thanos.mosbank.alerts.Alerts;
import com.thanos.mosbank.validator.Validator;

@Controller
public class RedirectController 
{
	private Validator validator;
	
	public RedirectController(Validator validator)
	{
		this.validator = validator;
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
	
	@RequestMapping(path = "/validateLogin", method = RequestMethod.POST)
	public String validateLoginCredentials(@RequestBody MultiValueMap<String, String> values, Model model)
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
			model.addAttribute("backUrl", "login");
			return "html/somethingwentwrong";
		}
		else if(returnedValue == StatusCode.WRONG_PASSWORD)
		{
			model.addAttribute("errorMessage", Alerts.WRONG_PASSWORD_MESSAGE);
			model.addAttribute("backUrl", "login");
			return "html/somethingwentwrong";
		}
		else
			return "html/mainPage"; //fetch users data to customize the page
	}
	
	@RequestMapping(path = "/validateSignUp", method = RequestMethod.POST)
	public String validateSignInCredentials(@RequestBody MultiValueMap<String, String> values)
	{
		System.out.println(values.get("firstname") + " " + values.get("lastname") +
				" " + values.get("username") + " " + values.get("password") +
				" " + values.get("email") + " " + values.get("telephone"));
		return "html/mainPage";
	}
	
	
}
