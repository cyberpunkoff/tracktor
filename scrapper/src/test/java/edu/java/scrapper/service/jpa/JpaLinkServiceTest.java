package edu.java.scrapper.service.jpa;

import edu.java.dto.LinkDto;
import edu.java.entity.LinkEntity;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.scrapper.IntegrationEnvironment;
import edu.java.service.LinkService;
import edu.java.service.TelegramChatService;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JpaLinkServiceTest extends IntegrationEnvironment {
    @Autowired
    LinkService jpaLinkService;
    @Autowired
    JpaLinkRepository jpaLinkRepository;
    @Autowired
    TelegramChatService jpaTelegramChatService;

    @DynamicPropertySource
    static void dynamicConfiguration(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jpa");
    }

    @Test
    @Transactional
    @Rollback
    void addLinkTest() {
        Long chatId = 1L;
        URI url = URI.create("vk.com");

        jpaTelegramChatService.register(chatId);
        LinkDto linkDto = jpaLinkService.add(chatId, url);

        assertThat(linkDto.getUrl()).isEqualTo(url);
        assertThat(linkDto.getTrackedBy().getFirst().getChatId()).isEqualTo(chatId);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Long chatId = 1L;
        URI url = URI.create("vk.com");

        jpaTelegramChatService.register(chatId);
        LinkDto linkDto = jpaLinkService.add(chatId, url);

        jpaLinkService.remove(chatId, url);

        List<LinkDto> trackedLinks = jpaLinkService.listAll(chatId);

        assertThat(trackedLinks).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void testGetLinksCheckedDurationAgo_shouldReturnOneLink() {
        Long chatId = 1L;
        URI url = URI.create("vk.com");

        jpaTelegramChatService.register(chatId);
        LinkDto linkDto = jpaLinkService.add(chatId, url);

        List<LinkDto> links = jpaLinkService.getLinksCheckedDurationAgo(Duration.ZERO);

        assertThat(links).size().isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    void testGetLinksCheckedDurationAgo_shouldReturnZeroLinks() {
        Long chatId = 1L;
        URI url = URI.create("vk.com");

        jpaTelegramChatService.register(chatId);
        LinkDto linkDto = jpaLinkService.add(chatId, url);

        List<LinkDto> links = jpaLinkService.getLinksCheckedDurationAgo(Duration.ofSeconds(1));

        assertThat(links).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateCheckedAt() {
        Long chatId = 1L;
        URI url = URI.create("vk.com");

        jpaTelegramChatService.register(chatId);
        LinkDto linkDto = jpaLinkService.add(chatId, url);

        jpaLinkService.updateCheckedAt(url, OffsetDateTime.parse("2007-12-03T10:15:30+01:00"));

        LinkEntity link = jpaLinkRepository.findFirstByUrl(url);
        assertThat(link.getCheckedAt()).isEqualTo(OffsetDateTime.parse("2007-12-03T10:15:30+01:00"));
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateUpdatedAt() {
        Long chatId = 1L;
        URI url = URI.create("vk.com");

        jpaTelegramChatService.register(chatId);
        LinkDto linkDto = jpaLinkService.add(chatId, url);

        jpaLinkService.updateUpdatedAt(url, OffsetDateTime.parse("2007-12-03T10:15:30+01:00"));

        LinkEntity link = jpaLinkRepository.findFirstByUrl(url);
        assertThat(link.getUpdatedAt()).isEqualTo(OffsetDateTime.parse("2007-12-03T10:15:30+01:00"));
    }
}
