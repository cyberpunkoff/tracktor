package edu.java.bot.service;

import edu.java.LinkUpdateRequest;
import edu.java.bot.message.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkUpdaterService implements LinkUpdater {
    private final MessageSender messageSender;

    @Override
    public int update(LinkUpdateRequest linkUpdateRequest) {
        String text = "Link " + linkUpdateRequest.url() + " updated!";
        linkUpdateRequest.tgChatIds().stream().forEach(chatId -> messageSender.sendMessage(chatId, text));
        return linkUpdateRequest.tgChatIds().size();
    }
}
