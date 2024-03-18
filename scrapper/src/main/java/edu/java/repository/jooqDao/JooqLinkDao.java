package edu.java.repository.jooqDao;

import edu.java.dto.Chat;
import edu.java.dto.LinkDto;
import edu.java.repository.LinkDao;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.repository.jooq.Tables.CHATS_LINKS;
import static edu.java.repository.jooq.Tables.LINKS;
import static org.jooq.Records.mapping;
import static org.jooq.impl.DSL.asterisk;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

@Repository
@RequiredArgsConstructor
public class JooqLinkDao implements LinkDao {
    private final DSLContext dslContext;

    @Override
    public List<LinkDto> findAll(Long tgChatId) {
        return dslContext.select(
                LINKS.ID,
                LINKS.URL,
                LINKS.CHECKED_AT,
                LINKS.UPDATED_AT,
                multiset(select(CHATS_LINKS.CHAT_ID)
                    .from(CHATS_LINKS)
                    .where(CHATS_LINKS.LINK_ID.eq(LINKS.ID))
                ).convertFrom(r -> r.map(mapping(Chat::new)))
                    .as("trackedBy")
            )
            .from(LINKS)
            .where(CHATS_LINKS.CHAT_ID.eq(tgChatId))
            .fetchInto(LinkDto.class);
    }

    @Override
    public List<LinkDto> findAllCheckedLaterThan(Duration duration) {
        return dslContext.select(
                LINKS.ID,
                LINKS.URL,
                LINKS.CHECKED_AT,
                LINKS.UPDATED_AT,
                multiset(select(CHATS_LINKS.CHAT_ID)
                    .from(CHATS_LINKS)
                    .where(CHATS_LINKS.LINK_ID.eq(LINKS.ID))
                ).convertFrom(r -> r.map(mapping(Chat::new)))
                    .as("trackedBy")
            )
            .from(LINKS)
            .where(LINKS.CHECKED_AT.lessThan(OffsetDateTime.now().minus(duration)))
            .fetchInto(LinkDto.class);
    }

    @Override
    public LinkDto add(URI url, Long chatId) {
        LinkDto existingLink = get(url);

        if (existingLink == null) {
            existingLink = dslContext.insertInto(LINKS)
                .columns(LINKS.URL)
                .values(url.toString())
                .returning(asterisk())
                .fetchOneInto(LinkDto.class);
        }

        dslContext.insertInto(CHATS_LINKS)
            .columns(CHATS_LINKS.CHAT_ID, CHATS_LINKS.LINK_ID)
            .values(chatId, existingLink.getId());

        return get(existingLink.getId());
    }

    @Override
    public LinkDto get(Long id) {
        return dslContext.select(
                LINKS.ID,
                LINKS.URL,
                LINKS.CHECKED_AT,
                LINKS.UPDATED_AT,
                multiset(select(CHATS_LINKS.CHAT_ID)
                    .from(CHATS_LINKS)
                    .where(CHATS_LINKS.LINK_ID.eq(LINKS.ID))
                ).convertFrom(r -> r.map(mapping(Chat::new)))
                    .as("trackedBy")
            )
            .from(LINKS)
            .where(LINKS.ID.eq(id))
            .fetchOneInto(LinkDto.class);
    }

    @Override
    public void updateCheckedAt(URI url, OffsetDateTime timestamp) {
        dslContext.update(LINKS)
            .set(LINKS.CHECKED_AT, timestamp)
            .where(LINKS.URL.eq(url.toString()));
    }

    @Override
    public void updateUpdatedAt(URI url, OffsetDateTime timestamp) {
        dslContext.update(LINKS)
            .set(LINKS.UPDATED_AT, timestamp)
            .where(LINKS.URL.eq(url.toString()));
    }

    @Override
    public LinkDto get(URI url) {
        return dslContext.select(
                LINKS.ID,
                LINKS.URL,
                LINKS.CHECKED_AT,
                LINKS.UPDATED_AT,
                multiset(select(CHATS_LINKS.CHAT_ID)
                    .from(CHATS_LINKS)
                    .where(CHATS_LINKS.LINK_ID.eq(LINKS.ID))
                ).convertFrom(r -> r.map(mapping(Chat::new)))
                    .as("trackedBy")
            )
            .from(LINKS)
            .where(LINKS.URL.eq(url.toString()))
            .fetchOneInto(LinkDto.class);
    }

    @Override
    public LinkDto remove(URI url, Long id) {
        LinkDto linkDto = get(url);
        dslContext.deleteFrom(CHATS_LINKS)
            .where(CHATS_LINKS.CHAT_ID.eq(id))
            .and(CHATS_LINKS.LINK_ID.eq(linkDto.getId()));

        dslContext.deleteFrom(LINKS)
            .where(LINKS.URL.eq(url.toString()));

        return linkDto;
    }
}
