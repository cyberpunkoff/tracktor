package edu.java.bot.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LinkService {
    private final Map<Long, List<String>> links;

    public LinkService() {
        this.links = new HashMap<>();
    }

    public void addLink(Long id, String link) {
        if (this.links.containsKey(id)) {
            this.links.get(id).add(link);
        } else {
            this.links.put(id, new LinkedList<>(List.of(link)));
        }
    }

    public List<String> getLinks(Long id) {
        // TODO: Optional? Null safety?
        return this.links.get(id);
    }

    public void removeLink(String link) {
    }
}
