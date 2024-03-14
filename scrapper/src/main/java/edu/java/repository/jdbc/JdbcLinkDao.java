package edu.java.repository.jdbc;

import edu.java.dto.LinkDto;
import edu.java.repository.LinkDao;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
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
        // TODO: add unique link checks
        LinkDto linkDto = jdbcTemplate.query("insert into links (url) values (?) RETURNING *", ROW_MAPPER, url.toString())
            .getFirst();

        jdbcTemplate.update(
            "insert into chats_links (chat_id, link_id) values (?, ?)",
            chatId,
            linkDto.getId()
        );

        return this.get(linkDto.getId());
    }

    @Override
    public LinkDto get(Long id) {
        return Objects.requireNonNull(jdbcTemplate.query(GET_LINKS_QUERY + " WHERE links.id = ?", SET_EXTRACTOR, id))
            .getFirst();
    }

    @Override
    public void updateCheckedAt(URI url, Timestamp timestamp) {

    }

    @Override
    public void updateUpdatedAt(URI url, Timestamp timestamp) {

    }

    @Override
    public LinkDto get(URI url) {
        return Objects.requireNonNull(jdbcTemplate.query(
                GET_LINKS_QUERY + " WHERE links.url = ?",
                SET_EXTRACTOR,
                url.toString()
            ))
            .getFirst();
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
