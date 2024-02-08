package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand extends AbstractCommand {
    public static final String COMMAND = "/list";
    public static final String DESCRIPTION = "Lists all links that you follow";
    private final LinkService linkService;

    @Autowired
    ListCommand(LinkService linkService) {
        super(COMMAND, DESCRIPTION);
        this.linkService = linkService;
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        String message = CommandUtils.createListMessage(
                linkService.getLinks(update.message().from().id())
        );
        return new SendMessage(update.message().chat().id(), message);
    }
}
