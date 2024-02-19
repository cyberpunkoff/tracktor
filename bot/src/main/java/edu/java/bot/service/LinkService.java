package edu.java.bot.service;

import edu.java.bot.link.links.Link;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LinkService {
    private final Map<Long, List<Link>> links;

    public LinkService() {
        this.links = new HashMap<>();
    }

    public void addLink(Long id, Link link) {
        if (this.links.containsKey(id)) {
            this.links.get(id).add(link);
        } else {
            this.links.put(id, new LinkedList<>(List.of(link)));
        }
    }

    public List<Link> getLinks(Long id) {
        // TODO: Optional? Null safety?
        return this.links.get(id);
    }

    public boolean containsLinkId(Long userId, int linkId) {
        return linkId >=  0 && linkId < links.get(userId).size();
    }

    public void removeLink(Long userId, String url) {
        links.get(userId).removeIf(link -> link.getUrl().equals(url));
    }

    public void removeLinkById(Long userId, int linkId) {
        links.get(userId).remove(linkId);
    }
}
