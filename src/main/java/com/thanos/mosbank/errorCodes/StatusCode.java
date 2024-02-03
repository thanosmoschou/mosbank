package com.thanos.mosbank.errorCodes;

public class StatusCode
{
	public static final int WRONG_USERNAME = -1;
	public static final int WRONG_PASSWORD = 0;
	public static final int USERNAME_ALREADY_EXISTS = -5;
	public static final int INVALID_PASSWORD = -4;
	public static final int INVALID_EMAIL = -3;
	public static final int INVALID_PHONE_NUMBER = -2;
	public static final int INVALID_RECEIVER_IBAN = -20;
	public static final int NOT_ENOUGH_BALANCE = -30;
	public static final int SUCCESSFUL_TRANSACTION = 100;
	public static final int INVALID_AMOUNT_FOR_TRANSACTION = -400;
	public static final int NEW_PASSWORD_IS_INVALID = -500;
}
