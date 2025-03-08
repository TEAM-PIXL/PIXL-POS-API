package pos.api.teampixl.org.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

  /*
   * Configure Spring Security to allow access to Swagger UI resources
   * and disable CSRF and security headers.
   *
   * @param http the HttpSecurity object to configure
   * @return a SecurityFilterChain object
   * @throws Exception if an error occurs
   * @see org.springframework.security.config.annotation.web.builders.HttpSecurity
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
        .headers(headers -> headers.disable()); // optional, to remove default security headers
    return http.build();
  }

  /*
   * Configure CORS to allow requests from any origin.
   *
   * @return a WebMvcConfigurer object
   * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
      }
    };
  }
}
