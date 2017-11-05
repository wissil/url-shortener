package hr.infobip.urlservice.urls.register.controllers;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import hr.infobip.urlservice.accounts.services.AccountSecurityService;
import hr.infobip.urlservice.urls.models.Url;
import hr.infobip.urlservice.urls.register.responses.UrlRegisterFailResponse;
import hr.infobip.urlservice.urls.register.responses.UrlRegisterResponse;
import hr.infobip.urlservice.urls.register.responses.UrlRegisterSuccessResponse;
import hr.infobip.urlservice.urls.register.services.UrlService;
import hr.infobip.urlservice.urls.stats.services.AccountStatsService;

/**
 * Controller used for <b>URL</b> registration.
 * 
 * @author fiilip
 *
 */
@RestController
public class UrlRegisterController {
	
	/**
	 * Service reponsible for <b>URL</b> management.
	 */
	private final UrlService urlService;
	
	/**
	 * Provides the security/authentication context.
	 */
	private final AccountSecurityService securityService;
	
	/**
	 * Service responsible for statistics management.
	 */
	private final AccountStatsService statsService;
	
	/**
	 * Validates the <b>URL</b>s.
	 */
	private final UrlValidator urlValidator;
	
	/**
	 * Creates a new instance of {@link UrlRegisterController}.
	 * 
	 * @param urlService Service reponsible for <b>URL</b> management.
	 * @param urlValidator Validates the <b>URL</b>s.
	 * @param securityService Provides the security/authentication context.
	 * @param statsService Service responsible for statistics management.
	 */
	@Autowired
	public UrlRegisterController(UrlService urlService, UrlValidator urlValidator,
			AccountSecurityService securityService, AccountStatsService statsService) {
		this.urlService = Objects.requireNonNull(urlService);
		this.urlValidator = Objects.requireNonNull(urlValidator);
		this.securityService = Objects.requireNonNull(securityService);
		this.statsService = Objects.requireNonNull(statsService);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/register", produces = "application/json")
	public ResponseEntity<UrlRegisterResponse> registerUrl(@RequestBody Url url) {
		if (!isValidRequestBody(url)) {
			// illegal request body
			return new ResponseEntity<>(
					new UrlRegisterFailResponse("Illegal request body."),
					HttpStatus.BAD_REQUEST);
		}	
		
		if (!isValidUrl(url.getUrl())) {
			// url not in a valid format
			return new ResponseEntity<>(
					new UrlRegisterFailResponse("Url provided not in a valid format."),
					HttpStatus.BAD_REQUEST);
		}		
		
		// legal url
		String accountId = securityService.getUsernameFromContext();
		
		// register
		String urlId = urlService.registerUrl(url);	
		
		// register stats
		statsService.registerUrlToAccount(url, accountId);
				
		return new ResponseEntity<>(
				new UrlRegisterSuccessResponse(buildUrl(urlId)),
				HttpStatus.CREATED);
	}

	/**
	 * Checks whether the given <code>longUrl</code> is in the valid format.
	 * 
	 * @param longUrl Long url.
	 * @return <code>true</code> if the given <code>longUrl</code> is valid,
	 * and <code>false</code> otherwise.
	 */
	private boolean isValidUrl(String longUrl) {
		return longUrl != null && urlValidator.isValid(longUrl);
	}

	/**
	 * Checks whether the given request body in the form of given <code>url</code>
	 * is valid.
	 * 
	 * @param url Url object received as the request body.
	 * @return <code>true</code> if the given <code>url</code> received as the request body is valid,
	 * and <code>false</code> otherwise.
	 */
	private boolean isValidRequestBody(Url url) {
		return url != null && url.getUrl() != null;
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
}
