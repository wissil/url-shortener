package hr.infobip.urlservice.config;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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
	 * Provides details for the accounts stored in this service.
	 */
    private final AccountDetailsService accountDetailsService;
    
    /**
     * Encodes passwords to hashes.
     */
    private final PasswordEncoder passwordEncoder;
	
    /**
     * Creates a new instance of {@link SecurityConfig}.
     * 
     * @param accountDetailsService Service whose role is to provide detilas for the accounts
     * stored in this service.
     * @param passwordEncoder Encodes passwords to only store hashes in memory.
     */
    @Autowired
	public SecurityConfig(AccountDetailsService accountDetailsService, PasswordEncoder passwordEncoder) {
		this.accountDetailsService = Objects.requireNonNull(accountDetailsService);
		this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountDetailsService).passwordEncoder(passwordEncoder);
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
			.antMatchers(HttpMethod.GET, "/help").permitAll() // public endpoint
			.antMatchers(HttpMethod.GET, "/").permitAll() // public endpoint
			.anyRequest().authenticated() // any other endpoint authenticated
			.and()
			.httpBasic();
	}
}
