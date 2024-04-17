package edu.java.bot.command;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class StartCommandTest extends AbstractCommandTest {
    @InjectMocks
    private StartCommand startCommand;

    @Test
    void startCommandTest() {
        when(message.text()).thenReturn("/start");


        String startCommandMessage = startCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo(StartCommand.MESSAGE);
    }
}
