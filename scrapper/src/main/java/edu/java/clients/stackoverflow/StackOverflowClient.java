package edu.java.clients.stackoverflow;

public interface StackOverflowClient {
    QuestionResponse fetchQuestion(long id);
}
