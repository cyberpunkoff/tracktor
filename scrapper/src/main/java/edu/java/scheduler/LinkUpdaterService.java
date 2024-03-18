package edu.java.scheduler;

import edu.java.LinkUpdateRequest;
import edu.java.clients.github.EventResponse;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.stackoverflow.QuestionResponse;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.dto.Chat;
import edu.java.dto.LinkDto;
import edu.java.service.LinkService;
import edu.link.links.GitHubLink;
import edu.link.links.Link;
import edu.link.links.StackOverflowLink;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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

    public List<LinkUpdateRequest> updateLinks(List<LinkDto> linksToUpdate) {
        List<LinkUpdateRequest> updateRequests = new ArrayList<>();

        for (LinkDto linkDto : linksToUpdate) {
            //TODO : properly fix this method
            // bcoz it is wrong now
            OffsetDateTime oldUpdatedAt = linkDto.getUpdatedAt().toInstant().atOffset(ZoneOffset.UTC);
            Link link = Link.parse(linkDto.getUrl().toString());
            String description = "New update";

            OffsetDateTime newUpdatedAt = OffsetDateTime.MIN;
            if (link instanceof GitHubLink gitHubLink) {
                List<EventResponse> events =
                    gitHubClient.fetchEvents(gitHubLink.getUserName(), gitHubLink.getRepoName());

                for (EventResponse event : events) {
                    if (event.createdAt().isAfter(oldUpdatedAt)) {
                        description = getGitHubEventDescription(event);
                    }

                    if (event.createdAt().isAfter(newUpdatedAt)) {
                        newUpdatedAt = event.createdAt();
                    }
                }

            } else if (link instanceof StackOverflowLink stackOverflowLink) {
                QuestionResponse questionResponse =
                    stackOverflowClient.fetchQuestion(stackOverflowLink.getQuestionId());
                description = "Question updated";
                newUpdatedAt = questionResponse.items().getFirst().lastActivityDate();
            }

            log.debug("Old updatedAt={}, new updatedAt={}", oldUpdatedAt, newUpdatedAt);

            if (newUpdatedAt.isAfter(oldUpdatedAt)) {
                linkService.updateUpdatedAt(linkDto.getUrl(), newUpdatedAt);
                // TODO: may be I have to update it there; but idk
                // TODO: like in repository to make url have last update time
                // TODO: or maybe it is not necessary!

                updateRequests.add(createLinkUpdateRequest(linkDto, description));
            }

            linkService.updateCheckedAt(linkDto.getUrl(), OffsetDateTime.now());
        }

        return updateRequests;
    }

    public LinkUpdateRequest createLinkUpdateRequest(LinkDto link, String description) {
        return new LinkUpdateRequest(
            link.getId(),
            link.getUrl(),
            description,
            link.getTrackedBy().stream().map(Chat::getChatId).toList()
        );
    }

    public String getGitHubEventDescription(EventResponse event) {
        return switch (event.type()) {
            case FORK -> "New fork of repository";
            case PULL_REQUEST -> "New pull request";
            case ISSUE -> "Issues update";
            case PUSH -> "New push in repository";
            case DEFAULT -> "Repository updated";
        };
    }
}
