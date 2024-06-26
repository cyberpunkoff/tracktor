package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    @Bean
    Scheduler scheduler,
    AccessType databaseAccessType,
    @NotNull
    Client client,
    String topic,
    boolean useQueue
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public enum RetryBackoffPolicy { CONSTANT, LINEAR, EXPONENT }

    public enum AccessType { JDBC, JPA, JOOQ }

    public record Client(GitHub gitHub, StackOverflow stackOverflow, Bot bot) {
        public record GitHub(RetryBackoffPolicy retryPolicy, String baseUrl, String token) {
        }

        public record StackOverflow(RetryBackoffPolicy retryPolicy, String baseUrl) {
        }

        public record Bot(String baseUrl) {
        }
    }
}
