package edu.java.bot.clients.scrapper;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.configuration.RetryConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Bean
    public ScrapperClient scrapperClient(RetryConfiguration retryConfiguration, ApplicationConfig config) {
        return new WebClientScrapperClient(retryConfiguration, config.scrapperUrl());
    }
}
