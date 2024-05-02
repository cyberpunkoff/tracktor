package edu.java.clients.bot;

import edu.java.LinkUpdateRequest;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientBotClient implements BotClient {
    private static final String DEFAULT_BASE_URL = "http://127.0.0.1:8090";
    private static final String UPDATES_ENDPOINT = "/updates";
    private final WebClient webClient;

    public WebClientBotClient() {
        this(WebClient.builder(), DEFAULT_BASE_URL);
    }

    public WebClientBotClient(WebClient.Builder builder, String baseUrl) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    @Override
    public void sendUpdate(LinkUpdateRequest linkUpdateRequest) {
        webClient.post()
            .uri(UPDATES_ENDPOINT)
            .bodyValue(linkUpdateRequest)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}
