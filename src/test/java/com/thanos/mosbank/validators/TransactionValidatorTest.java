package com.thanos.mosbank.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thanos.mosbank.db.DbSaver;
import com.thanos.mosbank.model.BankAccount;

@ExtendWith(MockitoExtension.class)
class TransactionValidatorTest 
{
	@Mock
	DbSaver dbSaver;
	@InjectMocks
	TransactionValidator transactionValidator;

	
	@Test
	void balanceIsEnough()
	{
		BankAccount account = BankAccount.builder()
										 .account_id(1)
										 .balance(1000)
										 .user(null)
										 .build();
		
		when(dbSaver.fetchBankAccountFromRepositorySearchByIban("GR101010101010101010")).thenReturn(account);
		
		assertTrue(transactionValidator.isBalanceEnough("GR101010101010101010", 900));
	}
	
	
	@Test
	void balanceIsNotEnough()
	{
		BankAccount account = BankAccount.builder()
										 .account_id(1)
										 .balance(1000)
										 .user(null)
										 .build();
		
		when(dbSaver.fetchBankAccountFromRepositorySearchByIban("GR101010101010101010")).thenReturn(account);
		
		assertFalse(transactionValidator.isBalanceEnough("GR101010101010101010", 9000));
	}
}
