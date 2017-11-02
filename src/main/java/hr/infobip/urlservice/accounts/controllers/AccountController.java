package hr.infobip.urlservice.accounts.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.infobip.urlservice.accounts.requests.AccountCreateRequest;
import hr.infobip.urlservice.accounts.responses.AccountCreateResponse;
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
	public ResponseEntity<AccountCreateResponse> create(@RequestBody AccountCreateRequest request) {
		String accountId = request.getAccountId();
		
		
		if (!isLegalAccountId(accountId)) {
			// bad request: accountId not provided
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		System.err.println(accountId);
		
		// accountId provided
		if (service.exists(accountId)) {
			// account already exists
			final boolean fail = false;
			
			return new ResponseEntity<AccountCreateResponse>(
					new AccountCreateResponse(fail, null),
					HttpStatus.BAD_REQUEST);
		} else {
			// account doesn't already exist --> save it!
			String password = service.generatePassword();
			System.err.println(password);
			service.save(accountId, password);
			
			final boolean success = true;
			
			return new ResponseEntity<AccountCreateResponse>(
					new AccountCreateResponse(success, password),
					HttpStatus.CREATED);
		}
	}

	private static boolean isLegalAccountId(String accountId) {
		return accountId != null && !accountId.isEmpty();
	}
}
