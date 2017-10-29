package hr.infobip.urlservice.accounts.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import hr.infobip.urlservice.accounts.model.Account;

/**
 * A class that represents the security aspect of this application.<br>
 * In terms, it provides the authentication and authorization data of the logged in account.
 * 
 * @author fiilip
 *
 */
@Service
public class AccountSecurityService {
	
	/**
	 * Authentication manager of this service.
	 */
	private final AuthenticationManager authManager;
	
	/**
	 * Service that provides the account details.
	 */
	private final AccountDetailsService accDetailsService;
	
	/**
	 * Creates new instance of {@link AccountSecurityService}.
	 * 
	 * @param authManager Authentication manager.
	 * @param accDetailsService Provides details of the accounts.
	 */
	@Autowired
	public AccountSecurityService(AuthenticationManager authManager, AccountDetailsService accDetailsService) {
		this.authManager = Objects.requireNonNull(authManager);
		this.accDetailsService = Objects.requireNonNull(accDetailsService);
	}
	
	/**
	 * Gets the {@link Account} currently logged-in.
	 * 
	 * @return If there is currently logged-in account, it's being returned, otherwise 
	 * <code>null</code> is returned.
	 */
	public Account getLoggedInAccount() {
		Object loggedIn = SecurityContextHolder.getContext().getAuthentication().getDetails();
		
		if (!(loggedIn instanceof Account)) {
			return null;
		}
		
		return (Account) loggedIn;
	}

	/**
	 * Performs the log-in of the account with <code>accountId</code> and
	 * <code>password</code>.
	 * 
	 * @param accountId Id of the account being logged-in.
	 * @param password Password of the account being logged-in.
	 */
	public void login(String accountId, String password) {
		UserDetails accDetails = accDetailsService.loadUserByUsername(accountId);
		
		UsernamePasswordAuthenticationToken token = 
				new UsernamePasswordAuthenticationToken(accDetails, password, accDetails.getAuthorities());
		
		// authenticate token with the given account details
		authManager.authenticate(token);
		
		if (token.isAuthenticated()) {
			// provide authentication info to the context
			SecurityContextHolder.getContext().setAuthentication(token);
		}
	}
}
