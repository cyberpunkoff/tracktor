package edu.java.bot.command;

import edu.java.bot.service.LinkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrackCommandTest extends AbstractCommandTest {
    @InjectMocks
    private TrackCommand trackCommand;
    @Mock
    private LinkService linkService;

    @Test
    void testTrackInvalidUrl() {
        when(message.text()).thenReturn("/track invalid url");

        String startCommandMessage = trackCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("Invalid format! Please use /track <url>");
    }

    @Test
    void testTrackValidUrl() {
        when(message.text()).thenReturn("/track https://github.com/octocat/Hello-World");

        String startCommandMessage = trackCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("Added your link to list!");
    }
}
