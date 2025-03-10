package pos.api.teampixl.org.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pos.api.teampixl.org.common.logger.Logger;
import pos.api.teampixl.org.database.SQLite;
import pos.api.teampixl.org.database.repositories.UserRepository;

@Configuration
public class DatabaseConfig {

    private static final Logger logger = Logger.getLogger(DatabaseConfig.class.getName());

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        SQLite.initializeDatabase();
        return args -> {
            logger.info("Initialized repositories.");
        };
    }
    
}
