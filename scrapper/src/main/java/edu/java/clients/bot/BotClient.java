package edu.java.clients.bot;

import edu.java.model.client.LinkUpdateRequest;

public interface BotClient {
    void sendUpdate(LinkUpdateRequest linkUpdateRequest);
}
