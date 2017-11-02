package hr.infobip.urlservice.urls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class UrlRepository {

	// urlId --> Url
	private final Map<String, Url> urls;
	
	public UrlRepository() {
		this.urls = new HashMap<>();
	}
	
	public boolean add(String urlId, Url url) {
		return urls.put(urlId, url) == null;
	}
	
	public boolean contains(String urlId) {
		return urls.containsKey(urlId);
	}
}
