package edu.java.service.updater;

import edu.java.LinkUpdateRequest;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.dto.LinkDto;
import edu.java.service.LinkService;
import edu.link.links.GitHubLink;
import edu.link.links.Link;
import edu.link.links.StackOverflowLink;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinkUpdaterService {
    private final GitHubClient gitHubClient;
    private final LinkService linkService;
    private final StackOverflowClient stackOverflowClient;

    public List<LinkUpdateRequest> createUpdateRequests(List<LinkDto> linksToUpdate) {
        List<LinkUpdateRequest> updateRequests = new ArrayList<>();

        for (LinkDto linkDto : linksToUpdate) {
            Link link = Link.parse(linkDto.getUrl().toString());

            LinkUpdater updater = null;
            if (link instanceof GitHubLink) {
                GitHubLinkUpdater gitHubLinkUpdater = new GitHubLinkUpdater(gitHubClient);
                gitHubLinkUpdater.updateLink(linkDto);
                updater = gitHubLinkUpdater;
            } else if (link instanceof StackOverflowLink) {
                StackoverflowLinkUpdater stackoverflowLinkUpdater = new StackoverflowLinkUpdater(stackOverflowClient);
                stackoverflowLinkUpdater.updateLink(linkDto);
                updater = stackoverflowLinkUpdater;
            }

            if (updater == null) {
                throw new RuntimeException("Unknown link");
            }

            if (updater.isLinkUpdated()) {
                linkService.updateUpdatedAt(linkDto.getUrl(), updater.getUpdatedAt());
                updateRequests.addAll(updater.getLinkUpdates());
            }

            linkService.updateCheckedAt(linkDto.getUrl(), OffsetDateTime.now());
        }

        return updateRequests;
    }
}
