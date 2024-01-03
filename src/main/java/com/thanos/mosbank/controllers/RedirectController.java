/*
 * Author: Thanos Moschou
 * Description: This is a banking system using Spring Boot
 * 
 * Last Modification Date: 3/1/2024
 */

package com.thanos.mosbank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController 
{
	@RequestMapping(path = "/")
	public String showIndexPage()
	{
		return showLoginPage();
	}
	
	
	@RequestMapping(path = "/login")
	public String showLoginPage()
	{
		return "html/login.html";
	}
	
	
	@RequestMapping(path = "/signup")
	public String showSignupPage()
	{
		return "html/signup.html";
	}
}
