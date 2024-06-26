/*
 * Author: Thanos Moschou
 * Description: This is a banking system using Spring Boot.
 *
 * Last Modification Date: 8/3/2024
 */

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
	public static final String SUCCESSFUL_TRANSACTION_MESSAGE = "The transaction was successful.";
	public static final String INVALID_AMOUNT_FOR_TRANSACTION_MESSAGE = "Negative or 0 values are not allowed. Try again";
	public static final String USER_DID_NOT_LOGGED_IN_MESSAGE = "You are not logged in. Log in and try again.";
	public static final String NEW_PASSWORD_IS_INVALID_MESSAGE = "The new password is invalid. Make sure that you enter a valid password and the 2 password fields"
			+ " contain the same value.";
	public static final String SUCCESSFUL_ACCOUNT_EDIT_MESSAGE = "Your account details have successfully changed.";
	public static final String INVALID_CARD_CREDENTIALS_MESSAGE = "The given credentials are wrong. Maybe the card number does not exist or the pin is wrong. Try again.";
}
