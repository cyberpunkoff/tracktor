package edu.java.bot;

import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.command.Command;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.message.UserMessageProcessor;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bot implements UpdatesListener, ExceptionHandler, AutoCloseable {
    private final UserMessageProcessor userMessageProcessor;
    private final ApplicationConfig applicationConfig;
    private TelegramBot bot;

    @Autowired
    public Bot(UserMessageProcessor userMessageProcessor, ApplicationConfig applicationConfig) {
        this.userMessageProcessor = userMessageProcessor;
        this.applicationConfig = applicationConfig;
    }

    <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.stream().map(userMessageProcessor::process).forEach(this::execute);
        } catch (Exception e) {
            handleException(e);
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        log.info("Starting telegram bot...");

        bot = new TelegramBot(applicationConfig.telegramToken());
        bot.setUpdatesListener(this, this);

        SetMyCommands setMyCommands =
            new SetMyCommands(
                userMessageProcessor.getCommands().stream().map(Command::toApiCommand).toArray(BotCommand[]::new));
        execute(setMyCommands);
    }

    @Override
    public void close() {
        bot.shutdown();
    }

    @Override
    public void onException(TelegramException e) {
        handleException(e);
    }

    private void handleException(Exception e) {
        if (e instanceof TelegramException te && te.response() != null) {
            log.error("Telegram API exception: {} - {}", te.response().errorCode(), te.response().description());
        } else {
            log.error(e.toString());
        }
    }
}
