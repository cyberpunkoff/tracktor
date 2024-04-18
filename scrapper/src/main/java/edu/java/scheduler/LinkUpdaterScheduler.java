package edu.java.scheduler;

import edu.java.LinkUpdateRequest;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.LinkDto;
import edu.java.sender.UpdateSender;
import edu.java.service.LinkService;
import edu.java.service.updater.LinkUpdaterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LinkUpdaterScheduler {
    private final LinkService linkService;
    private final UpdateSender updateSender;
    private final LinkUpdaterService linkUpdaterService;
    private final ApplicationConfig.Scheduler scheduler;

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        if (!scheduler.enable()) {
            return;
        }

        log.info("Starting update scheduler...");

        List<LinkDto> linksToUpdate = linkService.getLinksCheckedDurationAgo(scheduler.forceCheckDelay());
        List<LinkUpdateRequest> updatesToSend = linkUpdaterService.createUpdateRequests(linksToUpdate);
        updatesToSend.forEach(updateSender::send);

        log.info("Update scheduler done");
    }
}
