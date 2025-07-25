package org.iesalixar.daw2.keinerhurtado.duel_deck_api.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;



@Configuration
public class DotenvConfig {

    private static final Logger logger = LoggerFactory.getLogger(DotenvConfig.class);

    static {
        try {
            logger.info("Loading environment variables from .env file...");

            Dotenv dotenv = Dotenv.configure().load();
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
                logger.debug("Set system property: {} = {}", entry.getKey(), entry.getValue());
            });

            logger.info(".env file loaded successfully.");
        } catch (Exception e) {
            logger.error("Failed to load .env file", e);
        }
    }

    // Añadir este método para asegurar que las variables de entorno se cargan antes de la ejecución
    @PostConstruct
    public void init() {
        logger.info("DotenvConfig initialized");
    }
}
