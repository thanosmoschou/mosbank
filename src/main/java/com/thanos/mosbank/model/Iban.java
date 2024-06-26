/*
 * Author: Thanos Moschou
 * Description: This is a banking system using Spring Boot.
 *
 * Last Modification Date: 8/3/2024
 */

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
	 JPA and Hibernate, by default, follow certain naming conventions which can
	 lead to mismatches between entity field names and database column names if they
	 are not explicitly mapped. By default, Hibernate uses the camel case notation of entity
	 fields and converts them into underscore-separated column names in SQL queries
	 (e.g., userId becomes user_id).
	 If your database columns use camel case and your entity fields also use camel case,
	 you might face the issue where Hibernate expects an underscore-separated column name but
	 the actual column name in the database is camel case.

	 To address this, you can do the following:

	 1. Explicitly Map Column Names
	 Explicitly map the column names in your entities using the @Column annotation to
	 ensure Hibernate generates the correct SQL.

	 2. Configure Hibernate Naming Strategy
	 You can configure Hibernate to use the exact column names as defined in your
	 entity classes without converting them to underscore notation by setting the following
	 properties in your application.properties or application.yml

	 spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

	 By explicitly mapping your entity fields to their respective database columns and configuring
	 Hibernate to use the appropriate naming strategy, you can avoid issues related to column name mismatches.
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
