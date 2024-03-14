package edu.java.service;

import edu.java.dto.LinkDto;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

public interface LinkService {
    LinkDto add(long tgChatId, URI url);

    LinkDto remove(long tgChatId, URI url);

    List<LinkDto> listAllCheckedLaterThan(Duration duration);

    void updateCheckedAt(URI url, Timestamp timestamp);

    void updateUpdatedAt(URI url, Timestamp timestamp);

    List<LinkDto> listAll(long tgChatId);
}
