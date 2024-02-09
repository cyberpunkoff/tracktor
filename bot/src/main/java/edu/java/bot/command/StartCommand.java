package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand extends AbstractCommand {
    public static final String COMMAND = "/start";
    public static final String DESCRIPTION = "Starts the bot";
    public static final String MESSAGE = "This bot allows you to track updated "
            + "on your links! Use /help to get started.";

    StartCommand() {
        super(COMMAND, DESCRIPTION);
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        return new SendMessage(update.message().chat().id(), MESSAGE);
    }
}
