package hr.infobip.urlservice.urls.resources;

public class UrlRegisterFailResponse {
	
	private final String message;
	
	public UrlRegisterFailResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
