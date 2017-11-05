package hr.infobip.urlservice.accounts.responses;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class representing a service response to the invalid request of creating a new user account.
 * 
 * @author fiilip
 *
 */
public class AccountCreateInvalidRequestResponse implements AccountCreateResponse {

	/**
	 * Error message.
	 */
	private final String message;
	
	/**
	 * Creates a new instance of {@link AccountCreateInvalidRequestResponse}.
	 * 
	 * @param message Message of this response.
	 */
	public AccountCreateInvalidRequestResponse(String message) {
		this.message = Objects.requireNonNull(message);
	}
	
	/**
	 * Gets the message of this response.
	 * 
	 * @return Message of this reponse.
	 */
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}
}
