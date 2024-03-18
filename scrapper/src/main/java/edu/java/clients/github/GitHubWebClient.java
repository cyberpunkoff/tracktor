package edu.java.clients.github;

import edu.java.configuration.ApplicationConfig;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

public class GitHubWebClient implements GitHubClient {
    private static final String DEFAULT_BASE_URL = "https://api.github.com";
    private static final String REPOSITORY_ENDPOINT = "/repos/{owner}/{repo}";
    private static final String EVENTS_SUFFIX = "/events";
    private final WebClient webClient;
    private final ApplicationConfig applicationConfig;

    public GitHubWebClient(ApplicationConfig applicationConfig) {
        this(WebClient.builder(), DEFAULT_BASE_URL, applicationConfig);
    }

    public GitHubWebClient(
        WebClient.Builder webClientBuilder,
        String baseUrl,
        ApplicationConfig applicationConfig
    ) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.applicationConfig = applicationConfig;
    }

    @Override
    public RepositoryResponse fetch(String owner, String repo) {
        return webClient.get()
            .uri(REPOSITORY_ENDPOINT, owner, repo)
            .header(
                "Authorization",
                "Bearer " + applicationConfig.client().gitHub().token()
            )
            .retrieve()
            .bodyToMono(RepositoryResponse.class)
            .block();
    }

    @Override
    public List<EventResponse> fetchEvents(String owner, String repo) {
        return webClient.get()
            .uri(REPOSITORY_ENDPOINT + EVENTS_SUFFIX, owner, repo)
            .header(
                "Authorization",
                "Bearer " + applicationConfig.client().gitHub().token()
            )
            .retrieve()
            .bodyToFlux(EventResponse.class)
            .collectList()
            .block();
    }
}
