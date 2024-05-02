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

        if (link == null) {
            link = linkRepository.save(new LinkEntity(url));
        }

        ChatEntity chat = chatRepository.findFirstById(tgChatId);

        link.getTrackedBy().add(chat);
        chat.getLinks().add(link);

        linkRepository.save(link);
        chatRepository.save(chat);
        return linkEntityToDtoMapper(link);
    }

    @Override
    public LinkDto remove(long tgChatId, URI url) {
        ChatEntity chat = chatRepository.findFirstById(tgChatId);
        LinkEntity link = linkRepository.findFirstByUrl(url);
        chat.getLinks().remove(link);

        try {
            linkRepository.delete(link);
        } catch (Exception ignored) {
        }

        return linkEntityToDtoMapper(link);
    }

    @Override
    public List<LinkDto> getLinksCheckedDurationAgo(Duration duration) {
        return linkRepository.findByCheckedAtBefore(
                OffsetDateTime.now().minus(duration)
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
            Timestamp.from(linkEntity.getUpdatedAt().toInstant()),
            Timestamp.from(linkEntity.getCheckedAt().toInstant()),
            linkEntity.getTrackedBy().stream().map(e -> new Chat(e.getId())).toList()
        );
    }
}
