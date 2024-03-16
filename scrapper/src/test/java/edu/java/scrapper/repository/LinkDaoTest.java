package edu.java.scrapper.repository;

import edu.java.dto.Chat;
import edu.java.dto.LinkDto;
import edu.java.repository.ChatDao;
import edu.java.repository.LinkDao;
import edu.java.scrapper.IntegrationEnvironment;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LinkDaoTest extends IntegrationEnvironment {
    @Autowired
    LinkDao linkDao;
    @Autowired
    ChatDao chatDao;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @SneakyThrows
    @Test
    @Transactional
    @Rollback
    void addTest() {
        Long userId = 123L;
        URI url = new URI("https://example.com");

        chatDao.add(new Chat(userId));
        linkDao.add(url, userId);

        String linkFromDb =
            jdbcTemplate.queryForObject("SELECT url from links WHERE url = ?", String.class, url.toString());

        assertThat(linkFromDb).isEqualTo(url.toString());
    }

    @SneakyThrows
    @Test
    @Transactional
    @Rollback
    void getByUrlTest() {
        Long userId = 123L;
        URI url = new URI("https://example.com");

        jdbcTemplate.update("INSERT INTO chats values (?)", userId);
        Long linkId =
            jdbcTemplate.queryForObject("INSERT INTO links (url) values (?) RETURNING id", Long.class, url.toString());
        jdbcTemplate.update("INSERT INTO chats_links (chat_id, link_id) values (123, ?)", linkId);

        String linkFromDb = linkDao.get(url).getUrl().toString();

        assertThat(linkFromDb).isEqualTo(url.toString());
    }

    @SneakyThrows
    @Test
    @Transactional
    @Rollback
    void getByIdTest() {
        Long userId = 123L;
        URI url = new URI("https://example.com");

        jdbcTemplate.update("INSERT INTO chats values (?)", userId);
        Long linkId =
            jdbcTemplate.queryForObject("INSERT INTO links (url) values (?) RETURNING id", Long.class, url.toString());
        jdbcTemplate.update("INSERT INTO chats_links (chat_id, link_id) values (123, ?)", linkId);

        String linkFromDb = linkDao.get(linkId).getUrl().toString();

        assertThat(linkFromDb).isEqualTo(url.toString());
    }

    @SneakyThrows
    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Long userId = 123L;
        URI url = new URI("https://example.com");

        jdbcTemplate.update("INSERT INTO chats values (?)", userId);
        Long linkId =
            jdbcTemplate.queryForObject("INSERT INTO links (url) values (?) RETURNING id", Long.class, url.toString());
        jdbcTemplate.update("INSERT INTO chats_links (chat_id, link_id) values (123, ?)", linkId);

        LinkDto removedLink = linkDao.remove(url, userId);

        assertThat(removedLink.getUrl()).isEqualTo(url);
    }

    @SneakyThrows
    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        Long userId = 123L;
        URI url = new URI("https://example.com");

        jdbcTemplate.update("INSERT INTO chats values (?)", userId);
        Long linkId =
            jdbcTemplate.queryForObject("INSERT INTO links (url) values (?) RETURNING id", Long.class, url.toString());
        jdbcTemplate.update("INSERT INTO chats_links (chat_id, link_id) values (123, ?)", linkId);

        List<LinkDto> linksFromDb = linkDao.findAll(userId);

        assertThat(linksFromDb).size().isEqualTo(1);
    }

    @SneakyThrows
    @Test
    @Transactional
    @Rollback
    void findAllCheckedLaterThanTest() {
        Long userId = 123L;
        URI url = new URI("https://example.com");

        jdbcTemplate.update("INSERT INTO chats values (?)", userId);
        Long linkId = jdbcTemplate.queryForObject("INSERT INTO links (url, checked_at) values (?, ?) RETURNING id",
            Long.class,
            url.toString(),
            OffsetDateTime.now().minusMinutes(5)
        );
        jdbcTemplate.update("INSERT INTO chats_links (chat_id, link_id) values (123, ?)", linkId);

        List<LinkDto> linksFromDb = linkDao.findAllCheckedLaterThan(Duration.ZERO);

        assertThat(linksFromDb).size().isEqualTo(1);
    }
}
