package edu.java.clients.bot;

import edu.java.model.client.LinkUpdateRequest;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientBotClient implements BotClient {
    private static final String DEFAULT_BASE_URL = "http://127.0.0.1:8090";
    private static final String UPDATES_ENDPOINT = "/updates";
    private final WebClient webClient;

    public WebClientBotClient() {
        this(DEFAULT_BASE_URL);
    }

    public WebClientBotClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public void sendUpdate(LinkUpdateRequest linkUpdateRequest) {
        webClient.post()
            .uri(DEFAULT_BASE_URL)
            .bodyValue(linkUpdateRequest)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}
