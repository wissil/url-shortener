package hr.infobip.urlservice.urls.stats.services;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import hr.infobip.urlservice.urls.models.Url;

/**
 * Service responsible for all the statistics management of this application.
 * 
 * @author fiilip
 *
 */
@Service
public class AccountStatsService {
		
	/**
	 * Statistics per account.<br>
	 * Maps the <code>accountId</code> to the set of associated {@link Url} objects.
	 */
	// accountId --> set[urlIds]
	private final Map<String, Set<Url>> accountStats;
	
	/**
	 * Maps the {@link Url} object to the number of times it got called.
	 */
	// urlId --> number of hits
	private final Map<Url, AtomicInteger> urlHits;
	
	/**
	 * Creates a new instance of {@link AccountStatsService}.
	 */
	public AccountStatsService() {
		this.accountStats = new HashMap<>();
		this.urlHits = new ConcurrentHashMap<>();
	}
	
	/**
	 * Registers a given <code>url</code> to the given <code>accountId</code>.
	 * 
	 * @param url <b>URL</b> being registered to the given <code>accountId</code>.
	 * @param accountId Account Id the given <code>url</code> is being registered to.
	 */
	public void registerUrlToAccount(Url url, String accountId) {
		Set<Url> stats = null;
		
		if (accountStats.get(accountId) == null) {
			stats = new HashSet<>();		
		} else {
			stats = accountStats.get(accountId);		
		}
		stats.add(url);
		
		accountStats.put(accountId, stats);
		urlHits.put(url, new AtomicInteger(0));
	}

	/**
	 * Increment <b>URL</b> hit statistic for the given <code>url</code> by <code>1</code> hit.
	 * 
	 * @param url <b>URL</b> that was hit.
	 * @return Number of times the given <code>url</code> was hit after this increment.
	 */
	public int incrementHit(Url url) {
		// return not to lose potentially beneficial info in the client code
		// altho currently not used
		return urlHits.get(url).incrementAndGet();
	}
	
	/**
	 * Generates the statistics in the format of map <code>(url, numberOfHits)</code>
	 * for the given <code>accountId</code>.
	 * 
	 * @param accountId Account Id the statistics is being generated for.
	 * @return Statistics of <b>URL</b> hits for the <b>URL</b>s registered by the given <code>accountId</code>.
	 */
	public Map<String, Integer> generateStats(String accountId) {
		return accountStats.get(accountId)
				.stream()
				.collect(Collectors.toMap(
						u -> u.getUrl(), 
						u -> urlHits.get(u).intValue()));			
	}
}
