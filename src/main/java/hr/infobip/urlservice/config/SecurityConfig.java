package hr.infobip.urlservice.config;

import java.util.Objects;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import hr.infobip.urlservice.accounts.services.AccountDetailsService;

/**
 * Security configuration of this service.
 * 
 * @author fiilip
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * Generates a password encoder bean.
	 * 
	 * @return Password encoder instance.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UrlValidator urlValidator() {
		return new UrlValidator(new String[]{"http", "https"});
	}
	
    private final AccountDetailsService accountDetailsService;
	
    @Autowired
	public SecurityConfig(AccountDetailsService accountDetailsService) {
		this.accountDetailsService = Objects.requireNonNull(accountDetailsService);
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    		System.err.println("???????");
        auth.userDetailsService(accountDetailsService).passwordEncoder(passwordEncoder());
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/account").permitAll() // public endpoint
			.antMatchers(HttpMethod.GET, "/u/**").permitAll() // public endpoint
			.anyRequest().authenticated() // any other endpoint authenticated
			.and()
			.httpBasic();
	}
}
