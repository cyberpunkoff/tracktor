package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TrackCommandTest {
    @Autowired
    private TrackCommand trackCommand;

    @Test
    void testTrackInvalidUrl() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/track invalid url");
        when(user.firstName()).thenReturn("Test");
        when(update.message().chat()).thenReturn(new Chat());
        when(update.message().from()).thenReturn(user);

        String startCommandMessage = trackCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("Invalid format! Please use /track <url>");
    }

    @Test
    void testTrackValidUrl() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/track https://github.com/octocat/Hello-World");
        when(user.firstName()).thenReturn("Test");
        when(update.message().chat()).thenReturn(new Chat());
        when(update.message().from()).thenReturn(user);

        String startCommandMessage = trackCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("Added your link to list!");
    }
}
