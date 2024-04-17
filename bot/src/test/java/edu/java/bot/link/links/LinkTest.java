package edu.java.bot.link.links;

import edu.link.links.GitHubLink;
import edu.link.links.Link;
import edu.link.links.StackOverflowLink;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LinkTest {
    @Test
    void testStackOverflowUrl_shouldReturnStackOverflowLink() {
        String stackOverflowUrl =
            "https://stackoverflow.com/questions/62437760/in-a-dockerfile-is-possible-to-not-override-the-base-image-cmd-when-implementing";

        Link link = Link.parse(stackOverflowUrl);

        assertThat(link).isInstanceOf(StackOverflowLink.class);
    }

    @Test
    void testGitHubUrl_shouldReturnGitHubLink() {
        String gitHubUrl = "https://github.com/octocat/Hello-World";

        Link link = Link.parse(gitHubUrl);

        assertThat(link).isInstanceOf(GitHubLink.class);
    }

    @Test
    void testIncorrectUrl_shouldReturnNull() {
        String invalidUrl = "not really a url";

        // TODO: так ли это должно быть? может переписать на Optional?
        Link link = Link.parse(invalidUrl);

        assertThat(link).isNull();
    }
}
