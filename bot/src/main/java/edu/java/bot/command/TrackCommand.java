package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.AddLinkRequest;
import edu.java.bot.clients.scrapper.ScrapperClient;
import edu.java.bot.model.UserMessage;
import edu.java.bot.service.LinkService;
import edu.link.links.Link;
import java.net.URI;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand extends AbstractCommand {
    private final ScrapperClient scrapperClient;
    public static final String COMMAND = "/track";
    public static final String DESCRIPTION = "Add link to follow list";
    private final LinkService linkService;

    TrackCommand(ScrapperClient scrapperClient, LinkService linkService) {
        super(COMMAND, DESCRIPTION);
        this.scrapperClient = scrapperClient;
        this.linkService = linkService;
    }

    @Override
    @SneakyThrows // TODO: remove this!!!
    public SendMessage handle(Update update) {
        logMessage(update);
        UserMessage message = CommandParser.parseMessage(update.message().text());

        Long chatId = update.message().chat().id();
        if (message.getArguments() == null || message.getArguments().length != 1) {
            return new SendMessage(chatId, "Invalid format! Please use /track <url>");
        }

        String url = message.getArguments()[0];
        Link link = Link.parse(url);

        if (link != null) {
//            linkService.addLink(update.message().from().id(), link);
            scrapperClient.trackLink(chatId, new AddLinkRequest(new URI(link.getUrl())));
            return new SendMessage(chatId, "Added your link to list!");
        } else {
            return new SendMessage(chatId, "Unsupported link");
        }
    }
}
