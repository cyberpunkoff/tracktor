package edu.link.links;

import lombok.Getter;

public class GitHubLink extends Link {
    @Getter
    private String userName;
    @Getter
    private String repoName;

    public GitHubLink(String url, String userName, String repoName) {
        super(url);
        this.userName = userName;
        this.repoName = repoName;
    }
}
