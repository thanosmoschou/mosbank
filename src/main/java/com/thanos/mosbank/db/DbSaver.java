package com.thanos.mosbank.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
	@Autowired
	private TransactionRepository transactionsRepo;
	
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
	
	public List<User> fetchAllUsersFromRepository()
	{
		return usersRepo.findAll();
	}
	
	public User fetchUserFromRepositorySearchByIban(String iban)
	{
		Iban userIban = fetchIbanFromRepository(iban);
		List<User> users = fetchAllUsersFromRepository();
		
		for(User u : users)
			if(userIban.hasUser(u))
				return u;
		return null;
	}
	
	public User fetchUserFromRepositorySearchByCredentialsObject(Credentials cred)
	{
		List<User> users = fetchAllUsersFromRepository();
		
		for(User u : users)
			if(cred.hasUser(u))
				return u;
		return null;
	}
	
	public Card fetchCardFromRepository(String cardNumber)
	{
		return cardsRepo.findById(cardNumber).get();
	}
	
	public Credentials fetchSingleCredentialsFromRepository(String username)
	{
		return credentialsRepo.findById(username).get();
	}
	
	public Credentials fetchSingleCredentialsFromRepositoryPassUser(User aUser)
	{
		List<Credentials> allCredentials = credentialsRepo.findAll();
		
		for(Credentials cred : allCredentials)
			if(cred.hasUser(aUser))
				return cred;
		return null;
	}
	
	//maybe useless here
	public Iban fetchIbanFromRepository(String iban)
	{
		return ibanRepo.findById(iban).get();
	}
	
	public Iban fetchIbanFromRepositoryViaUser(User user)
	{
		List<Iban> ibans = ibanRepo.findAll();
		
		for(Iban i : ibans)
			if(i.hasUser(user))
				return i;
		return null;
	}
	
	//Maybe useless in my case because I do not know the id explicitly.
	//This is useful if I know the id from the db and retrieve the account.
	public BankAccount fetchBankAccountFromRepository(int id)
	{
		return bankAccountRepo.findById(id).get();
	}
	
	public BankAccount fetchBankAccountFromRepositoryViaUser(User user)
	{
		List<BankAccount> accounts = bankAccountRepo.findAll();
		
		for(BankAccount a : accounts)
			if(a.hasUser(user))
				return a;
		return null;
	}
	
	public BankAccount fetchBankAccountFromRepositorySearchByIban(String iban)
	{
		User user = fetchUserFromRepositorySearchByIban(iban);
		BankAccount userBankAccount = fetchBankAccountFromRepositoryViaUser(user);
		
		return userBankAccount;
	}
	
	//The same explanation here
	public Transaction fetchSingleTransactionFromRepository(int transId)
	{
		return transactionsRepo.findById(transId).get();
	}
	
	public List<Transaction> fetchAllUserTransactionsViaIban(Iban iban)
	{
		List<Transaction> userTrans = new ArrayList<Transaction>();
		
		for(Transaction tr : transactionsRepo.findAll())
			if(tr.hasIban(iban))
				userTrans.add(tr);
		return userTrans;
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
	
	/*
	 * For each entity I provide 2 methods that do the same. I overloaded them.
	 * The one method gets the fields and creates the object internally and
	 * then stores it to the db.
	 * The other one gets the object and stores it straight away.
	 * 
	 * I use the one that is more convenient in each case.
	 */
	
	//Method overloading
	public void storeUserToRepository(User user)
	{
		//Note that the save() method will perform an update if the 
		//entity already exists in the database, and an insert if it does not.
		usersRepo.save(user);
	}
	
	public void storeCardToRepository(String cardNumber, String expDate, String cvv, User createdUser)
	{
		//generate user's card
		Card userCard = Card.builder()
							.number(cardNumber)
							.expire_date(expDate)
							.cvv(cvv)
							.user(createdUser)
							.build();
		
		cardsRepo.save(userCard);
	}
	
	public void storeCardToRepository(Card card)
	{
		cardsRepo.save(card);
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
	
	public void storeCredentialsToRepository(Credentials credentials)
	{
		credentialsRepo.save(credentials);
	}
	
	public void storeIbanToRepository(String ibanNumber, User createdUser)
	{
		Iban userIban = Iban.builder()
							.iban(ibanNumber)
							.user(createdUser)
							.build();

		ibanRepo.save(userIban);
	}
	
	public void storeIbanToRepository(Iban iban)
	{
		ibanRepo.save(iban);
	}
	
	public void storeBankAccountToRepositoryForUser(User createdUser)
	{
		//account id is created automatically
		BankAccount userBankAccount = BankAccount.builder()
												 .balance(0)
												 .user(createdUser)
												 .build();
		
		bankAccountRepo.save(userBankAccount);
	}
	
	public void storeBankAccountToRepository(BankAccount bankAccount)
	{
		bankAccountRepo.save(bankAccount);
	}
	
	public void storeTransactionToRepository(String date, Iban iban, int amount, String description)
	{
		//transaction id is created automatically
		Transaction userTransaction = Transaction.builder()
												 .trans_date(date)
												 .iban(iban)
												 .amount(amount)
												 .description_message(description)
												 .build();
		
		transactionsRepo.save(userTransaction);
	}
	
	public void storeTransactionToRepository(Transaction transaction)
	{
		transactionsRepo.save(transaction);
	}
	
	public void deleteCredentialsObjectFromRepository(Credentials credentials)
	{
		credentialsRepo.delete(credentials);
	}
	
}
