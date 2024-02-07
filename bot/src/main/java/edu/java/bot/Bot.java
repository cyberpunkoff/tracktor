package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScope;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandsScopeChat;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.command.Command;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.message.UserMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Slf4j
public class Bot implements UpdatesListener, AutoCloseable {
    private final UserMessageProcessor userMessageProcessor;
    private final ApplicationConfig applicationConfig;
    private TelegramBot bot;

    @Autowired
    public Bot(UserMessageProcessor userMessageProcessor, ApplicationConfig applicationConfig) {
        this.userMessageProcessor = userMessageProcessor;
        this.applicationConfig = applicationConfig;
    }

    void execute(BaseRequest request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        updates.stream().map(userMessageProcessor::process).forEach(this::execute);

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        log.info("Starting telegram bot...");
        bot = new TelegramBot(applicationConfig.telegramToken());
        bot.setUpdatesListener(this);

        SetMyCommands setMyCommands =
            new SetMyCommands(
                userMessageProcessor.getCommands().stream().map(Command::toApiCommand).toArray(BotCommand[]::new));
        execute(setMyCommands);
    }

    @Override
    public void close() {
        bot.shutdown();
    }
}
