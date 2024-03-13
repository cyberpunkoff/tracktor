package edu.java.service.jdbc;

import edu.java.dto.Link;
import edu.java.repository.LinkDao;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final LinkDao linkRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        return linkRepository.add(url, tgChatId);
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        return linkRepository.remove(url, tgChatId);
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        return linkRepository.findAll(tgChatId);
    }
}
