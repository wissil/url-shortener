package hr.infobip.urlservice.accounts.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class representing the request to create a new user account.
 * 
 * @author fiilip
 *
 */
public class AccountCreateRequest {

	/**
	 * Account Id.
	 */
	private final String accountId;
	
	/**
	 * Default constructor.
	 */
	// needed to parse JSON
	public AccountCreateRequest() {
		this(null);
	}
	
	/**
	 * Creates a new instance of {@link AccountCreateRequest}.
	 * 
	 * @param accountId Account Id.
	 */
	public AccountCreateRequest(String accountId) {
		this.accountId = accountId;
	}
	
	/**
	 * Gets the account Id from this request.
	 * 
	 * @return Account Id.
	 */
	@JsonProperty("accountId")
	public String getAccountId() {
		return accountId;
	}
}
