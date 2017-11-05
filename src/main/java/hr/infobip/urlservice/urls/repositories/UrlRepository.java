package hr.infobip.urlservice.urls.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import hr.infobip.urlservice.urls.models.Url;

/**
 * Repository of {@link Url} objects.
 * 
 * @author fiilip
 *
 */
@Repository
public class UrlRepository {
	

	// urlId --> Url
	private final Map<String, Url> urls;
	
	/**
	 * Creates a new instance of {@link UrlRepository}.
	 */
	public UrlRepository() {
		this.urls = new HashMap<>();
	}
	
	/**
	 * Adds a new <code>url</code> object with the associated <code>urlId</code>
	 * to this repository.
	 * 
	 * @param urlId Id of the <code>url</code> being added to this repository.
	 * @param url <b>URL</b> being added to this repository.
	 * @return <code>true</code> if the <code>url</code> was successfully added,
	 * and <code>false</code> if some other <b>URL</b> is already registered with the given <code>urlId</code>.
	 */
	public boolean add(String urlId, Url url) {
		if (contains(urlId)) {
			return false;
		}
		
		urls.put(urlId, url);		
		return true;
	}
	
	/**
	 * Checks whether this repository contains a <b>URL</b> with given <code>urlId</code>.
	 * 
	 * @param urlId Id of <b>URL</b>.
	 * @return <code>true</code> if this repository contains the <b>URL</b> for the given <code>urlId</code>,
	 * and <code>false</code> otherwise.
	 */
	public boolean contains(String urlId) {
		return urls.containsKey(urlId);
	}
	
	/**
	 * Gets the {@link Url} object for the associated <code>urlId</code>.
	 * 
	 * @param urlId Id of <b>URL</b>.
	 * @return {@link Url} object for the associated <code>urlId</code>, 
	 * and <code>null</code> if such object doesn't exist.
	 */
	public Url getUrl(String urlId) {
		return urls.get(urlId);
	}
}
