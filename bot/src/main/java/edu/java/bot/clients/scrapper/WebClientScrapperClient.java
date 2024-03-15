package edu.java.bot.clients.scrapper;

import edu.java.AddLinkRequest;
import edu.java.LinkResponse;
import edu.java.ListLinksResponse;
import edu.java.RemoveLinkRequest;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientScrapperClient implements ScrapperClient {
    private final static String DEFAULT_BASE_URL = "http://127.0.0.1:8080";
    private final static String LINK_ENDPOINT = "/links";
    private final static String TG_CHAT_ENDPOINT = "/tg-chat/{id}";
    private final static String TG_CHAT_HEADER = "Tg-Chat-Id";
    private final WebClient webClient;

    public WebClientScrapperClient() {
        this(DEFAULT_BASE_URL);
    }

    public WebClientScrapperClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public ListLinksResponse getLinks(Long tgChatId) {
        return webClient.get()
            .uri(LINK_ENDPOINT)
            .header(TG_CHAT_HEADER, tgChatId.toString())
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    @Override
    public LinkResponse trackLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri(LINK_ENDPOINT)
            .header(TG_CHAT_HEADER, tgChatId.toString())
            .bodyValue(addLinkRequest)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse untrackLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
            .uri(LINK_ENDPOINT)
            .header(TG_CHAT_HEADER, tgChatId.toString())
            .bodyValue(removeLinkRequest)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public void addChat(Long id) {
        webClient.post()
            .uri(TG_CHAT_ENDPOINT, id)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    @Override
    public void deleteChat(Long id) {
        webClient.delete()
            .uri(TG_CHAT_ENDPOINT, id)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}
