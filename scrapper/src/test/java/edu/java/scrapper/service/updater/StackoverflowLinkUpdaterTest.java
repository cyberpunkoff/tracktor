package edu.java.scrapper.service.updater;

import edu.java.clients.stackoverflow.QuestionResponse;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.dto.LinkDto;
import edu.java.service.updater.StackoverflowLinkUpdater;
import java.net.URI;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StackoverflowLinkUpdaterTest {
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
    static QuestionResponse questionResponse = new QuestionResponse(
        List.of(
            new QuestionResponse.ItemResponse(1L, 1, 1, DEFAULT_TIME, "test")
        )
    );

    @Test
    void testUpdateLink_shouldContainUpdates() {
        StackOverflowClient stackOverflowClient = mock(StackOverflowClient.class);
        when(stackOverflowClient.fetchQuestion(anyLong())).thenReturn(questionResponse);
        StackoverflowLinkUpdater stackoverflowLinkUpdater = new StackoverflowLinkUpdater(stackOverflowClient);

        LinkDto link = new LinkDto(
            1L,
            URI.create("https://stackoverflow.com/questions/123/test"),
            Timestamp.from(DEFAULT_TIME.minusDays(1).toInstant()),
            Timestamp.from(DEFAULT_TIME.toInstant())
        );

        stackoverflowLinkUpdater.updateLink(link);

        assertThat(stackoverflowLinkUpdater.getLinkUpdates()).size().isEqualTo(1);
        assertThat(stackoverflowLinkUpdater.isLinkUpdated()).isTrue();
    }

    @Test
    void testUpdateLink_shouldBeEmpty() {
        StackOverflowClient stackOverflowClient = mock(StackOverflowClient.class);
        when(stackOverflowClient.fetchQuestion(anyLong())).thenReturn(questionResponse);
        StackoverflowLinkUpdater stackoverflowLinkUpdater = new StackoverflowLinkUpdater(stackOverflowClient);

        LinkDto link = new LinkDto(
            1L,
            URI.create("https://stackoverflow.com/questions/123/test"),
            Timestamp.from(DEFAULT_TIME.plusDays(1).toInstant()),
            Timestamp.from(DEFAULT_TIME.toInstant())
        );

        stackoverflowLinkUpdater.updateLink(link);

        assertThat(stackoverflowLinkUpdater.getLinkUpdates()).isEmpty();
        assertThat(stackoverflowLinkUpdater.isLinkUpdated()).isFalse();
    }
}
