package hr.infobip.urlservice.urls;

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

import hr.infobip.urlservice.urls.responses.UrlRegisterFailResponse;
import hr.infobip.urlservice.urls.responses.UrlRegisterResponse;
import hr.infobip.urlservice.urls.responses.UrlRegisterSuccessResponse;

@RestController
public class UrlRegisterController {
	
	private final UrlRegisterService registerService;
	
	private final UrlValidator urlValidator;
	
	@Autowired
	public UrlRegisterController(UrlRegisterService registerService, 
			UrlValidator urlValidator) {
		this.registerService = registerService;
		this.urlValidator = urlValidator;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/register", produces = "application/json")
	public ResponseEntity<UrlRegisterResponse> registerUrl(@RequestBody Url url) {
		if (!isLegalRequestBody(url)) {
			// illegal request body
			return new ResponseEntity<>(
					new UrlRegisterFailResponse("Illegal request body."),
					HttpStatus.BAD_REQUEST);
		}
		
		// legal request body
		String longUrl = url.getUrl();
		if (!isLegalUrl(longUrl)) {
			return new ResponseEntity<>(
					new UrlRegisterFailResponse("Url provided not in a valid format."),
					HttpStatus.BAD_REQUEST);
		}
		
		// legal url
		String urlId = registerService.registerUrl(url);
		
		return new ResponseEntity<>(
				new UrlRegisterSuccessResponse(buildUrl(urlId)),
				url.getHttpStatus());
	}

	private boolean isLegalUrl(String longUrl) {
		return longUrl != null && urlValidator.isValid(longUrl);
	}

	private boolean isLegalRequestBody(Url url) {
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