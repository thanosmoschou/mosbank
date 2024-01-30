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
	
	/*
	public void printInfo()
	{
		System.out.println("Id: " + this.id);
		System.out.println("Firstname: " + this.firstname);
		System.out.println("Lastname: " + this.lastname);
		System.out.println("Email: " + this.email);
		System.out.println("Telephone: " + this.telephone);
	}
	*/
}
