package pos.api.teampixl.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import jakarta.annotation.PreDestroy;
import pos.api.teampixl.org.server.URL;

import pos.api.teampixl.org.logger.Logger;

/** The main application */
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Application.class, args);
    Logger.getLogger(Application.class.getName()).info("Application started from " + Application.class.getName());
    Logger.getLogger(Application.class.getName()).info("Running application on " + new URL(context));
    
  }

  @PreDestroy
  public void onShutdown() {
    Logger.getLogger(Application.class.getName()).info("Application stopped from " + Application.class.getName());
  }
}
