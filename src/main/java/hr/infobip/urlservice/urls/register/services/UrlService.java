package hr.infobip.urlservice.urls.register.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.infobip.urlservice.urls.models.Url;
import hr.infobip.urlservice.urls.register.UrlIdGenerator;
import hr.infobip.urlservice.urls.repositories.UrlRepository;

/**
 * Service responsible for <b>URL</b> manegement inside this application.
 * 
 * @author fiilip
 *
 */
@Service
public class UrlService {
	
	/**
	 * Repository of registered <b>URL</b>s.
	 */
	private final UrlRepository urlRepository;
	
	/**
	 * Generates short <b>URL</b> Ids.
	 */
	private final UrlIdGenerator urlIdGenerator;
		
	/**
	 * Creates a new instance of {@link UrlService}.
	 * 
	 * @param urlRepository Repository of registered <b>URL</b>s.
	 * @param urlIdGenerator Generates short <b>URL</b> Ids.
	 */
	@Autowired
	public UrlService(UrlRepository urlRepository, UrlIdGenerator urlIdGenerator) {
		this.urlRepository = Objects.requireNonNull(urlRepository);
		this.urlIdGenerator = Objects.requireNonNull(urlIdGenerator);
	}

	/**
	 * Registers a given <code>url</code> to this service.
	 * 
	 * @param url <b>URL</b> being registered to this service.
	 * @return Generated <i>Url Id</i> associated to the given <code>url</code>.
	 */
	public String registerUrl(Url url) {
		String urlId = null;
		
		do {
			urlId = urlIdGenerator.generateUrlId();
		} while (!urlRepository.add(urlId, url));
		
		return urlId;
	}
	
	/**
	 * Checks whether the <b>URL</b> with associated given <code>urlId</code>
	 * exists in this service.
	 * 
	 * @param urlId Id of the <b>URL</b>.
	 * @return <code>true</code> if the <b>URL</b> with given <code>urlId</code> exists,
	 * and <code>false</code> otherwise.
	 */
	public boolean urlExists(String urlId) {
		return urlRepository.contains(urlId);
	}
		
	/**
	 * Gets the <b>URL</b> for the given <code>urlId</code>.
	 * 
	 * @param urlId Id of the <b>URL</b> being searched for.
	 * @return {@link Url} object associated to the given <code>urlId</code> if such exists,
	 * and <code>false</code> otherwise.
	 */
	public Url getUrl(String urlId) {
		return urlRepository.getUrl(urlId);
	}
}
