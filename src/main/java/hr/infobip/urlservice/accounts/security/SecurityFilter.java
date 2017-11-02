package hr.infobip.urlservice.accounts.security;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import com.sun.research.ws.wadl.Resources;

import hr.infobip.urlservice.accounts.repositories.AccountRepository;
import hr.infobip.urlservice.urls.resources.UrlRegistration;

@Provider
public class SecurityFilter implements ContainerRequestFilter {
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	
	private final Map<String, SecuredResource> resources;
		
	private static final String SECURED_URL_PREFIX = UrlRegistration.URL_REGEX;
	
	/**
	 * Repository of account objects.
	 */
	private final AccountRepository repository;
	
	/**
	 * Creates a new instance of {@link SecurityFilter}.
	 * 
	 * @param repository Repository of user accounts.
	 */
	@Autowired
	public SecurityFilter(AccountRepository repository) {
		this.repository = Objects.requireNonNull(repository);
		this.resources = new HashMap<>();
	}

	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		// pass through the filter if no auth required
		if (!requiresAuthorization(context)) return;
		
		// apply the auth filter
		applyFilter(context);
	}
	
	/**
	 * Applies the authenthication filter to the given request <code>context</code>.
	 * 
	 * @param context Context of interest.
	 */
	private void applyFilter(ContainerRequestContext context) {
		List<String>authHeaders = context.getHeaders().get(AUTHORIZATION_HEADER);
		
		// no authenthication header present --> error
		if (authHeaders == null || authHeaders.size() == 0) {
			context.abortWith(Response
					.status(Status.UNAUTHORIZED)
					.entity(String.format("No authenthication provided."))
					.build());
			return;
		}
		
		// authenthication header is present --> apply the filter
		String authToken = authHeaders.get(0);
		authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");

		String decoded = new String(Base64.getDecoder().decode(authToken));
		String[] tokens = decoded.split(":");
		
		String accountId = tokens[0];
		String password = tokens[1];

		// abort if credentials invalid
		if (!validCredentials(accountId, password)) {
			context.abortWith(Response
					.status(Status.UNAUTHORIZED)
					.entity(String.format("Invalid accountId or password for user %s.", accountId))
					.build());
		}
	}
	
	/**
	 * Checks whether or not the given request <code>context</code> requires authorization
	 * or not.
	 * 
	 * @param context Context of interest.
	 * @return <code>true</code> if the <code>context</code> requires authorization,
	 * and <code>false</code> otherwise.
	 */
	private boolean requiresAuthorization(ContainerRequestContext context) {
		
		return context.getUriInfo().getPath().startsWith(SECURED_URL_PREFIX);
	}
	
	/**
	 * Checks whether or not the user given credentials are valid.
	 *  
	 * @param accountId Account Id.
	 * @param password Password.
	 * @return <code>true</code> if the credentials are valid, and <code>false</code> otherwise.
	 */
	private boolean validCredentials(String accountId, String password) {
		// user not in the repository?
		if (!repository.contains(accountId)) return false;
				
		return repository.findById(accountId).getPasswordHash().equals(password);
	}
	
	public void registerResource(SecuredResource resource) {
		this.resources.put(resource.getUrlRegex(), resource);
	}
}
