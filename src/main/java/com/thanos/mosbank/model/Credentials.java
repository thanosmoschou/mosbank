package com.thanos.mosbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "credentials")
public class Credentials 
{
	/*
	 * Spring Data Jpa uses underscore notation if an attribute
	 * is bigger than 1 word. So here in my attributes I use this notation.
	 * Keep in mind that in my method parameters I use lowerCamelcase.
	 */
	
	@Id
	private String username;
	private String password;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user; //jpa creates a whole object of a record of the table that foreign key references to.
	
	public boolean hasPassword(String password)
	{
		return this.password.equals(password);
	}
	
	public int fetchUserId()
	{
		return user.fetchUserId();
	}
	
	public boolean hasUsername(String aUsername)
	{
		return this.username.equals(aUsername);
	}
	
	public boolean hasUser(User aUser)
	{
		return this.user.equals(aUser);
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public void setUsername(String aUsername)
	{
		this.username = aUsername;
	}
	
	public void setPassword(String aPassword)
	{
		this.password = aPassword;
	}
	
}
