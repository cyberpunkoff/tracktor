package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.regex.Pattern;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelpCommandTest {
    @Autowired
    private HelpCommand helpCommand;

    @Test
    public void helpCommandOutputTest() {
        Pattern commandDescriptionPattern = Pattern.compile("/[a-zA-Z]* [a-zA-Z ]*");

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/help");
        when(user.firstName()).thenReturn("Test");
        when(update.message().chat()).thenReturn(new Chat());
        when(update.message().from()).thenReturn(user);

        String[] helpMessageLines = helpCommand.handle(update).getParameters().get("text").toString().split("\n");
        Arrays.stream(helpMessageLines).skip(1).forEach(
            line -> assertThat(line).matches(commandDescriptionPattern)
        );
    }
}
