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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;
	private int balance;
	@OneToOne
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private User user;
	
	public boolean hasUser(User aUser)
	{
		return this.user.equals(aUser);
	}
	
	public int getBalance()
	{
		return this.balance;
	}
}
