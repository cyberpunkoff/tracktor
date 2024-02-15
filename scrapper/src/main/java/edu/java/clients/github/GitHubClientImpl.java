package edu.java.clients.github;

import edu.java.clients.stackoverflow.StackOverflowQuestionResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GitHubClientImpl implements GitHubClient {
    private static final String DEFAULT_BASE_URL = "https://api.github.com/";
    private static final String REPOSITORY_ENDPOINT = "repos/%s/%s";
    private final WebClient webClient;

    public GitHubClientImpl() {
        this(DEFAULT_BASE_URL);
    }

    public GitHubClientImpl(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public RepositoryResponse fetch(String user, String repository) {
        return webClient.get()
            .uri(String.format(REPOSITORY_ENDPOINT, user, repository))
            .retrieve()
            .bodyToMono(RepositoryResponse.class)
            .block();
    }
}
