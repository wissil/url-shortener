package hr.infobip.urlservice.help;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller that displays the <i>Help</i> page.
 * 
 * @author fiilip
 *
 */
@Controller
public class HelpController {
	
	private static final String HELP_PAGE = "help";

    @RequestMapping("/help")
    public String help() {
        return HELP_PAGE;
    }
}
