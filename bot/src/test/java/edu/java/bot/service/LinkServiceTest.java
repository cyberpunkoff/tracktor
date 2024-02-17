package edu.java.bot.service;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LinkServiceTest {
    @Test
    public void saveGetLinkTest() {
        LinkService linkService = new LinkService();

        linkService.addLink(0L, "test.url");

        assertThat(linkService.getLinks(0L)).isEqualTo(List.of("test.url"));
    }
}
