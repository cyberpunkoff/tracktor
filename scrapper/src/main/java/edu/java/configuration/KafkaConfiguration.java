package edu.java.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConditionalOnProperty(name = "app.use-queue", havingValue = "true")
public class KafkaConfiguration {
    @Bean
    public NewTopic topic(ApplicationConfig config) {
        return TopicBuilder.name(config.topic())
            .partitions(2)
            .replicas(1)
            .build();
    }
}
