package edu.java.bot.command;

import edu.java.bot.service.LinkService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ListCommandTest extends AbstractCommandTest {
    @InjectMocks
    private ListCommand listCommand;
    @Mock
    private LinkService linkService;

    @Test
    void testNoLinksTrackedMessage() {
        when(message.text()).thenReturn("/track");

        String startCommandMessage = listCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("You haven't added any links yet");
    }
}
