package edu.java.bot.message;

import com.pengrad.telegrambot.model.LinkPreviewOptions;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSender {
    private final Bot bot;

    public void sendMessage(Long chatId, String text) {
        bot.execute(new SendMessage(chatId, text));
    }

    public void sendMessageWithoutLinkPreview(Long chatId, String text) {
        bot.execute(new SendMessage(chatId, text).linkPreviewOptions(new LinkPreviewOptions().isDisabled(true)));
    }
}
