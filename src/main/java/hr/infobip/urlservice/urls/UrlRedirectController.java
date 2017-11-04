package hr.infobip.urlservice.urls;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.infobip.urlservice.urls.services.UrlRegisterService;

@RestController
public class UrlRedirectController {
	
	private final UrlRegisterService urlService;
	
	@Autowired
	public UrlRedirectController(UrlRegisterService urlService) {
		this.urlService = Objects.requireNonNull(urlService);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/u/{urlId}")
	public void redirect(@PathVariable String urlId, HttpServletRequest request, HttpServletResponse response) {
		System.err.println("Url ID: " + urlId);
		if (!urlService.urlExists(urlId)) {
			// TODO error management
			return;
		}
		
		Url url = urlService.getUrl(urlId);
		
		// increment to Url service directly :: this doesn't work
		url.incrementNumberOfHits();
		
		String newUrl = url.getUrl();
        response.setStatus(url.getRedirectType());
        response.setHeader("Location", newUrl);
        response.setHeader("Connection", "close");
	}

}
