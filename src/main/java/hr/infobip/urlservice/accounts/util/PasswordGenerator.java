package hr.infobip.urlservice.accounts.util;

import java.security.SecureRandom;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {
	
	/**
	 * Length of the password to be generated.
	 */
	private static final int PASS_LENGTH = 8;
	
	/**
	 * Random number generator.<br>
	 * {@link SecureRandom} is used because of better variation of generated symbols.
	 */
	private final SecureRandom random;
	
	/**
	 * Symbols available for password generation.
	 */
	// currently there are 62 symbols in use
	private static final char[] symbols = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', 
			
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
			'U', 'V', 'W', 'X', 'Y', 'Z'
	};

	/**
	 * Creates a new instance of {@link PasswordGenerator}.
	 */
	public PasswordGenerator() {
		this.random = new SecureRandom();
	}
	
	/**
	 * Generates a random alpha-numeric password.
	 * 
	 * @return Randomly generated alpha-numeric password.
	 */
	public String generate() {
		StringBuilder sb = new StringBuilder(PASS_LENGTH);

		IntStream
			.range(0, PASS_LENGTH)
			.forEach(i -> sb.append(symbols[random.nextInt(symbols.length)]));

		return sb.toString();
	}
}
