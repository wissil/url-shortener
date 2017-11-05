package hr.infobip.urlservice.urls.models;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class representing a single <i>long</i> <b>URL</b>.
 * 
 * @author fiilip
 *
 */
@JsonInclude(Include.NON_NULL)
public class Url {	
	
	/**
	 * The actual <i>URL</i> textual representation.
	 */
	private final String url;
	
	/**
	 * Redirect type of this <b>URL</b>.
	 */
	private final int redirectType;
	
	/**
	 * Default constructor.
	 */
	public Url() {
		this(null);
	}

	/**
	 * Creates a new instance of {@link Url}.
	 * 
	 * @param url Textual representation of the <b>URL</b>.
	 */
	public Url(String url) {
		this(url, HttpStatus.FOUND.value());
	}
	
	/**
	 * Creates a new instance of {@link Url}.
	 * 
	 * @param url Textual representation of the <b>URL</b>.
	 * @param redirectType Integer value representing the redirect type.
	 */
	public Url(String url, int redirectType) {
		this.url = url;
		this.redirectType = redirectType;
	}
	
	/**
	 * Gets the textual representation of this <b>URL</b>.
	 * 
	 * @return Textual representation of this <b>URL</b>.
	 */
	@JsonProperty("url")
	public String getUrl() {
		return url;
	}
	
	/**
	 * Gets the integer value representing the redirect type of this <b>URL</b>.
	 * 
	 * @return Integer value representing the redirect type of this <b>URL</b>
	 */
	@JsonProperty("redirectType")
	public int getRedirectType() {
		return redirectType;
	}
	
	/**
	 * Gets the redirect type in form of {@link HttpStatus}.
	 * 
	 * @return Redirect type in the form of {@link HttpStatus}.
	 */
	@JsonIgnore
	public HttpStatus getRedirectTypeHttpStatus() {
		return parseTypeToHttpStatus();
	}
	
	/**
	 * Parses the integer value of redirect type to {@link HttpStatus} object.
	 * 
	 * @return Redirect type in the form of {@link HttpStatus}.
	 */
	private HttpStatus parseTypeToHttpStatus() {
		return redirectType == HttpStatus.MOVED_PERMANENTLY.value() ?
				HttpStatus.MOVED_PERMANENTLY :
				HttpStatus.FOUND;
	}
}
