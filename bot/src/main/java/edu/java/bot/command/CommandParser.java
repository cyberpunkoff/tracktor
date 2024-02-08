package edu.java.bot.command;

import edu.java.bot.model.UserMessage;
import java.util.Arrays;

public final class CommandParser {
    private static final String COMMAND_PREFIX = "/";

    private CommandParser() {
    }

    public static boolean isCommand(String message) {
        return message.startsWith(COMMAND_PREFIX);
    }

    public static UserMessage parseMessage(String message) {
        String[] parts = message.split(" ");
        // TODO: add checks for invalid message structure
        return UserMessage.builder()
            .command(parts[0])
            .arguments(Arrays.copyOfRange(parts, Math.min(1, parts.length - 1), parts.length))
            .build();
    }
}
