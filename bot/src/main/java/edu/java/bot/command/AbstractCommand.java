package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractCommand implements Command {
    private final String command;
    private final String description;

    AbstractCommand(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public void logMessage(Update update) {
        log.info(
            "Received message from {} with text {}",
            update.message().from().firstName(),
            update.message().text());
    }

    @Override
    public String getCommand() {
        return this.command;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
