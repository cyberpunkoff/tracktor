package edu.java.bot.command;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class HelpCommandTest extends AbstractCommandTest {
    @InjectMocks
    private HelpCommand helpCommand;
    @Mock
    List<AbstractCommand> commands;

    @Test
    void helpCommandOutputTest() {
        Pattern commandDescriptionPattern = Pattern.compile("/[a-zA-Z]* [a-zA-Z ]*");

        when(message.text()).thenReturn("/help");

        String[] helpMessageLines = helpCommand.handle(update).getParameters().get("text").toString().split("\n");
        Arrays.stream(helpMessageLines).skip(1).forEach(
            line -> assertThat(line).matches(commandDescriptionPattern)
        );
    }
}
