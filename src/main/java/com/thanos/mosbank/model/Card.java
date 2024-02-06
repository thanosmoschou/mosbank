package com.thanos.mosbank.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cards")
public class Card 
{
	/*
	 * Spring Data Jpa uses underscore notation if an attribute
	 * is bigger than 1 word. So here in my attributes I use this notation.
	 * Keep in mind that in my method parameters I use lowerCamelcase.
	 */
	
	@Id
	private String number;
	private String pin;
	private String expire_date;
	private String cvv;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	public boolean hasPin(String pin)
	{
		return this.pin.equals(pin);
	}
	
	public boolean hasUser(User aUser)
	{
		return this.user.equals(aUser);
	}
	
	public int fetchUserId()
	{
		return this.user.fetchUserId();
	}
}
