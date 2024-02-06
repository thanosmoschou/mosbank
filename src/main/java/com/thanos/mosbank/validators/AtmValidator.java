package com.thanos.mosbank.validators;

import org.springframework.beans.factory.annotation.Autowired;

import com.thanos.mosbank.db.DbSaver;
import com.thanos.mosbank.model.Card;

public class AtmValidator 
{
	private static AtmValidator instance = null;
	
	@Autowired
	private DbSaver dbSaver;
	
	private AtmValidator()
	{
		
	}
	
	public static AtmValidator getInstance()
	{
		if(instance == null)
			instance = new AtmValidator();
		return instance;
	}
	
	public Card login(String number, String pin)
	{
		Card card = dbSaver.fetchCardFromRepository(number);
		
		if(card == null)
			return null;
		
		if(card.hasPin(pin))
			return card;
		
		return null;
	}
}
