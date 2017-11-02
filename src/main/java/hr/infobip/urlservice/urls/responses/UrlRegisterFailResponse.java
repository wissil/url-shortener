package hr.infobip.urlservice.urls.responses;

import java.util.Objects;

public class UrlRegisterFailResponse implements UrlRegisterResponse {
	
	private final String message;
	
	public UrlRegisterFailResponse(String message) {
		this.message = Objects.requireNonNull(message);
	}

	public String getMessage() {
		return message;
	}
}
