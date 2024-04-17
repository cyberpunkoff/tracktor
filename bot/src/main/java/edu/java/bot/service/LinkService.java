package edu.java.bot.service;

import edu.java.LinkResponse;
import edu.java.ListLinksResponse;
import edu.java.RemoveLinkRequest;
import edu.java.bot.clients.scrapper.ScrapperClient;
import edu.link.links.Link;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final Map<Long, List<Link>> links = new HashMap<>();
    private final ScrapperClient scrapperClient;

//    public LinkService() {
//        this.links = new HashMap<>();
//    }

    public void addLink(Long id, Link link) {
        if (this.links.containsKey(id)) {
            this.links.get(id).add(link);
        } else {
            this.links.put(id, new LinkedList<>(List.of(link)));
        }
    }

    public List<Link> getLinks(Long id) {
        ListLinksResponse linksResponse = scrapperClient.getLinks(id);

        return linksResponse.links()
            .stream()
            .map(link -> Link.parse(link.url().toString()))
            .toList();
//        // TODO: Optional? Null safety?
//        return this.links.get(id);
    }

    public boolean containsLinkId(Long userId, int linkId) {
        // TODO: надо было зависеть не от конктретной реализации, а от абстракций
        // тогда бы не пришлось тут переписывать
//        return links.containsKey(userId) && linkId >= 0 && linkId < links.get(userId).size();
        ListLinksResponse linksResponse = scrapperClient.getLinks(userId);

        return !linksResponse.links().isEmpty() && linkId >= 0 && linkId < linksResponse.size();
    }

    @SneakyThrows
    public void removeLink(Long userId, String url) {
//        links.get(userId).removeIf(link -> link.getUrl().equals(url));
        scrapperClient.untrackLink(userId, new RemoveLinkRequest(new URI(url)));
    }

    public void removeLinkById(Long userId, int linkId) {
//        links.get(userId).remove(linkId);

        ListLinksResponse linksResponse = scrapperClient.getLinks(userId);
        LinkResponse link = linksResponse.links().get(linkId);
        scrapperClient.untrackLink(userId, new RemoveLinkRequest(link.url()));
    }
}
