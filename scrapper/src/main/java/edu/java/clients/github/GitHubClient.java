package edu.java.clients.github;

import java.util.List;

public interface GitHubClient {
    RepositoryResponse fetch(String user, String repository);

    List<EventResponse> fetchEvents(String owner, String repo);
}
