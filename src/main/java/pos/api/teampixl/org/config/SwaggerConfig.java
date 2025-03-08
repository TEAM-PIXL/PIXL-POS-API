package pos.api.teampixl.org.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

  /*
   * This method creates a bean for the OpenAPI object.
   * The OpenAPI object is used to configure the API documentation.
   * The title, version, and description of the API documentation are set using the Info object.
   *
   * @return OpenAPI object
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("API Documentation")
                .version("1.0.0")
                .description("API documentation for the application"));
  }

  /*
   * This method creates a bean for the GroupedOpenApi object.
   * The GroupedOpenApi object is used to group the API documentation.
   * The publicApi method groups the API documentation for the public endpoints.
   *
   * @return GroupedOpenApi object
   */
  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder().group("public").pathsToMatch("/**").build();
  }
}
