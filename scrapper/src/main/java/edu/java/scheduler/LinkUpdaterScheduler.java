package edu.java.scheduler;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.Link;
import edu.java.repository.LinkDao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LinkUpdaterScheduler {
    private final LinkDao linkRepository;
    private final ApplicationConfig.Scheduler scheduler;

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        if (!scheduler.enable()) {
            return;
        }

        log.info("Updated!");
        List<Link> toUpdate = linkRepository.findAllCheckedLaterThan(scheduler.forceCheckDelay());

        // update link checked at and updated at there
        // then send notification to bot

        for (Link link : toUpdate) {
            log.info(String.valueOf(link.getUrl()));
        }
    }
}
