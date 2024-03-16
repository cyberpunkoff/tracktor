package edu.java.scrapper.repository;

import edu.java.dto.Chat;
import edu.java.repository.ChatDao;
import edu.java.scrapper.IntegrationEnvironment;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
public class ChatDaoTest extends IntegrationEnvironment {
    @Autowired
    ChatDao chatDao;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    void addTest() {
        Chat chat = new Chat(123L);

        chatDao.add(chat);

        Long insertedId = jdbcTemplate.queryForObject("SELECT chat_id FROM chats LIMIT 1", Long.class);

        assertThat(insertedId).isEqualTo(chat.getChatId());
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    void removeTest() {
        Chat chat = new Chat(123L);

        jdbcTemplate.update("INSERT INTO chats values (?)", chat.getChatId());

        chatDao.remove(chat);

        assertThatExceptionOfType(EmptyResultDataAccessException.class).isThrownBy(
            () -> jdbcTemplate.queryForObject("SELECT chat_id FROM chats", Long.class)
        );
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    void findAllTest() {
        Chat chat = new Chat(123L);
        Chat otherChat = new Chat(124L);

        jdbcTemplate.update("INSERT INTO chats values (?)", chat.getChatId());
        jdbcTemplate.update("INSERT INTO chats values (?)", otherChat.getChatId());

        List<Chat> chats = chatDao.findAll();

        assertThat(chats).size().isEqualTo(2);
    }
}
