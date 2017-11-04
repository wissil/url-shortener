package hr.infobip.urlservice.accounts.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.infobip.urlservice.accounts.model.Account;
import hr.infobip.urlservice.accounts.repositories.AccountRepository;

/**
 * A service that abstracts the operations on the {@link Account} objects.<br>
 * This is where the business logic on {@link Account} objects takes place.
 * 
 * @author fiilip
 *
 */
@Service
public class AccountService {
	
	/**
	 * Repository of the registered accounts.
	 */
	private final AccountRepository repository;

	/**
	 * Creates a new instance of {@link AccountService}.
	 * 
	 * @param repository Repository of the registered accounts.
	 */
	@Autowired
	public AccountService(AccountRepository repository) {
		this.repository = Objects.requireNonNull(repository);
	}
	
	/**
	 * Saves the account with the given <code>accountId</code>.<br>
	 * If the account with a given <code>accountId</code> already exists,
	 * no further changes will be made by calling this method.
	 * 
	 * @param accountId Id of the account to be saved.
	 * @param encoded Encoded password of the account to be saved.
	 */
	public void save(String accountId, String encoded) {
		// only store password hashes to the memory
		repository.save(new Account(accountId, encoded));
	}
	
	/**
	 * Checks whether the account with a given <code>accountId</code> already exists.
	 * 
	 * @param accountId Id of the account being checked for.
	 * @return <code>true</code> if the account with a given <code>accountId</code> already exists,
	 * and <code>false</code> otherwise.
	 */
	public boolean exists(String accountId) {
		return repository.exists(accountId);
	}
}
