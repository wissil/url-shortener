package hr.infobip.urlservice.config;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A class that provides the configuration for what format of <b>URL</b>s
 * are considered valid.
 * 
 * @author fiilip
 *
 */
@Configuration
public class UrlValidationConfig {
	
	/**
	 * Supported <b>URL</b> protocols.
	 */
	private static final String[] VALID_PROTOCOLS = new String[] {"http", "https"};

	/**
	 * Gets the URL validator bean.
	 * 
	 * @return Instance of {@link UrlValidator}.
	 */
	@Bean
	public UrlValidator urlValidator() {
		return new UrlValidator(VALID_PROTOCOLS);
	}
}
