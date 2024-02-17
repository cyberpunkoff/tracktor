package edu.java.clients.github;

import org.springframework.web.reactive.function.client.WebClient;

public class GitHubWebClient implements GitHubClient {
    private static final String DEFAULT_BASE_URL = "https://api.github.com";
    private static final String REPOSITORY_ENDPOINT = "/repos/{owner}/{repo}";
    private final WebClient webClient;

    public GitHubWebClient() {
        this(DEFAULT_BASE_URL);
    }

    public GitHubWebClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public RepositoryResponse fetch(String owner, String repo) {
        return webClient.get()
            .uri(REPOSITORY_ENDPOINT, owner, repo)
            .retrieve()
            .bodyToMono(RepositoryResponse.class)
            .block();
    }
}
