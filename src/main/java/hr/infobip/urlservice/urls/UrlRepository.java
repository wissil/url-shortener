package hr.infobip.urlservice.urls;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

@Repository
public class UrlRepository {
	
	// accountId --> [urls]
	private final Map<String, Set<Url>> accountStats;

	// urlId --> Url
	private final Map<String, Url> urls;
	
	public UrlRepository() {
		this.accountStats = new HashMap<>();
		this.urls = new HashMap<>();
	}
	
	// add observer: increment number of url hits?
	
	public boolean add(String accountId, String urlId, Url url) {
		if (contains(urlId)) {
			return false;
		}
		
		if (accountStats.get(accountId) == null) {
			Set<Url> stats = new HashSet<>();		
			stats.add(url);
			accountStats.put(accountId, stats);
		} else {
			Set<Url> stats = accountStats.get(accountId);
			stats.add(url);
			accountStats.put(accountId, stats);
		}
		
		urls.put(urlId, url);
		return true;
	}
	
	public boolean contains(String urlId) {
		return urls.containsKey(urlId);
	}
	
	public Url getUrl(String urlId) {
		return urls.get(urlId);
	}
	
	public Set<Url> getStats(String accountId) {
		return accountStats.get(accountId);
	}
	
	public void incrementUrlHit(Url url) {
		url.incrementNumberOfHits();
	}
	
	public boolean accountExists(String accountId) {
		return accountStats.containsKey(accountId);
	}
}
