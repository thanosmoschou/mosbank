package com.thanos.mosbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bankaccounts")
public class BankAccount 
{
	/*
	 * Spring Data Jpa uses underscore notation if an attribute
	 * is bigger than 1 word. So here in my attributes I use this notation.
	 * Keep in mind that in my method parameters I use lowerCamelcase.
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int account_id;
	private int balance;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	public boolean hasUser(User aUser)
	{
		return this.user.equals(aUser);
	}
	
	public void increaseBalance(int amount)
	{
		this.balance += amount;
	}
	
	public void reduceBalance(int amount)
	{
		this.balance -= amount;
	}
	
	public boolean hasEnoughBalance(int amount)
	{
		return (this.balance - amount) >= 0;
	}
	
	public int getBalance()
	{
		return this.balance;
	}
}
