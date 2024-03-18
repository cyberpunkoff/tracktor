package edu.java.scheduler;

import edu.java.LinkUpdateRequest;
import edu.java.clients.bot.BotClient;
import edu.java.configuration.ApplicationConfig;
import edu.java.dto.LinkDto;
import edu.java.service.LinkService;
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
    private final BotClient botClient;
    private final LinkUpdaterService linkUpdaterService;
    private final ApplicationConfig.Scheduler scheduler;

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        if (!scheduler.enable()) {
            return;
        }

        log.info("Updating links...");
        List<LinkDto> linksToUpdate = linkService.listAllCheckedLaterThan(scheduler.forceCheckDelay());
        List<LinkUpdateRequest> updatesToSend = linkUpdaterService.updateLinks(linksToUpdate);
        updatesToSend.forEach(botClient::sendUpdate);
    }
}
