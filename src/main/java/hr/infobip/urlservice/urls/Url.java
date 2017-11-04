package hr.infobip.urlservice.urls;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Url {
	
	private static final int MOVED_PERMANENTLY = HttpStatus.MOVED_PERMANENTLY.value();
	
	private static final int FOUND = HttpStatus.FOUND.value();
	
	private final String url;
	
	private final int redirectType;
	
	private int numberOfHits;
	
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
		this.numberOfHits = 0;
	}
	
	@JsonProperty("url")
	public String getUrl() {
		return url;
	}
	
	@JsonProperty("redirectType")
	public int getRedirectType() {
		return redirectType;
	}
	
	@JsonIgnore
	public HttpStatus getHttpStatus() {
		if (redirectType == MOVED_PERMANENTLY) {
			return HttpStatus.MOVED_PERMANENTLY;
		} else {
			return HttpStatus.FOUND;
		}
	}
	
	@JsonIgnore
	public int getNumberOfHits() {
		return numberOfHits;
	}
	
	@JsonIgnore
	public void incrementNumberOfHits() {
		numberOfHits ++;
	}
}
