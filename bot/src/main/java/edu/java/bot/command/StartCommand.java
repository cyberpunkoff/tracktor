package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.scrapper.ScrapperClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class StartCommand extends AbstractCommand {
    private final ScrapperClient scrapperClient;
    public static final String COMMAND = "/start";
    public static final String DESCRIPTION = "Starts the bot";
    public static final String MESSAGE = "This bot allows you to track updated "
        + "on your links! Use /help to get started.";

    StartCommand(ScrapperClient scrapperClient) {
        super(COMMAND, DESCRIPTION);
        this.scrapperClient = scrapperClient;
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        try {
            scrapperClient.addChat(update.message().chat().id());
            return new SendMessage(update.message().chat().id(), MESSAGE);
        } catch (WebClientResponseException e) {
            return new SendMessage(update.message().chat().id(), "You are already registered!");
        }
    }
}
