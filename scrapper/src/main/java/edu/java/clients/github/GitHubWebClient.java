package edu.java.clients.github;

import edu.java.configuration.ApplicationConfig;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubWebClient implements GitHubClient {
    private static final String DEFAULT_BASE_URL = "https://api.github.com";
    private static final String REPOSITORY_ENDPOINT = "/repos/{owner}/{repo}";
    private final WebClient webClient;
    private final ApplicationConfig applicationConfig;

    public GitHubWebClient(ApplicationConfig applicationConfig) {
        this(DEFAULT_BASE_URL, applicationConfig);
    }

    public GitHubWebClient(String baseUrl, ApplicationConfig applicationConfig) {
        this.webClient = WebClient.create(baseUrl);
        this.applicationConfig = applicationConfig;
    }

    @Override
    public RepositoryResponse fetch(String owner, String repo) {
        // TODO: add token for personal use
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
}
