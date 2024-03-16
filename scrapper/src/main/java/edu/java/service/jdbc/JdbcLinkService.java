package edu.java.service.jdbc;

import edu.java.dto.LinkDto;
import edu.java.exceptions.LinkAlreadyExistsException;
import edu.java.exceptions.NoSuchChatException;
import edu.java.repository.LinkDao;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final LinkDao linkRepository;

    @Override
    @SneakyThrows
    @Transactional
    public LinkDto add(long tgChatId, URI url) {
        try {
            return linkRepository.add(url, tgChatId);
        } catch (DuplicateKeyException exception) {
            throw new LinkAlreadyExistsException();
        }
    }

    @Override
    @SneakyThrows
    @Transactional
    public LinkDto remove(long tgChatId, URI url) {
        try {
            return linkRepository.remove(url, tgChatId);
        } catch (EmptyResultDataAccessException exception) {
            throw new NoSuchChatException();
        }
    }

    @Override
    @Transactional
    public List<LinkDto> listAllCheckedLaterThan(Duration duration) {
        return linkRepository.findAllCheckedLaterThan(duration);
    }

    @Override
    @Transactional
    public void updateCheckedAt(URI url, OffsetDateTime timestamp) {
        linkRepository.updateCheckedAt(url, timestamp);
    }

    @Override
    @Transactional
    public void updateUpdatedAt(URI url, OffsetDateTime timestamp) {
        linkRepository.updateUpdatedAt(url, timestamp);
    }

    @Override
    @Transactional
    public List<LinkDto> listAll(long tgChatId) {
        return linkRepository.findAll(tgChatId);
    }
}
