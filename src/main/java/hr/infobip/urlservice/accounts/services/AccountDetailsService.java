package hr.infobip.urlservice.accounts.services;

import java.util.Collections;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hr.infobip.urlservice.accounts.model.Account;
import hr.infobip.urlservice.accounts.repositories.AccountRepository;


/**
 * A class that represents a service that obtains the account details.
 * 
 * @author fiilip
 *
 */
@Service
public class AccountDetailsService implements UserDetailsService {
	
	/**
	 * Instance of a repository of the {@link Account} objects.
	 */
	private final AccountRepository repository;
	
	/**
	 * Creates a new instance of the {@link AccountDetailsService}.
	 * 
	 * @param repository Repository of the {@link Account} objects.
	 */
	@Autowired
	public AccountDetailsService(AccountRepository repository) {
		this.repository = Objects.requireNonNull(repository);
	}

	@Override
	public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
	    Account account = repository.findById(accountId);
	    
	    return new User(account.getAccountId(), account.getPasswordHash(), Collections.emptySet());
	}

}
