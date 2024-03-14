package edu.java.scheduler;

import edu.java.LinkUpdateRequest;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.RepositoryResponse;
import edu.java.clients.stackoverflow.QuestionResponse;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.dto.Chat;
import edu.java.dto.LinkDto;
import edu.java.repository.LinkDao;
import edu.java.service.LinkService;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkUpdaterService {
    private final LinkDao linkRepository;
    private final GitHubClient gitHubClient;
    private final LinkService linkService;
    private final StackOverflowClient stackOverflowClient;

    public List<LinkDto> getUpdatedLinks(List<LinkDto> linksToUpdate) {
        List<LinkDto> updatedLinks = new ArrayList<>();

        for (LinkDto linkDto : linksToUpdate) {
            LinkDto link = LinkDto.parse(linkDto.getUrl().toString());

            Timestamp newUpdatedAt = null;
            if (link instanceof GitHubLink gitHubLink) {
                RepositoryResponse repositoryResponse =
                    gitHubClient.fetch(gitHubLink.getUser(), gitHubLink.getRepository());
                newUpdatedAt = Timestamp.from(Instant.from(repositoryResponse.updatedAt()));
            } else if (link instanceof StackOverflowLink stackOverflowLink) {
                QuestionResponse questionResponse = stackOverflowClient.fetchQuestion(stackOverflowLink.getId());
                newUpdatedAt = Timestamp.from(Instant.from(questionResponse.items().getFirst().lastActivityDate()));
            }

            if (newUpdatedAt.after(linkDto.getUpdatedAt())) {
                linkService.updateUpdatedAt(linkDto.getUrl(), newUpdatedAt);
                // TODO: may be I have to update it there; but idk
                // TODO: like in repository to make url have last update time
                // TODO: or maybe it is not necessary!
                updatedLinks.add(linkDto);
            }

            linkService.updateCheckedAt(linkDto.getUrl(), Timestamp.from(Instant.now()));
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
