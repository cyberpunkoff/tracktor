package edu.java.scrapper.service.jpa;

import edu.java.repository.jpa.JpaChatRepository;
import edu.java.scrapper.IntegrationEnvironment;
import edu.java.service.TelegramChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JpaTelegramChatServiceTest extends IntegrationEnvironment {
    @Autowired
    TelegramChatService jpaTelegramChatService;
    @Autowired
    JpaChatRepository jpaChatRepository;

    @DynamicPropertySource
    static void dynamicConfiguration(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jpa");
    }

    @Test
    @Transactional
    @Rollback
    void registerTest() {
        Long chatId = 1L;

        jpaTelegramChatService.register(chatId);

        assertThat(jpaChatRepository.findFirstById(chatId)).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void unregisterTest() {
        Long chatId = 1L;

        jpaTelegramChatService.register(chatId);

        jpaTelegramChatService.unregister(chatId);

        assertThat(jpaChatRepository.findFirstById(chatId)).isNull();
    }
}
