package hr.infobip.urlservice.urls.stats.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.infobip.urlservice.accounts.services.AccountService;
import hr.infobip.urlservice.urls.stats.responses.InvalidStatsRequestResponse;
import hr.infobip.urlservice.urls.stats.responses.StatsResponse;
import hr.infobip.urlservice.urls.stats.responses.ValidStatsRequestRespose;
import hr.infobip.urlservice.urls.stats.services.AccountStatsService;

/**
 * A controller that provides the statistics for <b>URL</b> hits by
 * registered accounts.<br>
 * 
 * Each registered account can register it's own <b>URL</b> and track the statistics
 * on how many times the registered <b>URL</b> was hit.
 * 
 * @author fiilip
 *
 */
@RestController
public class StatsController {
		
	/**
	 * Service responsible for the statistics management.
	 */
	private final AccountStatsService statsService;
	
	/**
	 * Service responsible for the accounts management.
	 */
	private final AccountService accountService;
	
	/**
	 * Creates a new instance of {@link StatsController}.
	 * 
	 * @param statsService Service responsible for the statistics management.
	 * @param accountService Service responsible for the accounts management.
	 */
	@Autowired
	public StatsController(AccountStatsService statsService, AccountService accountService) {
		this.statsService = Objects.requireNonNull(statsService);
		this.accountService = Objects.requireNonNull(accountService);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/statistic/{accountId}", produces = "application/json")
	public ResponseEntity<StatsResponse> getStats(@PathVariable String accountId) {
		if (!providedAccountId(accountId)) {
			return new ResponseEntity<>(
					new InvalidStatsRequestResponse("Request does not provide the account Id."),
					HttpStatus.BAD_REQUEST);
		}
		
		if (!accountService.containsAccount(accountId)) {
			return new ResponseEntity<>(
					new InvalidStatsRequestResponse(String.format("%s is not a registered account.", accountId)),
					HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(
				new ValidStatsRequestRespose(statsService.generateStats(accountId)),
				HttpStatus.OK);
	}
	
	/**
	 * Evaluates whether the given <code>accountId</code> is in the valid format.
	 * 
	 * @param accountId Account Id of interest.
	 * @return <code>true</code> if given <code>accountId</code> in a valid format,
	 * and <code>false</code> otherwise.
	 */
	private static boolean providedAccountId(String accountId) {
		return accountId != null && !accountId.isEmpty();
	}
}
