package hr.infobip.urlservice.urls.stats;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.infobip.urlservice.urls.Url;
import hr.infobip.urlservice.urls.responses.UrlRegisterFailResponse;
import hr.infobip.urlservice.urls.responses.UrlRegisterResponse;
import hr.infobip.urlservice.urls.services.UrlRegisterService;

@RestController
public class StatsController {
	
	private final UrlRegisterService urlService;
	
	public StatsController(UrlRegisterService urlService) {
		this.urlService = Objects.requireNonNull(urlService);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/statistic/{accountId}", produces = "application/json")
	public ResponseEntity<Map<String, Integer>> getStats(@PathVariable String accountId) {
		System.err.println("Acc Id: " + accountId);
//		if (!isLegalRequestBody(url)) {
//			// illegal request body
//			return new ResponseEntity<>(
//					new UrlRegisterFailResponse("Illegal request body."),
//					HttpStatus.BAD_REQUEST);
//		}		
		
		if (!urlService.accountExists(accountId)) {
			System.err.println("NOT EXISTS");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		System.err.println("EXISTS");
		return new ResponseEntity<>(
				urlService.getStats(accountId).stream().collect(Collectors.toMap(s->s.getUrl(), s->s.getNumberOfHits())),
				HttpStatus.OK);
	}
}
