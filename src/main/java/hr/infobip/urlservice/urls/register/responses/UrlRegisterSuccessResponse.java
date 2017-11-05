package hr.infobip.urlservice.urls.register.responses;

import java.util.Objects;

public class UrlRegisterSuccessResponse implements UrlRegisterResponse {

	private final String shortUrl;
	
	public UrlRegisterSuccessResponse(String shortUrl) {
		this.shortUrl = Objects.requireNonNull(shortUrl);
	}
	
	public String getShortUrl() {
		return shortUrl;
	}
}
