package pos.api.teampixl.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.annotation.PreDestroy;
import pos.api.teampixl.org.common.logger.Logger;
import pos.api.teampixl.org.database.SQLite;
import pos.api.teampixl.org.server.URL;



/** The main application */
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Application.class, args);
    SQLite.initializeDatabase();
    Logger.getLogger(Application.class.getName()).info("Application started from " + Application.class.getName());
    Logger.getLogger(Application.class.getName()).info("Running application on " + new URL(context));
  }

  @PreDestroy
  public void onShutdown() {
    Logger.getLogger(Application.class.getName())
          .info("Application stopped from " + Application.class.getName());
  }

  @Controller
  public static class HomeController {

    @GetMapping("/")
    public String redirectToSwagger() {
      return "redirect:/swagger-ui/index.html";
    }

  }
}
