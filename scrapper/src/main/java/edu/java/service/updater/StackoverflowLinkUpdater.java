package edu.java.service.updater;

import edu.java.LinkUpdateRequest;
import edu.java.clients.stackoverflow.QuestionResponse;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.dto.Chat;
import edu.java.dto.LinkDto;
import edu.link.links.Link;
import edu.link.links.StackOverflowLink;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class StackoverflowLinkUpdater extends LinkUpdater {
    private final StackOverflowClient stackOverflowClient;
    private OffsetDateTime updatedAt;
    private boolean isLinkUpdated;
    private LinkDto link;
    List<LinkUpdateRequest> updates = new ArrayList<>();

    public StackoverflowLinkUpdater(StackOverflowClient stackOverflowClient) {
        this.stackOverflowClient = stackOverflowClient;
    }

    public void updateLink(LinkDto link) {
        this.link = link;

        StackOverflowLink stackOverflowLink = (StackOverflowLink) Link.parse(link.getUrl().toString());
        QuestionResponse questionResponse = stackOverflowClient.fetchQuestion(
            stackOverflowLink.getQuestionId()
        );

        this.updatedAt = questionResponse.items().getFirst().lastActivityDate();
        isLinkUpdated = updatedAt.isAfter(link.getUpdatedAt().toInstant().atOffset(ZoneOffset.UTC));

        if (isLinkUpdated) {
            String description = "Question updated";
            updates.add(createLinkUpdateRequest(description));
        }
    }

    private LinkUpdateRequest createLinkUpdateRequest(String description) {
        return new LinkUpdateRequest(
            link.getId(),
            link.getUrl(),
            description,
            link.getTrackedBy().stream().map(Chat::getChatId).toList()
        );
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
