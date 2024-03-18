package edu.java.repository.jdbc;

import edu.java.dto.LinkDto;
import edu.java.repository.LinkDao;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class JdbcLinkDao implements LinkDao {
    private static final String GET_LINKS_QUERY =
        "select links.id, url, updated_at, checked_at, chats.chat_id from links "
            + "JOIN chats_links ON links.id = chats_links.link_id "
            + "JOIN chats ON chats_links.chat_id = chats.chat_id";

    private static final String GET_LINKS_OLDER_THAN_QUERY = GET_LINKS_QUERY
        + " WHERE EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - checked_at)) > ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcLinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LinkDto> findAll(Long tgChatId) {
        return jdbcTemplate.query(
            GET_LINKS_QUERY
                + " WHERE chats.chat_id = ?",
            SET_EXTRACTOR,
            tgChatId
        );
    }

    @Override
    public List<LinkDto> findAllCheckedLaterThan(Duration duration) {
        return jdbcTemplate.query(
            GET_LINKS_OLDER_THAN_QUERY,
            SET_EXTRACTOR,
            duration.toSeconds()
        );
    }

    @Override
    public LinkDto add(URI url, Long chatId) {
        LinkDto existingLink = get(url);

        if (existingLink == null) {
            existingLink =
                jdbcTemplate.query("insert into links (url) values (?) RETURNING *", ROW_MAPPER, url.toString())
                    .getFirst();
        }

        jdbcTemplate.update(
            "insert into chats_links (chat_id, link_id) values (?, ?)",
            chatId,
            existingLink.getId()
        );

        return this.get(existingLink.getId());
    }

    @Override
    public LinkDto get(Long id) {
        return Objects.requireNonNull(jdbcTemplate.query(GET_LINKS_QUERY + " WHERE links.id = ?", SET_EXTRACTOR, id))
            .getFirst();
    }

    @Override
    public void updateCheckedAt(URI url, OffsetDateTime timestamp) {
        jdbcTemplate.update("update links set checked_at = ? where url = ?", timestamp, url.toString());
    }

    @Override
    public void updateUpdatedAt(URI url, OffsetDateTime timestamp) {
        jdbcTemplate.update("update links set updated_at = ? where url = ?", timestamp, url.toString());
    }

    @Override
    public LinkDto get(URI url) {
        try {
            return jdbcTemplate.queryForObject(
                GET_LINKS_QUERY + " WHERE links.url = ?",
                ROW_MAPPER,
                url.toString()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public LinkDto remove(URI url, Long chatId) {
        LinkDto linkDto = this.get(url);

        jdbcTemplate.update(
            "delete from chats_links where chat_id = ? and link_id = ?",
            chatId,
            this.get(url).getId()
        );

        jdbcTemplate.update("delete from links where url = ?", url.toString());

        return linkDto;
    }
}
