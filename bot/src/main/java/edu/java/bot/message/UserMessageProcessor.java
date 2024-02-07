package edu.java.bot.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.AbstractCommand;
import edu.java.bot.command.Command;
import edu.java.bot.command.CommandParser;
import edu.java.bot.model.UserMessage;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Data
public class UserMessageProcessor {
    private final List<? extends AbstractCommand> commands;

    @Autowired
    public UserMessageProcessor(List<? extends AbstractCommand> commands) {
        this.commands = commands;
    }

    public SendMessage process(Update update) {
        UserMessage userMessage = CommandParser.parseMessage(update.message().text());
        log.info("Got new message! From: " + update.message().from().firstName());

        for (Command command : commands) {
            if (command.getCommand().equals(userMessage.getCommand())) {
                return command.handle(update);
            }
        }

        return new SendMessage(update.message().chat().id(), "Invalid command!");
    }

}
