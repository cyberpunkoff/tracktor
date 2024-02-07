package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends AbstractCommand {
    private final static String COMMAND = "/help";
    public static final String DESCRIPTION = "Prints help message";

    HelpCommand() {
        super(COMMAND, DESCRIPTION);
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        return new SendMessage(update.message().chat().id(), "Not implemented yet");
    }
}
