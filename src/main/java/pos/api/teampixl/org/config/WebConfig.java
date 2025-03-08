package pos.api.teampixl.org.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  /*
   * Add a resource handler for Swagger UI.
   * This allows the Swagger UI to be served from the classpath.
   * The Swagger UI is available at http://localhost:8080/swagger-ui/index.html
   *
   * @param registry the resource handler registry
   * @see WebMvcConfigurer#addResourceHandlers(ResourceHandlerRegistry)
   */
  @Override
  public void addResourceHandlers(@SuppressWarnings("null") ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/swagger-ui/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/4.11.1/");
  }
}
