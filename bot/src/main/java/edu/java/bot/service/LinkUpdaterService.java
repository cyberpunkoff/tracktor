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
        String text = linkUpdateRequest.description() + "\nLink: " + linkUpdateRequest.url();
        linkUpdateRequest.tgChatIds().forEach(
            chatId -> messageSender.sendMessageWithoutLinkPreview(chatId, text)
        );
        return linkUpdateRequest.tgChatIds().size();
    }
}
