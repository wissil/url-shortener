package hr.infobip.urlservice.config;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlRegistrationConfig {
	
	/**
	 * Url schemes that represent a valid <b>URL</b>.
	 */
	private static final String[] urlSchemes = new String[] {"http", "https"};
	
	/**
	 * Creates a new {@link UrlValidator} bean.
	 * 
	 * @return New instance of  {@link UrlValidator}.
	 */
	@Bean
	public UrlValidator urlValidator() {
		return new UrlValidator(urlSchemes);
	}
}
