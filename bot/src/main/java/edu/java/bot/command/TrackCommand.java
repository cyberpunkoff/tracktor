package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.link.links.Link;
import edu.java.bot.model.UserMessage;
import edu.java.bot.service.LinkService;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand extends AbstractCommand {
    public static final String COMMAND = "/track";
    public static final String DESCRIPTION = "Add link to follow list";
    private final LinkService linkService;

    TrackCommand(LinkService linkService) {
        super(COMMAND, DESCRIPTION);
        this.linkService = linkService;
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        UserMessage message = CommandParser.parseMessage(update.message().text());

        if (message.getArguments() == null || message.getArguments().length != 1) {
            return new SendMessage(update.message().chat().id(), "Invalid format! Please use /track <url>");
        }

        String url = message.getArguments()[0];
        Link link = Link.parse(url);

        if (link != null) {
            linkService.addLink(update.message().from().id(), link);
            return new SendMessage(update.message().chat().id(), "Added your link to list!");
        } else {
            return new SendMessage(update.message().chat().id(), "Unsupported link");
        }
    }
}
