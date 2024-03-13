package edu.java.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LinkUpdaterScheduler {
    // TODO: написать получше (использовать короткое имя bean'а)
    @Scheduled(fixedDelayString = "#{@'app-edu.java.configuration.ApplicationConfig'.scheduler().interval()}")
    public void update() {
        log.info("Updated!");
    }
}
