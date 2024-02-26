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
public class UntrackCommandTest extends AbstractCommandTest {
    @InjectMocks
    private UntrackCommand untrackCommand;
    @Mock
    private LinkService linkService;

    @Test
    void testUntrackIncorrectIndex() {
        when(message.text()).thenReturn("/untrack invalid");

        String startCommandMessage = untrackCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("Invalid index");
    }

    @Test
    void testUntrackNonExistentIndex() {
        when(message.text()).thenReturn("/untrack 5");

        String startCommandMessage = untrackCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("Index not found");
    }
}
