package hr.infobip.urlservice.urls.resources;


public class UrlRegisterSuccessResponse {

	private final String shortUrl;

	public UrlRegisterSuccessResponse(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}
}
