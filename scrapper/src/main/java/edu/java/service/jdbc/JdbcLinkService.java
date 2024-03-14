package edu.java.service.jdbc;

import edu.java.dto.LinkDto;
import edu.java.repository.LinkDao;
import edu.java.service.LinkService;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final LinkDao linkRepository;

    @Override
    public LinkDto add(long tgChatId, URI url) {
        return linkRepository.add(url, tgChatId);
    }

    @Override
    public LinkDto remove(long tgChatId, URI url) {
        return linkRepository.remove(url, tgChatId);
    }

    @Override
    public List<LinkDto> listAllCheckedLaterThan(Duration duration) {
        return linkRepository.findAllCheckedLaterThan(duration);
    }

    @Override
    public void updateCheckedAt(URI url, Timestamp timestamp) {
        linkRepository.updateCheckedAt(url, timestamp);
    }

    @Override
    public void updateUpdatedAt(URI url, Timestamp timestamp) {
        linkRepository.updateUpdatedAt(url, timestamp);
    }

    @Override
    public List<LinkDto> listAll(long tgChatId) {
        return linkRepository.findAll(tgChatId);
    }
}
