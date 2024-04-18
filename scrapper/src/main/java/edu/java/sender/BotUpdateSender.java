package edu.java.sender;

import edu.java.LinkUpdateRequest;
import edu.java.clients.bot.BotClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BotUpdateSender implements UpdateSender {
    private final BotClient botClient;

    @Override
    public void send(LinkUpdateRequest update) {
        botClient.sendUpdate(update);
    }
}
