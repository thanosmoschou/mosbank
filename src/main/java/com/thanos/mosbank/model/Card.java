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
	@Id
	private String number;
	private String expireDate;
	private String cvv;
	@OneToOne
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private User user;
}
