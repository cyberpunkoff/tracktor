package edu.link.parsers;

import edu.link.links.GitHubLink;
import edu.link.links.Link;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitHubLinkParser extends LinkParser {
    private final static Pattern URL_PATTERN =
        Pattern.compile("https://github\\.com/(?<userName>\\S*)/(?<repoName>\\S*)");

    public Link parse(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);

        if (matcher.find()) {
            return new GitHubLink(url, matcher.group("userName"), matcher.group("repoName"));
        } else if (next != null) {
            return next.parse(url);
        } else {
            return null;
        }
    }
}
