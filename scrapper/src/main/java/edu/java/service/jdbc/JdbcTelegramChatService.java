package edu.java.service.jdbc;

import edu.java.dto.Chat;
import edu.java.repository.ChatDao;
import edu.java.service.TelegramChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcTelegramChatService implements TelegramChatService {
    private final ChatDao chatRepository;


    @Override
    public void register(long tgChatId) {
        chatRepository.add(new Chat(tgChatId));
    }

    @Override
    public void unregister(long tgChatId) {
        chatRepository.remove(new Chat(tgChatId));
    }
}
