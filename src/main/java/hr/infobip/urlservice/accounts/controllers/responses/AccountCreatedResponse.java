package hr.infobip.urlservice.accounts.controllers.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A class representing a service response to the request of creating a new user account.
 * 
 * @author fiilip
 *
 */
@JsonInclude(Include.NON_NULL)
public class AccountCreatedResponse {
	
	/**
	 * Successful account creation message.
	 */
	private static final String ACC_CREATED = "Your account is created.";
	
	/**
	 * Failed account creation message.
	 */
	private static final String ACC_NOT_CREATED = "Account with the requested Id already exists.";

	/**
	 * Whether the requested account is created or not.
	 */
	private final boolean success;
	
	/**
	 * Description of the under-taken action.
	 */
	private final String description;
	
	/**
	 * Password generated for the given request.
	 */
	private final String password;

	/**
	 * Creates a new instance of {@link AccountCreatedResponse}.
	 * 
	 * @param success Whether or not the requested account was successfully created.
	 * @param password Generated password for the requested account.<br>
	 * <code>null</code> if no password was generated.
	 */
	public AccountCreatedResponse(boolean success, String password) {
		this.success = success;
		this.password = password;
		this.description = success ? ACC_CREATED : ACC_NOT_CREATED;
	}

	/**
	 * Checks whether the requested account was successfully created.
	 * 
	 * @return <code>true</code> if the request is fulfilled, and <code>false</code> otherwise.
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Gets the description message describing the result of the account creation attempt.
	 * 
	 * @return Description of the requested account creation attempt.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the password generated for the requested account.
	 * 
	 * @return Password generated for the requested account, and <code>null</code> if
	 * no password was generated.
	 */
	public String getPassword() {
		return password;
	}
}
