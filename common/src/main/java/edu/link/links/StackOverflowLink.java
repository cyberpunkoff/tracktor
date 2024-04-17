package edu.link.links;

public class StackOverflowLink extends Link {
    private final long questionId;

    public StackOverflowLink(String url, long questionId) {
        super(url);
        this.questionId = questionId;
    }

    public long getQuestionId() {
        return questionId;
    }
}
