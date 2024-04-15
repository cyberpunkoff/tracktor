package edu.java.clients.stackoverflow;

import java.util.List;

public interface StackOverflowClient {
    QuestionResponse fetchQuestion(long id);

    List<QuestionResponse> fetchQuestions(List<Long> id);
}
