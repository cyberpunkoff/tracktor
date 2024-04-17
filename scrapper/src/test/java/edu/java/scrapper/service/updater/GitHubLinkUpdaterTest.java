package edu.java.scrapper.service.updater;

import edu.java.clients.github.EventResponse;
import edu.java.clients.github.GitHubClient;
import edu.java.dto.LinkDto;
import edu.java.service.updater.GitHubLinkUpdater;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.net.URI;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class GitHubLinkUpdaterTest {
    static OffsetDateTime DEFAULT_TIME = OffsetDateTime.of(
        2020,
        10,
        7,
        10,
        15,
        12,
        0,
        ZoneOffset.UTC
    );
    static List<EventResponse> mockEvents = List.of(
        new EventResponse(
            EventResponse.EventType.FORK,
            DEFAULT_TIME
        ),
        new EventResponse(
            EventResponse.EventType.ISSUE,
            DEFAULT_TIME
        )
    );

    @Test
    void testGetLinkUpdates_shouldHaveUpdates() {
        GitHubClient gitHubClient = mock(GitHubClient.class);
        when(gitHubClient.fetchEvents(anyString(), anyString())).thenReturn(mockEvents);

        GitHubLinkUpdater gitHubLinkUpdater = new GitHubLinkUpdater(gitHubClient);
        LinkDto link = new LinkDto(
            1L,
            URI.create("https://github.com/testuser/testrepo"),
            Timestamp.from(DEFAULT_TIME.minusDays(1).toInstant()),
            Timestamp.from(DEFAULT_TIME.toInstant())
        );

        gitHubLinkUpdater.updateLink(link);

        assertThat(gitHubLinkUpdater.isLinkUpdated()).isTrue();
        assertThat(gitHubLinkUpdater.getLinkUpdates()).size().isEqualTo(mockEvents.size());
    }

    @Test
    void testGetLinkUpdates_shouldBeEmpty() {
        GitHubClient gitHubClient = mock(GitHubClient.class);
        when(gitHubClient.fetchEvents(anyString(), anyString())).thenReturn(mockEvents);

        GitHubLinkUpdater gitHubLinkUpdater = new GitHubLinkUpdater(gitHubClient);
        LinkDto link = new LinkDto(
            1L,
            URI.create("https://github.com/testuser/testrepo"),
            Timestamp.from(DEFAULT_TIME.plusDays(1).toInstant()),
            Timestamp.from(DEFAULT_TIME.toInstant())
        );

        gitHubLinkUpdater.updateLink(link);

        assertThat(gitHubLinkUpdater.isLinkUpdated()).isFalse();
        assertThat(gitHubLinkUpdater.getLinkUpdates()).isEmpty();
    }
}
