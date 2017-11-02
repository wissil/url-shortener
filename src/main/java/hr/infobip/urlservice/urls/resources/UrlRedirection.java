package hr.infobip.urlservice.urls.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import hr.infobip.urlservice.accounts.security.SecuredResource;
import hr.infobip.urlservice.accounts.security.SecurityFilter;
import hr.infobip.urlservice.urls.repositories.UrlRepository;

@Path("/u")
public class UrlRedirection implements SecuredResource {
	
	private final UrlRepository repository;
		
	@Autowired
	public UrlRedirection(UrlRepository repository) {
		this.repository = Objects.requireNonNull(repository);
	}

	@GET
	@Path("{urlId}")
	public Response redirectTo(@PathParam("urlId") String urlId) throws URISyntaxException {
		if (!repository.contains(urlId)) {
			return Response
					.status(Status.NOT_FOUND)
					.build();
		} else {
			Url url = repository.getUrl(urlId);

			return Response
					.status(url.getRedirectType())
					.location(new URI(url.getUrl()))
					.build();
		}
	}

	@Override
	public String getUrlRegex() {
		return String.format("/u/%s/?", "([a-zA-Z0-9]{6})");
	}

	@Override
	public void registerToSecurityFilter() {
	}
}
