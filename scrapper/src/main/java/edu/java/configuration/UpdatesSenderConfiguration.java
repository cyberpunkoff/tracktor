package edu.java.configuration;

import edu.java.LinkUpdateRequest;
import edu.java.clients.bot.BotClient;
import edu.java.sender.BotUpdateSender;
import edu.java.sender.ScrapperQueueProducer;
import edu.java.sender.UpdateSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class UpdatesSenderConfiguration {
    @Bean
    public UpdateSender updateSender(
        ApplicationConfig config,
        KafkaTemplate<String, LinkUpdateRequest> kafka,
        BotClient client
    ) {
        return (config.useQueue())
            ? new ScrapperQueueProducer(kafka, config)
            : new BotUpdateSender(client);
    }
}
