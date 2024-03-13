package edu.java.bot.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.AbstractCommand;
import edu.java.bot.command.CommandParser;
import edu.java.bot.model.UserMessage;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Data
public class UserMessageProcessor {
    private Map<String, ? extends AbstractCommand> handlers;

    @Autowired
    public UserMessageProcessor(List<? extends AbstractCommand> commands) {
        handlers = commands.stream()
            .collect(Collectors.toMap(AbstractCommand::getCommand, Function.identity()));
    }

    public SendMessage process(Update update) {
        UserMessage userMessage = CommandParser.parseMessage(update.message().text());
        log.info("Got new message! From: " + update.message().from().firstName());

        SendMessage response = handlers.get(userMessage.getCommand()).handle(update);

        if (response == null) {
            response = new SendMessage(update.message().chat().id(), "Invalid command!");
        }

        return response;
    }
}
