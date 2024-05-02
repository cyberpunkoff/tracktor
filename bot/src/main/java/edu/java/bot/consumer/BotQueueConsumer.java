package edu.java.bot.consumer;

import edu.java.LinkUpdateRequest;
import edu.java.bot.service.LinkUpdater;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.use-queue", havingValue = "true")
public class BotQueueConsumer {
    private final LinkUpdater updateService;

    @SneakyThrows
    @RetryableTopic(
        attempts = "1",
        dltStrategy = DltStrategy.FAIL_ON_ERROR,
        dltTopicSuffix = "_dlq")
    @KafkaListener(topics = "${app.topic-name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(LinkUpdateRequest update) {
        log.info("New message! {}", update);
        updateService.update(update);
    }

    @DltHandler
    public void handleDltPayment(
        LinkUpdateRequest linkUpdate, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Event on dlt topic={}, payload={}", topic, linkUpdate);
    }
}
