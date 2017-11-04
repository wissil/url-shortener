package hr.infobip.urlservice.urls.services;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.infobip.urlservice.urls.Url;
import hr.infobip.urlservice.urls.UrlIdGenerator;
import hr.infobip.urlservice.urls.UrlRepository;

@Service
public class UrlRegisterService {
	
	private final UrlRepository urlRepository;
	
	private final UrlIdGenerator urlIdGenerator;
	
	@Autowired
	public UrlRegisterService(UrlRepository urlRepository, UrlIdGenerator urlIdGenerator) {
		this.urlRepository = Objects.requireNonNull(urlRepository);
		this.urlIdGenerator = Objects.requireNonNull(urlIdGenerator);
	}

	public String registerUrl(String accountId, Url url) {
		String urlId = null;
		
		do {
			urlId = urlIdGenerator.generateUrlId();
		} while (!urlRepository.add(accountId, urlId, url));
		
		return urlId;
	}
	
	public boolean urlExists(String urlId) {
		return urlRepository.contains(urlId);
	}
	
	public Url getUrl(String urlId) {
		return urlRepository.getUrl(urlId);
	}
	
	public Set<Url> getStats(String accountId) {
		return urlRepository.getStats(accountId);
	}
	
	public boolean accountExists(String accountId) {
		return urlRepository.accountExists(accountId);
	}
}
