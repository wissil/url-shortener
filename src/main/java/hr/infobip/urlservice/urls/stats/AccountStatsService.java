package hr.infobip.urlservice.urls.stats;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import hr.infobip.urlservice.urls.Url;

@Service
public class AccountStatsService {
	
	// accountId --> stats
	private final Map<String, AccountStats> stats;
	
	/**
	 * Creates a new instance of {@link AccountStatsService}.
	 */
	public AccountStatsService() {
		this.stats = new HashMap<>();
	}

	/**
	 * Gets the statistics for the given <code>accountId</code>.
	 * 
	 * @param accountId Account Id of interest.
	 * @return Statistics for the given <code>accountId</code>, and
	 * <code>null</code> if the statistics for the given <code>accountId</code> doesn't exist.
	 */
	public AccountStats getStats(String accountId) {
		return stats.get(accountId);
	}
	
	/**
	 * Increment number of times the given <code>url</code> has been hit.
	 * 
	 * @param <b>URL</b> of interest.
	 */
	public void incrementHits(Url url) {
		url.incrementNumberOfHits();
	}
}
