package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand extends AbstractCommand {
    public static final String COMMAND = "/untrack";
    public static final String DESCRIPTION = "Removes link from track list";

    UntrackCommand() {
        super(COMMAND, DESCRIPTION);
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        return new SendMessage(update.message().chat().id(), "Not implemented yet");
    }
}
