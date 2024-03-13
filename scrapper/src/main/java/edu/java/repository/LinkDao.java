package edu.java.repository;

import edu.java.dto.Chat;
import edu.java.dto.Link;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public interface LinkDao {
    ResultSetExtractor<List<Link>> SET_EXTRACTOR = (ResultSet resultSet) -> {
        Map<Long, Link> linkById = new HashMap<>();

        while (resultSet.next()) {
            Long chatId = resultSet.getLong("chat_id");

            Long id = resultSet.getLong("id");
            Timestamp updatedAt = resultSet.getTimestamp("updated_at");
            Timestamp checkedAt = resultSet.getTimestamp("checked_at");
            URI url;
            try {
                url = new URI(resultSet.getString("url"));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            Link link = linkById.computeIfAbsent(id, k -> new Link(id, url, updatedAt, checkedAt));

            link.getTrackedBy().add(new Chat(chatId));
        }

        return new ArrayList<>(linkById.values());
    };

    RowMapper<Link> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        Long id = resultSet.getLong("id");
        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        Timestamp checkedAt = resultSet.getTimestamp("checked_at");
        URI url;
        try {
            url = new URI(resultSet.getString("url"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return new Link(id, url, updatedAt, checkedAt);
    };

    List<Link> findAll(Long tgChatId);

    List<Link> findAllCheckedLaterThan(Duration duration);

    Link add(URI url, Long chatId);

    Link get(Long id);

    void updateCheckedAt(URI url, Timestamp timestamp);

    void updateUpdatedAt(URI url, Timestamp timestamp);

    Link get(URI url);

    Link remove(URI url, Long id);
}
