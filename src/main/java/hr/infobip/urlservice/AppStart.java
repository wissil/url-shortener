package hr.infobip.urlservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * A class containing the {@link #main} method.<br>
 * This class is used for running the <i>SpringBoot</i> application.
 * 
 * @author fiilip
 *
 */
@SpringBootApplication
@ComponentScan({"hr.infobip.urlservice"})
@EntityScan("hr.infobip.urlservice")
public class AppStart {

	/**
	 * Program entry-point.<br>
	 * Starts a <i>SpringBoot</i> application.
	 * 
	 * @param args No command line arguments.
	 */
	public static void main(String[] args) {
		// creates servlet container
		// hosts the app in the servlet container
		SpringApplication.run(AppStart.class, args);
	}

}
