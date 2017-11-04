package hr.infobip.urlservice.accounts.services;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * A service class that represents the security context of the application.<br>
 * 
 * @author fiilip
 *
 */
@Service
public class AccountSecurityService {
	
	/**
	 * Gets the <b>username</b> from the current security context.<br>
	 * Ie. the username being set in the <i>Authorization</i> header of the request.
	 * 
	 * @return If the username is specified in the current security context, it's being returned, 
	 * otherwise <code>null</code> is returned.
	 */
	public String getUsernameFromContext() {
		Object loggedIn = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (!(loggedIn instanceof User)) {
			return null;
		}
		
		User loggedInUser = (User) loggedIn;
		
		return loggedInUser.getUsername();
	}
}
