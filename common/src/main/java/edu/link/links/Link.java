package edu.link.links;

import edu.link.parsers.GitHubLinkParser;
import edu.link.parsers.StackOverflowLinkParser;
import java.util.Objects;


public abstract class Link {
    private static final GitHubLinkParser GIT_HUB_LINK_PARSER = new GitHubLinkParser();
    private static final StackOverflowLinkParser STACK_OVERFLOW_LINK_PARSER = new StackOverflowLinkParser();

    static {
        GIT_HUB_LINK_PARSER.setNext(STACK_OVERFLOW_LINK_PARSER);
    }

    private final String url;

    protected Link(String url) {
        // TODO: may be try changing this field to URI?
        this.url = url;
    }

    public static Link parse(String url) {
        return GIT_HUB_LINK_PARSER.parse(url);
    }

    public String getUrl() {
        return url;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Link link = (Link) o;
        return Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return url;
    }
}
