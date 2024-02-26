package edu.java.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

public abstract class AbstractCommandTest {
    @Mock
    protected Update update;
    @Mock
    protected Message message;
    @Mock
    protected User user;

    @BeforeEach
    void setup() {
        when(update.message()).thenReturn(message);
        when(user.firstName()).thenReturn("Test");
        when(update.message().chat()).thenReturn(new Chat());
        when(update.message().from()).thenReturn(user);
    }
}
