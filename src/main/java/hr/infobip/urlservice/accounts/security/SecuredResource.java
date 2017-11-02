package hr.infobip.urlservice.accounts.security;

import hr.infobip.urlservice.Resource;

public interface SecuredResource extends Resource {

	void registerToSecurityFilter();
}
