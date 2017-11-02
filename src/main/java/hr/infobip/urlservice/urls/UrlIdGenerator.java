package hr.infobip.urlservice.urls;

import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

/**
 * A class that is used to generate a short <i>URL Id</i> based on
 * some given URL.<br>
 * 
 * Generated short <i>URL Id</i> will be used as a short representation of some
 * given long URL.
 * 
 * @author fiilip
 *
 */
@Component
public class UrlIdGenerator {
	
	/**
	 * Length of the generated URL Id.
	 */
	private static final int URL_ID_LENGTH = 6;
	
	/**
	 * Regular expression describing the <i>Url Id</i>.
	 */
	//private static final String regex = String.format("([a-zA-Z0-9]{%d})", URL_ID_LENGTH);
	
	/**
	 * Symbols used for the URL generation.<br>
	 */
	// currently there are 62 symbols in use
	private static final char[] symbols = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
			'Y', 'Z'
	};
	
	/**
	 * Random value generator.
	 */
	private final Random random;
	
	/**
	 * Creates a new instance of {@link UrlIdGenerator}.
	 */
	public UrlIdGenerator() {
		this.random = new Random();
	}
	
	/**
	 * Generates a short <i>Url Id</i>.
	 * 
	 * @return Generated short <i>Url Id</i>.
	 */
	public String generateUrlId() {
		StringBuilder sb = new StringBuilder(URL_ID_LENGTH);
		
		IntStream
			.range(0, URL_ID_LENGTH)
			.forEach(i -> sb.append(symbols[random.nextInt(symbols.length)]));
			
		return sb.toString();
	}
}
