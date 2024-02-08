package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.UserMessage;
import edu.java.bot.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand extends AbstractCommand {
    public static final String COMMAND = "/track";
    public static final String DESCRIPTION = "Add link to follow list";
    private final LinkService linkService;

    @Autowired
    TrackCommand(LinkService linkService) {
        super(COMMAND, DESCRIPTION);
        this.linkService = linkService;
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        UserMessage message = CommandParser.parseMessage(update.message().text());
        linkService.addLink(update.message().from().id(), message.getArguments()[0]); // TODO: fix this
        return new SendMessage(update.message().chat().id(), "Added your link to list!");
    }
}
