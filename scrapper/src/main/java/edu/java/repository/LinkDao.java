package edu.java.repository;

import edu.java.dto.Chat;
import edu.java.dto.LinkDto;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public interface LinkDao {
    String CHECKED_AT_LABEL = "checked_at";
    String UPDATED_AT_LABEL = "updated_at";
    String URL_LABEL = "url";

    ResultSetExtractor<List<LinkDto>> SET_EXTRACTOR = (ResultSet resultSet) -> {
        Map<Long, LinkDto> linkById = new HashMap<>();

        while (resultSet.next()) {
            Long chatId = resultSet.getLong("chat_id");

            Long id = resultSet.getLong("id");
            Timestamp updatedAt = resultSet.getTimestamp(UPDATED_AT_LABEL);
            Timestamp checkedAt = resultSet.getTimestamp(CHECKED_AT_LABEL);
            URI url;
            try {
                url = new URI(resultSet.getString(URL_LABEL));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            LinkDto linkDto = linkById.computeIfAbsent(id, k -> new LinkDto(id, url, updatedAt, checkedAt));

            linkDto.getTrackedBy().add(new Chat(chatId));
        }

        return new ArrayList<>(linkById.values());
    };

    RowMapper<LinkDto> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        Long id = resultSet.getLong("id");
        Timestamp updatedAt = resultSet.getTimestamp(UPDATED_AT_LABEL);
        Timestamp checkedAt = resultSet.getTimestamp(CHECKED_AT_LABEL);
        URI url;
        try {
            url = new URI(resultSet.getString(URL_LABEL));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return new LinkDto(id, url, updatedAt, checkedAt);
    };

    List<LinkDto> findAll(Long tgChatId);

    List<LinkDto> findAllCheckedLaterThan(Duration duration);

    LinkDto add(URI url, Long chatId);

    LinkDto get(Long id);

    void updateCheckedAt(URI url, OffsetDateTime timestamp);

    void updateUpdatedAt(URI url, OffsetDateTime timestamp);

    LinkDto get(URI url);

    LinkDto remove(URI url, Long id);
}
