package hr.infobip.urlservice.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import hr.infobip.urlservice.accounts.resources.AccountCreation;
import hr.infobip.urlservice.accounts.security.SecurityFilter;
import hr.infobip.urlservice.urls.resources.UrlRedirection;
import hr.infobip.urlservice.urls.resources.UrlRegistration;

@Configuration
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		registerEndpoints();
		registerSecurity();
	}

	private void registerSecurity() {
		register(SecurityFilter.class);		
	}

	private void registerEndpoints() {
		register(UrlRedirection.class);
		register(UrlRegistration.class);
		register(AccountCreation.class);
	}

}
