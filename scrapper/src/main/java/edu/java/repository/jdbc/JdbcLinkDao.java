package edu.java.repository.jdbc;

import edu.java.dto.Link;
import edu.java.repository.LinkDao;
import java.net.URI;
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
    public List<Link> findAll(Long tgChatId) {
        return jdbcTemplate.query(
            GET_LINKS_QUERY
            + " WHERE chats.chat_id = ?",
            SET_EXTRACTOR,
            tgChatId
        );
    }

    @Override
    public List<Link> findAllCheckedLaterThan(Duration duration) {
        return jdbcTemplate.query(
            GET_LINKS_OLDER_THAN_QUERY,
            SET_EXTRACTOR,
            duration.toSeconds()
        );
    }

    @Override
    public Link add(URI url, Long chatId) {
        // TODO: add unique link checks
        Link link = jdbcTemplate.query("insert into links (url) values (?) RETURNING *", ROW_MAPPER, url.toString())
            .getFirst();

        jdbcTemplate.update(
            "insert into chats_links (chat_id, link_id) values (?, ?)",
            chatId,
            link.getId()
        );

        return this.get(link.getId());
    }

    @Override
    public Link get(Long id) {
        return Objects.requireNonNull(jdbcTemplate.query(GET_LINKS_QUERY + " WHERE links.id = ?", SET_EXTRACTOR, id))
            .getFirst();
    }

    @Override
    public Link get(URI url) {
        return Objects.requireNonNull(jdbcTemplate.query(
                GET_LINKS_QUERY + " WHERE links.url = ?",
                SET_EXTRACTOR,
                url.toString()
            ))
            .getFirst();
    }

    @Override
    public Link remove(URI url, Long chatId) {
        Link link = this.get(url);

        jdbcTemplate.update(
            "delete from chats_links where chat_id = ? and link_id = ?",
            chatId,
            this.get(url).getId()
        );

        jdbcTemplate.update("delete from links where url = ?", url.toString());

        return link;
    }
}
