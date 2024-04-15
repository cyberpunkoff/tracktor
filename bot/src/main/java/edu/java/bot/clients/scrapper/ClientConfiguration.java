package edu.java.bot.clients.scrapper;

import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Bean
    public ScrapperClient scrapperClient(ApplicationConfig config) {
        return new WebClientScrapperClient(config.scrapperUrl());
    }
}
