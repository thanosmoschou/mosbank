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


//MAKE METHOD TO STORE THE UPDATED BALANCE FOR AN ACCOUNT

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
	
	//test for this
	public User fetchUserFromRepositorySearchByIban(String iban)
	{
		Iban userIban = fetchIbanFromDb(iban);
		List<User> users = fetchAllUsersFromRepository();
		
		for(User u : users)
			if(userIban.hasUser(u))
				return u;
		return null;
	}
	
	public Card fetchCardFromDb(String cardNumber)
	{
		return cardsRepo.findById(cardNumber).get();
	}
	
	public Credentials fetchSingleCredentialsFromDb(String username)
	{
		return credentialsRepo.findById(username).get();
	}
	
	//maybe useless here
	public Iban fetchIbanFromDb(String iban)
	{
		return ibanRepo.findById(iban).get();
	}
	
	public Iban fetchIbanFromDbViaUser(User user)
	{
		List<Iban> ibans = ibanRepo.findAll();
		
		for(Iban i : ibans)
			if(i.hasUser(user))
				return i;
		return null;
	}
	
	
	//Maybe useless in my case because I do not know the id explicitly.
	//This is useful if I know the id from the db and retrieve the account.
	public BankAccount fetchBankAccountFromDb(int id)
	{
		return bankAccountRepo.findById(id).get();
	}
	
	public BankAccount fetchBankAccountFromDbViaUser(User user)
	{
		List<BankAccount> accounts = bankAccountRepo.findAll();
		
		for(BankAccount a : accounts)
			if(a.hasUser(user))
				return a;
		return null;
	}
	
	//test for this
	public BankAccount fetchBankAccountFromDbSearchByIban(String iban)
	{
		User user = fetchUserFromRepositorySearchByIban(iban);
		BankAccount userBankAccount = fetchBankAccountFromDbViaUser(user);
		
		return userBankAccount;
	}
	
	//The same explanation here
	public Transaction fetchSingleTransactionFromDb(int transId)
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
	
	public void storeTransactionToRepository(String date, Iban iban, int amount)
	{
		//transaction id is created automatically
		Transaction userTransaction = Transaction.builder()
												 .trans_date(date)
												 .iban(iban)
												 .amount(amount)
												 .build();
		
		transactionsRepo.save(userTransaction);
	}
	
}
