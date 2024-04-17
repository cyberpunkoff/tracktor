package edu.java.bot.clients.scrapper;

import edu.java.AddLinkRequest;
import edu.java.LinkResponse;
import edu.java.ListLinksResponse;
import edu.java.RemoveLinkRequest;

public interface ScrapperClient {
    ListLinksResponse getLinks(Long tgChatId);

    LinkResponse trackLink(Long tgChatId, AddLinkRequest addLinkRequest);

    LinkResponse untrackLink(Long tgChatId, RemoveLinkRequest removeLinkRequest);

    void addChat(Long id);

    void deleteChat(Long id);
}
