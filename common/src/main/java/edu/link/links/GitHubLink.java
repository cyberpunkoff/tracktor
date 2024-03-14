package edu.link.links;

public class GitHubLink extends Link {
    private String userName;
    private String repoName;

    public GitHubLink(String url, String userName, String repoName) {
        super(url);
        this.userName = userName;
        this.repoName = repoName;
    }
}
