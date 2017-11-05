package hr.infobip.urlservice.urls.stats.responses;

import java.util.Map;
import java.util.Objects;

/**
 * A class that represents a response to the statistics request that is valid.
 * 
 * @author fiilip
 *
 */
public class ValidStatsRequestRespose implements StatsResponse {

	/**
	 * Statistics.
	 */
	private final Map<String, Integer> stats;
	
	/**
	 * Creates a new instance of {@link ValidStatsRequestRespose}.
	 * 
	 * @param stats Statistics.
	 */
	public ValidStatsRequestRespose(Map<String, Integer> stats) {
		this.stats = Objects.requireNonNull(stats);
	}
	
	/**
	 * Gets the statistics carried by this response.
	 * 
	 * @return Statistics.
	 */
	public Map<String, Integer> getStats() {
		return stats;
	}
}
