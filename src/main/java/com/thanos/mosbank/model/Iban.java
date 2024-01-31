package com.thanos.mosbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ibans")
public class Iban 
{
	/*
	 * Spring Data Jpa uses underscore notation if an attribute
	 * is bigger than 1 word. So here in my attributes I use this notation.
	 * Keep in mind that in my method parameters I use lowerCamelcase.
	 */
	
	@Id
	private String iban;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	public boolean hasUser(User aUser)
	{
		return this.user.equals(aUser);
	}
	
	public boolean hasIban(Iban o)
	{
		return this.iban.equals(o.iban);
	}
	
	public String getIban()
	{
		return this.iban;
	}
}
