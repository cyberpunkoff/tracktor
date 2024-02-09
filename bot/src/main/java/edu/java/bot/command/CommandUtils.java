package edu.java.bot.command;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CommandUtils {
    private CommandUtils() {
    }

    public static String createHelpMessage(List<? extends AbstractCommand> commands) {
        StringBuilder message = new StringBuilder();
        message.append("Available commands\n");
        message.append(
            commands.stream()
                .map(command -> command.getCommand() + " " + command.getDescription())
                .collect(Collectors.joining("\n"))
        );
        return message.toString();
    }

    public static String createListMessage(List<String> links) {
        if (links == null) {
            return "You haven't added any links yet";
        }

        StringBuilder message = new StringBuilder();
        message.append("Tracking links\n");
        message.append(
            IntStream.range(0, links.size())
                .mapToObj(i -> i + 1 + ") " + links.get(i))
                .collect(Collectors.joining("\n"))
        );
        return message.toString();
    }
}
