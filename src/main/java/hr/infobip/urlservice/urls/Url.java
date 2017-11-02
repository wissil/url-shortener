package hr.infobip.urlservice.urls;

import org.springframework.http.HttpStatus;

public class Url {
	
	private static final int MOVED_PERMANENTLY = HttpStatus.MOVED_PERMANENTLY.value();
	
	private static final int FOUND = HttpStatus.FOUND.value();
	
	private final String url;
	
	private final int redirectType;
	
	/**
	 * Default constructor.
	 */
	public Url() {
		this(null, 0);
	}

	public Url(String url) {
		this(url, 0);
	}
	
	public Url(String url, int redirectType) {
		this.url = url;
		this.redirectType = 
				redirectType == MOVED_PERMANENTLY ?
				MOVED_PERMANENTLY :
				FOUND;
	}
	
	public String getUrl() {
		return url;
	}
	
	public int getRedirectType() {
		return redirectType;
	}
	
	public HttpStatus getHttpStatus() {
		if (redirectType == MOVED_PERMANENTLY) {
			return HttpStatus.MOVED_PERMANENTLY;
		} else {
			return HttpStatus.FOUND;
		}
	}
}
