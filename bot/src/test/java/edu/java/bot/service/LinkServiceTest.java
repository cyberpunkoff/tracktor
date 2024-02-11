package edu.java.bot.service;

import edu.java.bot.service.LinkService;
import org.junit.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class LinkServiceTest {
    @Test
    public void saveGetLinkTest() {
        LinkService linkService = new LinkService();

        linkService.addLink(0L, "test.url");

        assertThat(linkService.getLinks(0L)).isEqualTo(List.of("test.url"));
    }
}
