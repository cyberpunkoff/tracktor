package edu.java.service.jdbc;

import edu.java.dto.Chat;
import edu.java.exceptions.ChatAlreadyRegisteredException;
import edu.java.repository.ChatDao;
import edu.java.service.TelegramChatService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcTelegramChatService implements TelegramChatService {
    private final ChatDao chatRepository;

    @Override
    @SneakyThrows
    @Transactional
    public void register(long tgChatId) {
        try {
            chatRepository.add(new Chat(tgChatId));
        } catch (DuplicateKeyException e) {
            throw new ChatAlreadyRegisteredException();
        }
    }

    @Override
    @Transactional
    public void unregister(long tgChatId) {
        chatRepository.remove(new Chat(tgChatId));
    }
}
