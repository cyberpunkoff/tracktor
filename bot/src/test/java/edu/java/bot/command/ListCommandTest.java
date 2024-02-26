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
