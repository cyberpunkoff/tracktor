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
    private final ApplicationConfig applicationConfig;

    // TODO: написать получше (использовать короткое имя bean'а)
    @Scheduled(fixedDelayString = "#{@'app-edu.java.configuration.ApplicationConfig'.scheduler().interval()}")
    public void update() {
        log.info("Updated!");
        List<Link> toUpdate = linkRepository.findAllCheckedLaterThan(applicationConfig.scheduler().forceCheckDelay());

        for (Link link : toUpdate) {
            log.info(String.valueOf(link.getUrl()));
        }
    }
}
