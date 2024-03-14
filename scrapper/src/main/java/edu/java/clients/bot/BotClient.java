package edu.java.clients.bot;

import edu.java.LinkUpdateRequest;

public interface BotClient {
    void sendUpdate(LinkUpdateRequest linkUpdateRequest);
}
