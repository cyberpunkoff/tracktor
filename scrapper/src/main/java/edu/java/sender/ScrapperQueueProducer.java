package edu.java.sender;

import edu.java.LinkUpdateRequest;
import edu.java.configuration.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class ScrapperQueueProducer implements UpdateSender {
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    private final ApplicationConfig config;

    @Override
    public void send(LinkUpdateRequest update) {
        kafkaTemplate.send(config.topic(), update);
    }
}
