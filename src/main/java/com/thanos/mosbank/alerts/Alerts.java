package com.thanos.mosbank.alerts;

public class Alerts 
{
	public static final String WRONG_USERNAME_MESSAGE = "There is not a user with this username.";
	public static final String WRONG_PASSWORD_MESSAGE = "Your password is incorrect. Try again.";
	public static final String USERNAME_ALREADY_EXISTS_MESSAGE = "This username already exists. Select another one.";
	public static final String PASSWORD_DOES_NOT_MEET_CRITERIA_MESSAGE = "Password must be at least 8 digits long."
			+ " It must not have spaces. It must contain"
			+ " at least one number, at least a lowercase letter, at least an uppercase letter and"
			+ " at least one of the following symbols: !@#$%^&*";
	public static final String INVALID_EMAIL_MESSAGE = "This email is invalid. Enter a valid one.";
	public static final String INVALID_PHONE_NUMBER_MESSAGE = "This is not a Greek phone. Try again and type a different one.";
	public static final String INVALID_RECEIVER_IBAN_MESSAGE = "The receiver's IBAN does not exist. Please go back and try again.";
	public static final String NOT_ENOUGH_BALANCE_MESSAGE = "Your balance is not enough to complete the transaction. Please enter another amount.";
	public static final String SUCCESSFUL_TRANSACTION_MESSAGE = "The transaction was successful. Return to the main page.";
	public static final String INVALID_AMOUNT_FOR_TRANSACTION_MESSAGE = "Negative or 0 values are not allowed. Try again";
	public static final String USER_DID_NOT_LOGGED_IN_MESSAGE = "You are not logged in. Log in and try again.";
}
