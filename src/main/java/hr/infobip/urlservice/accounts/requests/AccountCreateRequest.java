package hr.infobip.urlservice.accounts.requests;

public class AccountCreateRequest {

	private final String accountId;
	
	/**
	 * Default constructor.
	 */
	public AccountCreateRequest() {
		this.accountId = null;
	}
	
	public AccountCreateRequest(String accountId) {
		this.accountId = accountId;
	}
	
	public String getAccountId() {
		return accountId;
	}
}
