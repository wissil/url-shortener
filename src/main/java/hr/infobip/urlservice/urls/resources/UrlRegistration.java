package hr.infobip.urlservice.urls.resources;


import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import hr.infobip.urlservice.accounts.security.SecuredResource;
import hr.infobip.urlservice.accounts.security.SecurityFilter;
import hr.infobip.urlservice.urls.service.UrlRegistrationService;


/**
 * A controller that is used for the URL registration.
 * 
 * @author fiilip
 *
 */
@Path("/")
public class UrlRegistration implements SecuredResource {
	
	/**
	 * RegEx describing this resource.
	 */
	public static final String URL_REGEX = "register";
		
	/**
	 * Performs registration of given URLs
	 */
	private final UrlRegistrationService service;
	
	/**
	 * Performs the validation uf given URLs
	 */
	private final UrlValidator urlValidator;
		
	/**
	 * Creates a new instance of {@link UrlRegistration}.
	 * 
	 * @param urlValidator Performs the validation uf given URLs.
	 * @param service Performs registration of given URLs.
	 */
	@Autowired
	public UrlRegistration(UrlValidator urlValidator, UrlRegistrationService service) {
		this.urlValidator = Objects.requireNonNull(urlValidator);
		this.service = Objects.requireNonNull(service);
	}

	/**
	 * Registers a given <b>URL</b> from the request.
	 * 
	 * @param req Request object received.
	 * @return Http response object.
	 */
	@POST
	@Path(URL_REGEX)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUrl(@RequestBody Url url) {
		String longUrl = url.getUrl();
		//System.err.println(url);
		
		if (longUrl == null || !urlValidator.isValid(longUrl)) {
			// input url not valid
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new UrlRegisterFailResponse("Given URL not valid."))
					.build();
		} else {
			// input url valid
			String urlId = service.registerUrl(url);
			
			return Response
					.status(Status.CREATED)
					.entity(new UrlRegisterSuccessResponse(buildUrl(urlId)))
					.build();
		}
	}
	
	/**
	 * Builds an entire and accessible shortened URL.<br>
	 * A URL is built based on the given <code>urlId</code> and a current request context.
	 * 
	 * @param urlId Generated short URL Id.
	 * @return Built URL string.
	 */
	private static String buildUrl(String urlId) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		
		  // build URL from request
		 return String.format("%s://%s:%d/u/%s/",
				  request.getScheme(), 
				  request.getServerName(), 
				  request.getServerPort(),
				  urlId);
	}

	@Override
	public String getUrlRegex() {
		return URL_REGEX;
	}

	@Override
	public void registerToSecurityFilter() {
	}
}
