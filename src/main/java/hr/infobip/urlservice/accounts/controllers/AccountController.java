package hr.infobip.urlservice.accounts.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.infobip.urlservice.accounts.requests.AccountCreateRequest;
import hr.infobip.urlservice.accounts.responses.AccountCreateInvalidRequestResponse;
import hr.infobip.urlservice.accounts.responses.AccountCreateResponse;
import hr.infobip.urlservice.accounts.responses.AccountCreateValidRequestResponse;
import hr.infobip.urlservice.accounts.services.AccountService;
import hr.infobip.urlservice.util.PasswordGenerator;

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
	 * Generates a password string.
	 */
	private final PasswordGenerator passwordGenerator;
	
	/**
	 * Encodes a password.
	 */
	private final PasswordEncoder passwordEncoder;
	
	/**
	 * Creates a new instance of {@link AccountController}.
	 * 
	 * @param service Service responsible for user accounts manegement.
	 */
	@Autowired
	public AccountController(AccountService service, PasswordGenerator passwordGenerator, 
			PasswordEncoder passwordEncoder) {
		this.service = Objects.requireNonNull(service);
		this.passwordGenerator = Objects.requireNonNull(passwordGenerator);
		this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/account", produces = "application/json")
	public ResponseEntity<AccountCreateResponse> create(@RequestBody AccountCreateRequest request) {
		String accountId = request.getAccountId();
				
		if (!providedAccountId(accountId)) {
			// bad request: accountId not provided
			return new ResponseEntity<>(
					new AccountCreateInvalidRequestResponse("Account id is not provided."),
					HttpStatus.BAD_REQUEST);
		}
				
		// accountId provided
		if (service.exists(accountId)) {
			// account already exists
			final boolean fail = false;
			
			return new ResponseEntity<>(
					new AccountCreateValidRequestResponse(fail, null, "Account with the requested Id already exists."),
					HttpStatus.BAD_REQUEST);
			
		} else {
			// account doesn't already exist --> save it!
			String password = passwordGenerator.generate();
			String encoded = passwordEncoder.encode(password);
			service.save(accountId, encoded);
			
			final boolean success = true;
			
			return new ResponseEntity<>(
					new AccountCreateValidRequestResponse(success, password, "Your account is created."),
					HttpStatus.CREATED);
		}
	}

	/**
	 * Evaluates whether the given <code>accountId</code> is in the valid format.
	 * 
	 * @param accountId Account Id of interest.
	 * @return <code>true</code> if given <code>accountId</code> in a valid format,
	 * and <code>false</code> otherwise.
	 */
	private static boolean providedAccountId(String accountId) {
		return accountId != null && !accountId.isEmpty();
	}
}
