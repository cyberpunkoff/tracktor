package edu.java.service.jpa;

import edu.java.dto.Chat;
import edu.java.dto.LinkDto;
import edu.java.entity.ChatEntity;
import edu.java.entity.LinkEntity;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.LinkService;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkRepository linkRepository;
    private final JpaChatRepository chatRepository;

    @Override
    public LinkDto add(long tgChatId, URI url) {
        LinkEntity link = linkRepository.findFirstByUrl(url);
        ChatEntity chat = chatRepository.findById(tgChatId).get();
        link.getTrackedBy().add(chat);
        linkRepository.save(link);
        return linkEntityToDtoMapper(link);
    }

    @Override
    public LinkDto remove(long tgChatId, URI url) {
        ChatEntity chat = chatRepository.findById(tgChatId).get();
        LinkEntity link = linkRepository.findFirstByUrl(url);
        chat.getLinks().remove(link);

        try {
            linkRepository.delete(link);
        } catch (Exception ignored) {
        }

        return linkEntityToDtoMapper(link);
    }

    @Override
    public List<LinkDto> listAllCheckedLaterThan(Duration duration) {
        return linkRepository.findByCheckedAtBefore(
                Timestamp.from(OffsetDateTime.now().toInstant().minus(duration))
            ).stream()
            .map(JpaLinkService::linkEntityToDtoMapper)
            .toList();
    }

    @Override
    public void updateCheckedAt(URI url, OffsetDateTime timestamp) {
        linkRepository.updateCheckedAt(timestamp, url);

    }

    @Override
    public void updateUpdatedAt(URI url, OffsetDateTime timestamp) {
        linkRepository.updateUpdatedAt(timestamp, url);
    }

    @Override
    public List<LinkDto> listAll(long tgChatId) {
        return linkRepository.findByTrackedById(tgChatId).stream()
            .map(JpaLinkService::linkEntityToDtoMapper)
            .toList();
    }

    private static LinkDto linkEntityToDtoMapper(LinkEntity linkEntity) {
        return new LinkDto(
            linkEntity.getId(),
            linkEntity.getUrl(),
            linkEntity.getUpdatedAt(),
            linkEntity.getCheckedAt(),
            linkEntity.getTrackedBy().stream().map(e -> new Chat(e.getChatId())).toList()
        );
    }
}
