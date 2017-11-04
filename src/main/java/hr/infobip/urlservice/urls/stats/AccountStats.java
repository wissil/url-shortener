package hr.infobip.urlservice.urls.stats;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A class representing the statistics for a single account, 
 * ie. how many times each <b>URL</b> registered by this account was hit.
 * 
 * @author fiilip
 *
 */
public class AccountStats {
	
	/**
	 * url --> number of hits
	 */
	private final Map<String, Integer> stats;
	
	/**
	 * Account Id of this statistics.
	 */
	private final String accountId;
	
	/**
	 * Creates a new instance of {@link AccountStats}.
	 * 
	 * @param accountId Account Id of this statistics.
	 */
	public AccountStats(String accountId) {
		this.accountId = Objects.requireNonNull(accountId);
		this.stats = new HashMap<>();
	}
	
	
	/**
	 * Registers a given <code>url</code> to this stats. If a given <code>url</code>
	 * is already registered to this stats, this method will take no effect.
	 * 
	 * @param url <b>URL</b> to be registered to this stats.
	 */
	public void registerUrl(String url) {
		stats.put(url, 0);
	}
	
	/**
	 * Gets the account Id that is corresponding to this statistics.
	 * 
	 * @return Account Id of this statistics.
	 */
	public String getAccountId() {
		return accountId;
	}
}
