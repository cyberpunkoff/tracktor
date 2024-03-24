package edu.java.configuration;

import edu.java.repository.jooqDao.JooqChatDao;
import edu.java.repository.jooqDao.JooqLinkDao;
import edu.java.service.LinkService;
import edu.java.service.TelegramChatService;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.service.jdbc.JdbcTelegramChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    public LinkService linkService(
        JooqLinkDao linkDao
    ) {
        return new JdbcLinkService(linkDao);
    }

    @Bean
    public TelegramChatService telegramChatService(
        JooqChatDao chatDao
    ) {
        return new JdbcTelegramChatService(chatDao);
    }
}
