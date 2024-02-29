package edu.java.bot.clients.scrapper;

import edu.java.bot.model.client.AddLinkRequest;
import edu.java.bot.model.client.LinkResponse;
import edu.java.bot.model.client.ListLinksResponse;
import edu.java.bot.model.client.RemoveLinkRequest;

public interface ScrapperClient {
    ListLinksResponse getLinks(Long tgChatId);

    LinkResponse trackLink(Long tgChatId, AddLinkRequest addLinkRequest);

    LinkResponse untrackLink(Long tgChatId, RemoveLinkRequest removeLinkRequest);

    void addChat(Long id);

    void deleteChat(Long id);
}
