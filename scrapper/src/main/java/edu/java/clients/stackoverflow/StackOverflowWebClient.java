package edu.java.clients.stackoverflow;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowWebClient implements StackOverflowClient {
    private static final String DEFAULT_BASE_URL = "https://api.stackexchange.com/2.3";
    private static final String QUESTION_ENDPOINT = "/questions/{id}?site=stackoverflow";
    private final WebClient webClient;

    public StackOverflowWebClient() {
        this(DEFAULT_BASE_URL);
    }

    public StackOverflowWebClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public QuestionResponse fetchQuestion(long id) {
        return webClient.get()
            .uri(QUESTION_ENDPOINT, id)
            .retrieve()
            .bodyToMono(QuestionResponse.class)
            .block();
    }

    @Override
    public List<QuestionResponse> fetchQuestions(List<Long> ids) {
        return webClient.get()
            .uri(QUESTION_ENDPOINT, ids.stream().map(String::valueOf).collect(Collectors.joining(";")))
            .retrieve()
            .bodyToFlux(QuestionResponse.class)
            .collectList()
            .block();
    }
}
