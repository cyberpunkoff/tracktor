package edu.java.bot.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
