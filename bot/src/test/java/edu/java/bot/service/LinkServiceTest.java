package edu.java.bot.service;

import edu.java.bot.link.links.GitHubLink;
import edu.java.bot.service.LinkService;
import org.junit.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class LinkServiceTest {
    @Test
    public void saveGetLinkTest() {
        LinkService linkService = new LinkService();
        GitHubLink gitHubLink = new GitHubLink("test.url", "test", "test");

        linkService.addLink(0L, gitHubLink);

        assertThat(linkService.getLinks(0L)).isEqualTo(List.of(gitHubLink));
    }
}
