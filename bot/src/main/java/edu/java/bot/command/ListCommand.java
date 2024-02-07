package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class ListCommand extends AbstractCommand {
    public static final String COMMAND = "/list";
    public static final String DESCRIPTION = "Lists all links that you follow";

    ListCommand() {
        super(COMMAND, DESCRIPTION);
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        return new SendMessage(update.message().chat().id(), "Not implemented yet");
    }
}
