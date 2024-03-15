package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.scrapper.ScrapperClient;
import edu.java.bot.model.UserMessage;
import edu.java.bot.service.LinkService;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand extends AbstractCommand {
    private final ScrapperClient scrapperClient;
    public static final String COMMAND = "/untrack";
    public static final String DESCRIPTION = "Removes link from track list";

    private final LinkService linkService;

    UntrackCommand(ScrapperClient scrapperClient, LinkService linkService) {
        super(COMMAND, DESCRIPTION);
        this.scrapperClient = scrapperClient;
        this.linkService = linkService;
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        UserMessage message = CommandParser.parseMessage(update.message().text());
        Long userId = update.message().chat().id();

        if (message.getArguments() == null || message.getArguments().length != 1) {
            return new SendMessage(
                update.message().chat().id(),
                "Invalid format! Please use /untrack <id>, where id from /list"
            );
        }

        int linkId;
        try {
            linkId = Integer.parseInt(message.getArguments()[0]) - 1;
        } catch (NumberFormatException e) {
            return new SendMessage(userId, "Invalid index");
        }

        if (linkService.containsLinkId(userId, linkId)) {
            linkService.removeLinkById(userId, linkId);
            return new SendMessage(userId, "Removed link!");
        } else {
            return new SendMessage(update.message().chat().id(), "Index not found");
        }
    }
}
