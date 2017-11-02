package hr.infobip.urlservice.urls.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.infobip.urlservice.urls.repositories.UrlRepository;
import hr.infobip.urlservice.urls.resources.Url;
import hr.infobip.urlservice.urls.resources.UrlIdGenerator;

/**
 * A service that offers <b>URL</b> registration manegement.
 * 
 * @author fiilip
 *
 */
@Service
public class UrlRegistrationService {
	
	/**
	 * Generates <i>URL Id</i>.
	 */
	private final UrlIdGenerator urlIdGenerator;
	
	/**
	 * Repository of registered URL-s.
	 */
	private final UrlRepository urlRepository;
	
	/**
	 * Creates a new instance of {@link UrlRegistrationService}.
	 * 
	 * @param urlIdGenerator Generates a short <i>URL Id</i>.
	 */
	@Autowired
	public UrlRegistrationService(UrlIdGenerator urlIdGenerator, UrlRepository urlRepository) {
		this.urlIdGenerator = Objects.requireNonNull(urlIdGenerator);
		this.urlRepository = Objects.requireNonNull(urlRepository);
	}

	/**
	 * Registers a given <i>long URL</i> <code>longUrl</code> to the service.
	 * 
	 * @param longUrl <i>Long Url</i> being registered to the service.
	 * @return Generated <i>Short Url Id</i>.
	 */
	public String registerUrl(Url longUrl) {		
		String urlId = null;
		
		do {
			urlId = urlIdGenerator.generateUrlId();
		} while (!urlRepository.saveUrl(urlId, longUrl));
		
		return urlId;
	}
	
 }
