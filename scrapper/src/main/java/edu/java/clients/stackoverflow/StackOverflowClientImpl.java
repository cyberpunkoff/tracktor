package edu.java.clients.stackoverflow;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StackOverflowClientImpl implements StackOverflowClient {
    private static final String DEFAULT_BASE_URL = "https://api.stackexchange.com/2.3";
    private static final String QUESTION_ENDPOINT = "/questions/%d?site=stackoverflow";
    private final WebClient webClient;

    public StackOverflowClientImpl() {
        this(DEFAULT_BASE_URL);
    }

    public StackOverflowClientImpl(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public QuestionResponse fetchQuestion(Integer id) {
        return webClient.get()
            .uri(String.format(QUESTION_ENDPOINT, id))
            .retrieve()
            .bodyToMono(StackOverflowQuestionResponse.class)
            .block().getItems().getFirst();
    }
}
