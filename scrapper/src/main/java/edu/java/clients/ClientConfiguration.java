package edu.java.clients;

import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.GitHubWebClient;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowWebClient;
import edu.java.configuration.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean
    public StackOverflowClient stackOverflowClient(
        WebClient.Builder webClientBuilder,
        ApplicationConfig applicationConfig
    ) {
        return new StackOverflowWebClient(
            webClientBuilder,
            applicationConfig.client().stackOverflow().baseUrl()
        );
    }

    // TODO: change this to proper way
    @Bean
    public GitHubClient gitHubClient(
        WebClient.Builder webClientBuilder,
        ApplicationConfig applicationConfig
    ) {
        return new GitHubWebClient(
            webClientBuilder,
            applicationConfig.client().gitHub().baseUrl(),
            applicationConfig
        );
    }
}
