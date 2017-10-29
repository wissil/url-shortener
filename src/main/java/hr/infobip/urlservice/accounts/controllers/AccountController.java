package hr.infobip.urlservice.accounts.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.infobip.urlservice.accounts.controllers.responses.AccountCreatedResponse;
import hr.infobip.urlservice.accounts.services.AccountService;

/**
 * A controller that is responsible for user accounts management.
 * 
 * @author fiilip
 *
 */
@RestController
public class AccountController {
	
	/**
	 * Underlying service taking care of account management.
	 */
	private final AccountService service;
	
	/**
	 * Creates a new instance of {@link AccountController}.
	 * 
	 * @param service Service responsible for user accounts manegement.
	 */
	@Autowired
	public AccountController(AccountService service) {
		this.service = Objects.requireNonNull(service);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/account", produces = "application/json")
	public AccountCreatedResponse create(@RequestBody String accountId) {
		if (service.exists(accountId)) {
			// account already exists
			final boolean fail = false;
			return new AccountCreatedResponse(fail, null);
		} else {
			// account doesn't already exist --> save it!
			String password = service.generatePassword();
			service.save(accountId, password);
			
			final boolean success = true;
			return new AccountCreatedResponse(success, password);
		}
	}
}
