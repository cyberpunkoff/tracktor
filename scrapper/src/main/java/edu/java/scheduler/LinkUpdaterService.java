package edu.java.scheduler;

import edu.java.LinkUpdateRequest;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.RepositoryResponse;
import edu.java.clients.stackoverflow.QuestionResponse;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.dto.Chat;
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

    public List<LinkDto> updateLinks(List<LinkDto> linksToUpdate) {
        List<LinkDto> updatedLinks = new ArrayList<>();

        for (LinkDto linkDto : linksToUpdate) {
            Link link = Link.parse(linkDto.getUrl().toString());

            OffsetDateTime newUpdatedAt = null;
            if (link instanceof GitHubLink gitHubLink) {
                RepositoryResponse repositoryResponse =
                    gitHubClient.fetch(gitHubLink.getUserName(), gitHubLink.getRepoName());
                newUpdatedAt = repositoryResponse.updatedAt();
            } else if (link instanceof StackOverflowLink stackOverflowLink) {
                QuestionResponse questionResponse =
                    stackOverflowClient.fetchQuestion(stackOverflowLink.getQuestionId());
                newUpdatedAt = questionResponse.items().getFirst().lastActivityDate();
            }

            OffsetDateTime oldUpdatedAt = linkDto.getUpdatedAt().toInstant().atOffset(newUpdatedAt.getOffset());

            log.debug("Old updatedAt={}, new updatedAt={}", oldUpdatedAt, newUpdatedAt);

            if (newUpdatedAt.isAfter(oldUpdatedAt)) {
                linkService.updateUpdatedAt(linkDto.getUrl(), newUpdatedAt);
                // TODO: may be I have to update it there; but idk
                // TODO: like in repository to make url have last update time
                // TODO: or maybe it is not necessary!
                updatedLinks.add(linkDto);
            }

            linkService.updateCheckedAt(linkDto.getUrl(), OffsetDateTime.now());
        }

        return updatedLinks;
    }

    public List<LinkUpdateRequest> createLinkUpdateRequests(List<LinkDto> links) {
        List<LinkUpdateRequest> linkUpdateRequests = new ArrayList<>();

        for (LinkDto link : links) {
            linkUpdateRequests.add(new LinkUpdateRequest(
                link.getId(),
                link.getUrl(),
                "No description",
                link.getTrackedBy().stream().map(Chat::getChatId).toList()
            ));
        }

        return linkUpdateRequests;
    }
}
