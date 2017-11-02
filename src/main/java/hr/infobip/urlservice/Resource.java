package hr.infobip.urlservice;

/**
 * An interface that represents any resource (or endpoint) of this service.
 * 
 * @author fiilip
 *
 */
public interface Resource {
	
	/**
	 * Gets a regular expression this describes the <i>URL</i> of this {@link Resource}.
	 * 
	 * @return RegEx describing this resource.
	 */
	String getUrlRegex();

}
