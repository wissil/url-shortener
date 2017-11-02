package hr.infobip.urlservice.urls.repositories;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import hr.infobip.urlservice.urls.resources.Url;

@Repository
public class UrlRepository {
	
	// urlId --> url
	private final Map<String, Url> urls;
	
	public UrlRepository() {
		this.urls = new HashMap<>();
	}

	public Url getUrl(String urlId) {
		return urls.get(urlId);
	}
	
	public boolean contains(String urlId) {
		return urls.containsKey(urlId);
	}
	
	public boolean saveUrl(String urlId, Url longUrl) {
		return urls.put(urlId, longUrl) == null;
	}
}
