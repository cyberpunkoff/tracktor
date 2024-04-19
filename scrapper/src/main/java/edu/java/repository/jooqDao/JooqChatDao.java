package edu.java.repository.jooqDao;

import edu.java.dto.Chat;
import edu.java.repository.ChatDao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static edu.java.repository.jooq.Tables.CHATS;
import static org.jooq.impl.DSL.asterisk;

@Repository
@RequiredArgsConstructor
public class JooqChatDao implements ChatDao {
    private final DSLContext dslContext;

    @Override
    public List<Chat> findAll() {
        return dslContext.select(asterisk())
            .from(CHATS)
            .fetchInto(Chat.class);
    }

    @Override
    public void add(Chat chat) {
        dslContext.insertInto(CHATS)
            .columns(CHATS.CHAT_ID)
            .values(chat.getChatId());
    }

    @Override
    public void remove(Chat chat) {
        dslContext.deleteFrom(CHATS)
            .where(CHATS.CHAT_ID.eq(chat.getChatId()));
    }
}
