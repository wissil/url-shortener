package hr.infobip.urlservice.urls.redirect;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.infobip.urlservice.urls.models.Url;
import hr.infobip.urlservice.urls.register.services.UrlService;
import hr.infobip.urlservice.urls.stats.services.AccountStatsService;

/**
 * Controller responsible for redirecting given short <b>URL</b>s 
 * to the registered associated <i>long</i> <b>URL</b>s.
 * 
 * @author fiilip
 *
 */
@RestController
public class UrlRedirectController {
	
	/**
	 * Service responsible for <b>URL</b> management.
	 */
	private final UrlService urlService;
	
	/**
	 * Service responsible for statistics management.
	 */
	private final AccountStatsService statsService;
	
	/**
	 * Creates a new instance of {@link UrlRedirectController}.
	 * 
	 * @param urlService Service responsible for <b>URL</b> management.
	 * @param statsService Service responsible for statistics management.
	 */
	@Autowired
	public UrlRedirectController(UrlService urlService, AccountStatsService statsService) {
		this.urlService = Objects.requireNonNull(urlService);
		this.statsService = Objects.requireNonNull(statsService);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/u/{urlId}")
	public void redirect(@PathVariable String urlId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!urlService.urlExists(urlId)) {
			response.sendError(
					HttpStatus.UNPROCESSABLE_ENTITY.value(), 
					"Requested URL not registered");
			return;
		}
		
		// obtain long url from url id
		Url url = urlService.getUrl(urlId);
		
		// update statistics
		statsService.incrementHit(url);
		
		// redirect
        response.setStatus(url.getRedirectType());
        response.setHeader("Location", url.getUrl());
        response.setHeader("Connection", "close");
	}
}
