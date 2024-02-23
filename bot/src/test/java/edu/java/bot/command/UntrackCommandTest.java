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
public class UntrackCommandTest {
    @Autowired
    private UntrackCommand untrackCommand;

    @Test
    void testUntrackIncorrectIndex() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/untrack invalid");
        when(user.firstName()).thenReturn("Test");
        when(update.message().chat()).thenReturn(new Chat());
        when(update.message().from()).thenReturn(user);

        String startCommandMessage = untrackCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("Invalid index");
    }

    @Test
    void testUntrackNonExistentIndex() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/untrack 5");
        when(user.firstName()).thenReturn("Test");
        when(update.message().chat()).thenReturn(new Chat());
        when(update.message().from()).thenReturn(user);

        String startCommandMessage = untrackCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("Index not found");
    }
}
