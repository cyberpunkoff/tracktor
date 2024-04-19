package edu.java.configuration;

import edu.java.repository.jdbc.JdbcChatDao;
import edu.java.repository.jdbc.JdbcLinkDao;
import edu.java.service.LinkService;
import edu.java.service.TelegramChatService;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.service.jdbc.JdbcTelegramChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public LinkService linkService(
        JdbcLinkDao linkDao
    ) {
        return new JdbcLinkService(linkDao);
    }

    @Bean
    public TelegramChatService telegramChatService(
        JdbcChatDao chatDao
    ) {
        return new JdbcTelegramChatService(chatDao);
    }
}
