package hr.infobip.urlservice.util;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {
	
	/**
	 * Length of the password to be generated.
	 */
	private static final int PASS_LENGTH = 8;
	
	/**
	 * Symbols available for password generation.
	 */
	private static final String SYMBOLS =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ" + // upper-case letters
			"abcdefghijklmnopqrstuvxyz" +  // lower-case letters
			"0123456789";				  // digits
	
	/**
	 * Number of symbols to choose from whenever generating a password.
	 */
	private static final int SYM_LEN = SYMBOLS.length(); // cached for performance improvement
	
	/**
	 * Random number generator.<br>
	 * {@link SecureRandom} is used because of better variation of generated symbols.
	 */
	private static Random random = new SecureRandom();

	/**
	 * Generates a random alpha-numeric password.
	 * 
	 * @return Randomly generated alpha-numeric password.
	 */
	public String generate() {
		StringBuilder sb = new StringBuilder(PASS_LENGTH);
		
		for (int i=0; i<PASS_LENGTH; i++) {
			int index = random.nextInt(SYM_LEN);
			sb.append(SYMBOLS.charAt(index));
		}
		
		return sb.toString();
	}
}
