package com.thanos.mosbank.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thanos.mosbank.model.BankAccount;
import com.thanos.mosbank.model.Card;
import com.thanos.mosbank.model.Credentials;
import com.thanos.mosbank.model.Iban;
import com.thanos.mosbank.model.Transaction;
import com.thanos.mosbank.model.User;
import com.thanos.mosbank.repos.BankAccountRepository;
import com.thanos.mosbank.repos.CardRepository;
import com.thanos.mosbank.repos.CredentialsRepository;
import com.thanos.mosbank.repos.IbanRepository;
import com.thanos.mosbank.repos.TransactionRepository;
import com.thanos.mosbank.repos.UserRepository;

@ExtendWith(MockitoExtension.class)
class DbSaverTest 
{
	@Mock
	UserRepository userRepo;
	@Mock
	CredentialsRepository credRepo;
	@Mock
	CardRepository cardRepo;
	@Mock
	IbanRepository ibanRepo;
	@Mock
	BankAccountRepository bankAccountRepo;
	@Mock
	TransactionRepository transactionsRepo;
	@InjectMocks
	DbSaver testDbSaver;
	
	User createdUser = User.builder()
						   .user_id(1) //there is no interaction with a db so I specify the id manually
						   .firstname("George")
						   .lastname("Ioannou")
						   .email("george@george.com")
						   .telephone("6978787878")
						   .build();
	
	Iban userIban = Iban.builder()
						.iban("GR788965412365897898")
						.user(createdUser)
						.build();
	
	@Test
	void testIfUserIsSaved()
	{	
		//The repo is a mock repo. It does not interact with a db in order to
		//create an id for the object. But in production the id will be
		//created. As a result I specify the id manually in the created user.
		//This store method will not interact with a db so the created object
		//won't have any id. But with when method I specify the wanted 
		//action from the DbSaver.
		testDbSaver.storeUserToRepository("George", "Ioannou", "george@george.com", "6978787878");
				
		when(userRepo.findById(1)).thenReturn(Optional.of(createdUser));
		
		User ret = testDbSaver.fetchUserFromRepository(1);
		assertEquals(createdUser, ret);
	}
	
	@Test
	void testIfCredentialsAreSaved()
	{
		Credentials userCred = Credentials.builder()
										  .username("thanos")
										  .password("changeme")
										  .user(createdUser)
										  .build();
		
		testDbSaver.storeCredentialsToRepository("thanos", "changeme", createdUser);
		
		when(credRepo.findById("thanos")).thenReturn(Optional.of(userCred));
		
		Credentials ret = testDbSaver.fetchSingleCredentialsFromDb("thanos");
		assertEquals(userCred, ret);
	}
	
	@Test
	void testIfCardIsSaved()
	{
		Card userCard = Card.builder()
							.number("4556383361319387")
							.cvv("808")
							.expire_date("08/25")
							.build();
		
		testDbSaver.storeCardToRepository("4556383361319387", "808", "08/25", createdUser);
		
		when(cardRepo.findById("4556383361319387")).thenReturn(Optional.of(userCard));
		
		Card ret = testDbSaver.fetchCardFromDb("4556383361319387");
		assertEquals(userCard, ret);
	}
	
	@Test
	void testIfIbanIsSavedSearchByIbanString()
	{
		testDbSaver.storeIbanToRepository("GR788965412365897898", createdUser);
		
		when(ibanRepo.findById("GR788965412365897898")).thenReturn(Optional.of(userIban));
		
		Iban ret = testDbSaver.fetchIbanFromDb("GR788965412365897898");
		assertEquals(userIban, ret);
	}
	
	@Test
	void testIfIbanIsSavedSearchByUser()
	{
		testDbSaver.storeIbanToRepository("GR788965412365897898", createdUser);
		
		when(ibanRepo.findAll()).thenReturn(List.of(userIban));
		
		Iban ret = testDbSaver.fetchIbanFromDbViaUser(createdUser);
		assertEquals(userIban, ret);
	}
	
	@Test
	void testIfBankAccountIsSavedSearchByBankAccountId()
	{
		BankAccount userBankAccount = BankAccount.builder()
												 .account_id(30)
												 .balance(0)
												 .user(createdUser)
												 .build();
		
		testDbSaver.storeBankAccountToRepository(createdUser);
		
		when(bankAccountRepo.findById(30)).thenReturn(Optional.of(userBankAccount));
		
		BankAccount ret = testDbSaver.fetchBankAccountFromDb(30);
		assertEquals(userBankAccount, ret);
	}
	
	@Test
	void testIfBankAccountIsSavedSearchByUser()
	{
		//in this test I search for a bank account via user
		BankAccount userBankAccount = BankAccount.builder()
												 .account_id(30)
												 .balance(0)
												 .user(createdUser)
												 .build();

		testDbSaver.storeBankAccountToRepository(createdUser);
		
		when(bankAccountRepo.findAll()).thenReturn(List.of(userBankAccount));
		
		BankAccount ret = testDbSaver.fetchBankAccountFromDbViaUser(createdUser);
		assertEquals(userBankAccount, ret);
	}

	@Test
	void testIfTransactionIsSavedSearchByTransactionid()
	{
		Transaction fakeTrans = Transaction.builder()
										   .trans_id(10)
										   .trans_date("01/01/2024")
										   .iban(userIban)
										   .amount(1000)
										   .build();
		
		testDbSaver.storeTransactionToRepository("01/01/2024", userIban, 1000);
		when(transactionsRepo.findById(10)).thenReturn(Optional.of(fakeTrans));
		
		Transaction retTrans = testDbSaver.fetchSingleTransactionFromDb(10);
		assertEquals(fakeTrans, retTrans);
	}
	
	@Test
	void testIfTransactionIsSavedSearchAllUserTransactionsByIban()
	{
		Transaction fakeTrans = Transaction.builder()
										   .trans_id(10)
										   .trans_date("01/01/2024")
										   .iban(userIban)
										   .amount(1000)
										   .build();
		
		testDbSaver.storeTransactionToRepository("01/01/2024", userIban, 1000);
		when(transactionsRepo.findAll()).thenReturn(List.of(fakeTrans));
		
		List<Transaction> retTrans = testDbSaver.fetchAllUserTransactionsViaIban(userIban);
		assertEquals(List.of(fakeTrans), retTrans);
	}
}
