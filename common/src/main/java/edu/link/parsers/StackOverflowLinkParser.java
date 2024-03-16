package edu.link.parsers;

import edu.link.links.Link;
import edu.link.links.StackOverflowLink;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackOverflowLinkParser extends LinkParser {
    private final static Pattern URL_PATTERN =
        Pattern.compile("https://stackoverflow.com/questions/(?<questionId>\\d*)/\\S*");

    public Link parse(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);

        if (matcher.find()) {
            return new StackOverflowLink(url, Long.parseLong(matcher.group("questionId")));
        } else if (next != null) {
            return next.parse(url);
        } else {
            return null;
        }
    }
}
