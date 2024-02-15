package edu.java.clients.github;

public interface GitHubClient {
    RepositoryResponse fetch(String user, String repository);
}
