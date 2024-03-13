package edu.java.repository;

import edu.java.dto.Chat;
import java.sql.ResultSet;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

public interface ChatDao {
    RowMapper<Chat> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> new Chat(
        resultSet.getLong("chat_id")
    );

    List<Chat> findAll();

    void add(Chat chat);

    void remove(Chat chat);
}
