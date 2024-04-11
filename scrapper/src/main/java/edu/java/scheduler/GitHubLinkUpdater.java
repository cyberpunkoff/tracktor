package edu.java.scheduler;

import edu.java.LinkUpdateRequest;
import edu.java.clients.github.EventResponse;
import edu.java.clients.github.GitHubClient;
import edu.java.dto.Chat;
import edu.java.dto.LinkDto;
import edu.link.links.GitHubLink;
import edu.link.links.Link;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class GitHubLinkUpdater extends LinkUpdater {
    private OffsetDateTime updatedAt;
    private final GitHubClient gitHubClient;
    private LinkDto link;
    private boolean isLinkUpdated;
    List<LinkUpdateRequest> updates;

    public GitHubLinkUpdater(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public void updateLink(LinkDto link) {
        this.link = link;

        GitHubLink gitHubLink = (GitHubLink) Link.parse(link.getUrl().toString());
        OffsetDateTime oldUpdatedAt = link.getUpdatedAt().toLocalDateTime().atOffset(ZoneOffset.UTC);

        updatedAt = OffsetDateTime.MIN;
        List<EventResponse> events = gitHubClient.fetchEvents(gitHubLink.getUserName(), gitHubLink.getRepoName());

        for (EventResponse event : events) {
            if (event.createdAt().isAfter(oldUpdatedAt)) {
                updates.add(createLinkUpdateRequest(event));
            }

            if (event.createdAt().isAfter(updatedAt)) {
                updatedAt = event.createdAt();
            }
        }

        isLinkUpdated = updatedAt.isAfter(oldUpdatedAt);
    }

    private LinkUpdateRequest createLinkUpdateRequest(EventResponse event) {
        return new LinkUpdateRequest(
            link.getId(),
            link.getUrl(),
            getGitHubEventDescription(event),
            link.getTrackedBy().stream().map(Chat::getChatId).toList()
        );
    }

    private String getGitHubEventDescription(EventResponse event) {
        return switch (event.type()) {
            case FORK -> "New fork of repository";
            case PULL_REQUEST -> "New pull request";
            case ISSUE -> "Issues update";
            case PUSH -> "New push in repository";
            case DEFAULT -> "Repository updated";
        };
    }

    @Override
    public List<LinkUpdateRequest> getLinkUpdates() {
        return updates;
    }

    @Override
    public boolean isLinkUpdated() {
        return isLinkUpdated;
    }

    @Override
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
