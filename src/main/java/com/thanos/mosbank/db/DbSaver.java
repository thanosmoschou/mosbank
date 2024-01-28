package com.thanos.mosbank.db;

import org.springframework.beans.factory.annotation.Autowired;

import com.thanos.mosbank.generators.CardCredentialsGenerator;
import com.thanos.mosbank.generators.IbanGenerator;
import com.thanos.mosbank.model.BankAccount;
import com.thanos.mosbank.model.Card;
import com.thanos.mosbank.model.Credentials;
import com.thanos.mosbank.model.Iban;
import com.thanos.mosbank.model.User;
import com.thanos.mosbank.repos.BankAccountRepository;
import com.thanos.mosbank.repos.CardRepository;
import com.thanos.mosbank.repos.CredentialsRepository;
import com.thanos.mosbank.repos.IbanRepository;
import com.thanos.mosbank.repos.UserRepository;

//Singleton Design Pattern
public class DbSaver 
{
	private static DbSaver instance = null;
	
	@Autowired
	private CredentialsRepository credentialsRepo;
	@Autowired
	private UserRepository usersRepo;
	@Autowired
	private CardRepository cardsRepo;
	@Autowired
	private IbanRepository ibanRepo;
	@Autowired
	private BankAccountRepository bankAccountRepo;
	
	private DbSaver()
	{	
	}
	
	public static DbSaver getInstance()
	{
		if(instance == null)
			instance = new DbSaver();
		return instance;
	}
	
	public User fetchUserFromRepository(int id)
	{
		return usersRepo.findById(id).get();
	}
	
	public Card fetchCardFromDb(String cardNumber)
	{
		return cardsRepo.findById(cardNumber).get();
	}
	
	public Credentials fetchCredentialsFromDb(String username)
	{
		return credentialsRepo.findById(username).get();
	}
	
	public Iban fetchIbanFromDb(String iban)
	{
		return ibanRepo.findById(iban).get();
	}
	
	public BankAccount fetchBankAccountFromDb(int id)
	{
		return bankAccountRepo.findById(id).get();
	}
	
	public void storeUserToRepository(String firstname, String lastname, String email, String telephone)
	{
		//id is created automatically in the db
		User createdUser = User.builder()
						       .firstname(firstname)
						       .lastname(lastname)
						       .email(email)
						       .telephone(telephone)
						       .build();
		
		usersRepo.save(createdUser);
	}
	
	public void storeCardToRepository(String cardNumber, String expDate, String cvv, User createdUser)
	{
		//generate user's card
		Card userCard = Card.builder()
							.number(cardNumber)
							.expireDate(expDate)
							.cvv(cvv)
							.user(createdUser)
							.build();
		
		cardsRepo.save(userCard);
	}
	
	public void storeCredentialsToRepository(String username, String password, User createdUser)
	{
		Credentials userCred = Credentials.builder()
										  .username(username)
										  .password(password)
										  .user(createdUser)
										  .build();

		credentialsRepo.save(userCred);
	}
	
	public void storeIbanToRepository(String ibanNumber, User createdUser)
	{
		Iban userIban = Iban.builder()
							.iban(ibanNumber)
							.user(createdUser)
							.build();

		ibanRepo.save(userIban);
	}
	
	public void storeBankAccountToRepository(User createdUser)
	{
		//account id is created automatically
		BankAccount userBankAccount = BankAccount.builder()
												 .balance(0)
												 .user(createdUser)
												 .build();
		
		bankAccountRepo.save(userBankAccount);
	}
	
}
