package hr.infobip.urlservice.accounts.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import hr.infobip.urlservice.accounts.model.Account;

/**
 * A class representing the repository of {@link Account} objects.<br>
 * A repository has the direct communication to the physical data storage, ie. database.
 * 
 * @author fiilip
 *
 */
@Repository
public class AccountRepository {
	
	/**
	 * accountId --> account
	 */
	private final Map<String, Account> accounts;
	
	/**
	 * Creates a new instance of {@link AccountRepository}.
	 */
	public AccountRepository() {
		this.accounts = new HashMap<>();
	}

	/**
	 * Find the {@link Account} object based on a given <code>accountId</code>.
	 * 
	 * @param accountId Id of the account being searched for.
	 * @return {@link Account} object with a given <code>accountId</code> if such exists,
	 * and <code>null</code> otherwise.
	 */
	public Account findById(String accountId) {
		return accounts.get(accountId);
	}

	/**
	 * Deletes the account with a given <code>accountId</code> from this repository.
	 * 
	 * @param accountId Id of the account to be deleted.
	 */
	public void delete(String accountId) {
		accounts.remove(accountId);
	}

	/**
	 * Checks whether the account with the given <code>accountId</code> exists in this repository.
	 * 
	 * @param accountId Id of the account being checked for existence.
	 * @return <code>true</code> if the account with a given <code>accountId</code> is in this repository,
	 * and <code>false</code> otherwise.
	 */
	public boolean exists(String accountId) {
		return accounts.containsKey(accountId);
	}

	/**
	 * Saves a given <code>account</code> to this repository.<br>
	 * If the given <code>account</code> already exists in this repository, no further 
	 * changes will be made by calling this method.
	 * 
	 * @param account Account to be saved.
	 */
	public void save(Account account) {		
		accounts.put(account.getAccountId(), account);
	}
}
