package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends AbstractCommand {
    private final static String COMMAND = "/help";
    public static final String DESCRIPTION = "Prints help message";
    private final List<? extends AbstractCommand> commands;
    private final String message;

    @Autowired
    HelpCommand(List<? extends AbstractCommand> commands) {
        super(COMMAND, DESCRIPTION);
        this.commands = commands;
        this.message = CommandUtils.createHelpMessage(commands);
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        return new SendMessage(update.message().chat().id(), this.message);
    }
}
