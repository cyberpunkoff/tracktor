package edu.java.repository.jdbc;

import edu.java.dto.Chat;
import edu.java.repository.ChatDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class JdbcChatDao implements ChatDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcChatDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Chat> findAll() {
        return jdbcTemplate.query("select * from chats", ROW_MAPPER);
    }

    @Override
    public void add(Chat chat) {
        jdbcTemplate.update("insert into chats (chat_id) values (?)", chat.getChatId());
    }

    @Override
    public void remove(Chat chat) {
        jdbcTemplate.update("delete from chats where chat_id = ?", chat.getChatId());

    }
}
