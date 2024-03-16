package edu.java.service;

import edu.java.dto.LinkDto;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {
    LinkDto add(long tgChatId, URI url);

    LinkDto remove(long tgChatId, URI url);

    List<LinkDto> listAllCheckedLaterThan(Duration duration);

    void updateCheckedAt(URI url, OffsetDateTime timestamp);

    void updateUpdatedAt(URI url, OffsetDateTime timestamp);

    List<LinkDto> listAll(long tgChatId);
}
