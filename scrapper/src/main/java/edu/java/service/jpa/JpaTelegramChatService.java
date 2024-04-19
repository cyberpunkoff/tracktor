package edu.java.service.jpa;

import edu.java.entity.ChatEntity;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.service.TelegramChatService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaTelegramChatService implements TelegramChatService {
    private final JpaChatRepository chatRepository;

    @Override
    public void register(long tgChatId) {
        chatRepository.save(new ChatEntity(tgChatId));
    }

    @Override
    public void unregister(long tgChatId) {
        chatRepository.delete(new ChatEntity(tgChatId));
    }
}
