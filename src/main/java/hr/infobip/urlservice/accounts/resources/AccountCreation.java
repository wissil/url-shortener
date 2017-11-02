package hr.infobip.urlservice.accounts.resources;

import java.io.IOException;
import java.util.Objects;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import hr.infobip.urlservice.Resource;
import hr.infobip.urlservice.accounts.services.AccountService;

/**
 * A controller that is responsible for user accounts management.
 * 
 * @author fiilip
 *
 */
@Path("/")
public class AccountCreation implements Resource {
	
	private static final String URL_REGEX = "account";
	
	ObjectMapper mapper = new ObjectMapper();

	
	/**
	 * Underlying service taking care of account management.
	 */
	private final AccountService service;
		
	/**
	 * Creates a new instance of {@link AccountController}.
	 * 
	 * @param service Service responsible for user accounts manegement.
	 */
	@Autowired
	public AccountCreation(AccountService service) {
		this.service = Objects.requireNonNull(service);
	}

	@POST
	@Path(URL_REGEX)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@RequestBody String json) {		
		try {		
			return processRequest(json);			
		} catch (IOException e) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new AccountCreatedFailResponse("Service request is not in a valid format."))
					.build();
		}		
	}

	/**
	 * Process the service request described by the input <code>json</code>.
	 * 
	 * @param json JSON data representing request input data.
	 * @return Service response based on the input data.
	 * @throws IOException On invalid (unexpected) input data.
	 */
	private Response processRequest(String json) throws IOException {
		ObjectNode node = mapper.readValue(json, ObjectNode.class);
		String accountId = node.get("accountId").textValue();
		
		if (service.exists(accountId)) {
			// account already exists
			final boolean fail = false;
			
			return Response
					.status(Status.BAD_REQUEST)
					.entity(new AccountCreatedSuccessResponse(fail, null))
					.build();
		} else {
			// account doesn't already exist --> save it!
			String password = service.generatePassword();
			service.save(accountId, password);
			
			final boolean success = true;
			
			return Response
					.status(Status.CREATED)
					.entity(new AccountCreatedSuccessResponse(success, password))
					.build();
		}
	}

	@Override
	public String getUrlRegex() {
		return URL_REGEX;
	}
}
