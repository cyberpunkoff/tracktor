package edu.java.bot.service;

import edu.java.bot.model.controller.LinkUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public class LinkUpdaterService implements LinkUpdater {
    @Override
    public int update(LinkUpdateRequest linkUpdateRequest) {
        linkUpdateRequest.tgChatIds().stream().forEach(send message);

        return linkUpdateRequest.tgChatIds().size();
    }
}
