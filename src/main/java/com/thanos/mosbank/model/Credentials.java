package com.thanos.mosbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "credentials")
public class Credentials 
{
	@Id
	private String username;
	private String password;
	@OneToOne
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private User user; //jpa creates a whole object of a record of the table that foreign key references to.
	
	public boolean hasPassword(String password)
	{
		return this.password.equals(password);
	}
	
	public int fetchUserId()
	{
		return user.fetchUserId();
	}
	
}
