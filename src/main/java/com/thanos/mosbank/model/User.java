/*
 * Author: Thanos Moschou
 * Description: This is a banking system using Spring Boot.
 * 
 * 
 * Spring Data Jpa uses underscore notation if an attribute
 * is bigger than 1 word. So in my classes' attributes I use this notation.
 * Keep in mind that in my method parameters and local variables I use lowerCamelcase.
 * 
 * 
 * Last Modification Date: 8/3/2024
 */

package com.thanos.mosbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Builder
@Entity
@Table(name = "users")
public class User 
{
	/*
	 * Spring Data Jpa uses underscore notation if an attribute
	 * is bigger than 1 word. So here in my attributes I use this notation.
	 * Keep in mind that in my method parameters I use lowerCamelcase.
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_id;
	private String firstname;
	private String lastname;
	private String email;
	private String telephone;
	
	public int fetchUserId()
	{
		return this.user_id;
	}
	
	public boolean hasFirstname(String firstname)
	{
		return this.firstname.equals(firstname);
	}

	public void setFirstname(String firstname) 
	{
		this.firstname = firstname;
	}

	public void setLastname(String lastname) 
	{
		this.lastname = lastname;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setTelephone(String telephone) 
	{
		this.telephone = telephone;
	}
}
