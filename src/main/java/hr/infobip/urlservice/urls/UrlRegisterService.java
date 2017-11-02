package hr.infobip.urlservice.urls;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlRegisterService {
	
	private final UrlRepository urlRepository;
	
	private final UrlIdGenerator urlIdGenerator;
	
	@Autowired
	public UrlRegisterService(UrlRepository urlRepository, UrlIdGenerator urlIdGenerator) {
		this.urlRepository = Objects.requireNonNull(urlRepository);
		this.urlIdGenerator = Objects.requireNonNull(urlIdGenerator);
	}

	public String registerUrl(Url url) {
		String urlId = null;
		
		do {
			urlId = urlIdGenerator.generateUrlId();
		} while (!urlRepository.add(urlId, url));
		
		return urlId;
	}
	
	public boolean urlExists(String urlId) {
		return urlRepository.contains(urlId);
	}
}
