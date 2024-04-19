package edu.java.clients;

import edu.java.clients.bot.BotClient;
import edu.java.clients.bot.WebClientBotClient;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.GitHubWebClient;
import edu.java.clients.retry.RetryFilterFactory;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowWebClient;
import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.RetryConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean
    public StackOverflowClient stackOverflowClient(
        WebClient.Builder webClientBuilder,
        ApplicationConfig applicationConfig,
        RetryConfiguration retryConfiguration
    ) {
        return new StackOverflowWebClient(
            webClientBuilder.filter(RetryFilterFactory.createFilter(RetryFilterFactory.createRetry(
                "stackoverflow",
                retryConfiguration
            ))),
            applicationConfig.client().stackOverflow().baseUrl()
        );
    }

    @Bean
    public GitHubClient gitHubClient(
        WebClient.Builder webClientBuilder,
        ApplicationConfig applicationConfig,
        RetryConfiguration retryConfiguration
    ) {
        return new GitHubWebClient(
            webClientBuilder.filter(RetryFilterFactory.createFilter(RetryFilterFactory.createRetry(
                "github",
                retryConfiguration
            ))),
            applicationConfig.client().gitHub().baseUrl(),
            applicationConfig
        );
    }

    @Bean
    public BotClient botClient(
        WebClient.Builder webClientBuilder,
        ApplicationConfig applicationConfig,
        RetryConfiguration retryConfiguration
    ) {
        return new WebClientBotClient(
            webClientBuilder.filter(RetryFilterFactory.createFilter(RetryFilterFactory.createRetry(
                "bot",
                retryConfiguration
            ))),
            applicationConfig.client().gitHub().baseUrl()
        );
    }
}
