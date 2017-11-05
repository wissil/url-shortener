package hr.infobip.urlservice.urls.stats.responses;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class that represents a response to the statistics request that is not valid.
 * 
 * @author fiilip
 *
 */
public class InvalidStatsRequestResponse implements StatsResponse {
	
	/**
	 * Message of this response.
	 */
	private final String message;
	
	/**
	 * Creates a new instance of {@link InvalidStatsRequestResponse}.
	 * 
	 * @param message Message of this response.
	 */
	public InvalidStatsRequestResponse(String message) {
		this.message = Objects.requireNonNull(message);
	}

	/**
	 * Gets the message of this response.
	 * 
	 * @return Message of this response.
	 */
	@JsonProperty("message")
	public String getMessage() {
		return message;
	}
}
