package hr.infobip.urlservice.accounts.resources;

public class AccountCreatedFailResponse {
	
	private final String message;

	public AccountCreatedFailResponse(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
