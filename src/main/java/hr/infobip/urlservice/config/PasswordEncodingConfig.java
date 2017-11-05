package hr.infobip.urlservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * A class that provides the configuration for password encoding.<br>
 * The implementation of the concrete password encoder should be provided here.
 * 
 * @author fiilip
 *
 */
@Configuration
public class PasswordEncodingConfig {

	/**
	 * Gets a password encoder bean.
	 * 
	 * @return Password encoder instance.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
