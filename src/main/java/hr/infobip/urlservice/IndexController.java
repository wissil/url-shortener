package hr.infobip.urlservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller of the index page.
 * 
 * @author fiilip
 *
 */
@Controller
public class IndexController {
	
	private static final String HELP_URL = "/help";
	
	/**
	 * Redirects to the help page.
	 */
	@RequestMapping("/")
    public String index() {
        return "redirect:" + HELP_URL;
    }

}
