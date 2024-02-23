package edu.java.bot.command;

import edu.java.bot.model.UserMessage;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommandParserTest {
    @Test
    void testParseMessageWithArguments() {
        UserMessage message = CommandParser.parseMessage("/hello test");

        assertThat(message.getCommand()).isEqualTo("/hello");
        assertThat(message.getArguments()).isEqualTo(new String[] {"test"});
    }

    @Test
    void testParseMessageWithoutArguments() {
        UserMessage message = CommandParser.parseMessage("/hello");

        assertThat(message.getCommand()).isEqualTo("/hello");
        assertThat(message.getArguments()).isNull();
    }
}
