package edu.java.clients;

import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.GitHubWebClient;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowWebClient;
import edu.java.configuration.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Bean
    public StackOverflowClient stackOverflowClient(ApplicationConfig applicationConfig) {
        return new StackOverflowWebClient(applicationConfig.client().stackOverflow().baseUrl());
    }

    // TODO: change this to proper way
    @Bean
    public GitHubClient gitHubClient(ApplicationConfig applicationConfig) {
        return new GitHubWebClient(applicationConfig.client().gitHub().baseUrl(), applicationConfig);
    }
}
