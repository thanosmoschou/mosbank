/*
 * Author: Thanos Moschou
 * Description: This is a banking system using Spring Boot.
 *
 * Last Modification Date: 8/3/2024
 */

package com.thanos.mosbank.generators;

import java.time.LocalDate;
import java.util.Random;

public class CardCredentialsGenerator 
{
	private static final int CARD_DIGITS = 16;
	private static final int PIN_DIGITS = 4;
	private static final int MINIMUM_YEAR_LIMIT_FOR_CARD_CHANGE = 5;
	private static final int MAXIMUM_YEAR_LIMIT_FOR_CARD_CHANGE  = 10;
	private static final int MONTH_RANGE = 12;
	
	/*
	 * This method initially takes the current date.
	 * 
	 * Then it takes the current month (note that if month is less than 10,
	 * it takes the 0 which is at the beginning of the month).
	 * 
	 * Then it gets the last two digits of the current year (for example
	 * for 2024 it takes 24).
	 * 
	 * Then it converts these two to integers to make some calculations.
	 * (if the month is less than 10, the 0 at the beginning is truncated).
	 * 
	 * After that, it creates a random expire year by adding the current year
	 * with a random year in the range 5 to 10 (including 10).
	 * 
	 * Then it creates a random expire month by adding the current month
	 * and a random number in range 0-999. After the addition, 
	 * it takes the modulo of the division with 12 in order to keep the 
	 * number in the range of 0-11. I want to exclude the 0 because it is
	 * not a month and also include the 12 in my range so in the final result I add 1.
	 * 
	 * After the calculations, it creates the expire date as a string.
	 * If the month is less than 10, it adds a 0 in the front.
	 */
	public static String generateExpireDate()
	{
		Random rand = new Random();
		String currDateAsAString = LocalDate.now().toString();
		String last2DigitsOfCurrentYearAsAString = currDateAsAString.substring(2, 4);
		String currentMonthAsAString = currDateAsAString.substring(5, 7);
		int last2DigitsOfCurrentYearAsInteger = Integer.parseInt(last2DigitsOfCurrentYearAsAString);
		int currentMonthAsInteger = Integer.parseInt(currentMonthAsAString);
		
		String expDate = "";
		int expYear = last2DigitsOfCurrentYearAsInteger + 
				rand.nextInt(MINIMUM_YEAR_LIMIT_FOR_CARD_CHANGE, MAXIMUM_YEAR_LIMIT_FOR_CARD_CHANGE + 1);
		int expMonth = ((currentMonthAsInteger + rand.nextInt(0, 1000)) % MONTH_RANGE) + 1;
		
		if(expMonth < 10)
			expDate += ("0" + expMonth);
		else
			expDate += expMonth;
		
		expDate += ("/" + expYear);
		return expDate;
	}
	
	public static String generateCVV()
	{
		Random rand = new Random();
		return Integer.toString(rand.nextInt(100, 1000));
	}
	
	public static String generateCardNumber()
	{
		String generatedCardNumber = createNumber();
		
		while(!isCardNumberValid(generatedCardNumber))
			generatedCardNumber = createNumber();
		
		return generatedCardNumber;
	}
	
	public static String generateCardPin()
	{
		String pin = "";
		Random rand = new Random();
		
		for(int i = 0; i < PIN_DIGITS; i++)
			pin += Integer.toString(rand.nextInt(0, 10)); //10 is exclusive
		
		return pin;
	}
	
	public static String createNumber()
	{
		String card = "";
        Random r = new Random();
        
        for(int i = 0; i < CARD_DIGITS; i++)
            card += r.nextInt(10);
            
        return card;
	}
	
	//Luhn's Algorithm
	public static boolean isCardNumberValid(String aCard)
	{
		int length = aCard.length();
	    int sum = 0;
	    int n;
	    int everySecondDigitCounterStartingBackwards = 0;
	    
	    for(int i = length - 1; i >= 0; i--)
	    {
	        n = Character.getNumericValue(aCard.charAt(i));
	        if(everySecondDigitCounterStartingBackwards % 2 != 0)
	            n *= 2;
	        
	        sum += n / 10;
	        sum += n % 10;
	        
	        everySecondDigitCounterStartingBackwards++;
	    }
	    
	    return (sum % 10 == 0);
	}
}
