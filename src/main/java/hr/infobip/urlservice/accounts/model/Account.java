package hr.infobip.urlservice.accounts.model;

import java.util.Objects;

/**
 * A class that represents a single account that is authorized to
 * use this application.
 * 
 * @author fiilip
 *
 */
public class Account {
	
	/**
	 * The Id of this account.
	 */
	private final String accountId;
	
	/**
	 * The password hash of this account.
	 */
	private final String passwordHash;
	
	/**
	 * Creates a new instance of {@link Account}.
	 * 
	 * @param accountId The Id of the account being created.
	 * @param passwordHash The password hash of the account being created.
	 */
	public Account(String accountId, String passwordHash) {
		this.accountId = Objects.requireNonNull(accountId);
		this.passwordHash = Objects.requireNonNull(passwordHash);
	}
	
	/**
	 * Gets the account Id of this account.
	 * 
	 * @return Account Id.
	 */
	public String getAccountId() {
		return accountId;
	}
	
	/**
	 * Gets the password hash of this account.
	 * 
	 * @return Password hash.
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		return true;
	}
}
