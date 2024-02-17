package edu.java.scrapper.clients.github;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.RepositoryResponse;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubWebClientTest {
    @Autowired
    GitHubClient gitHubClient;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort())
        .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("app.client.git-hub.base-url", wireMockExtension::baseUrl);
    }

    @BeforeEach
    void setupStub() {
        wireMockExtension.stubFor(
            WireMock.get("/repos/test/repo")
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(getStubBody()))
        );
    }

    @Test
    public void testRepositoryId_isCorrect() {
        RepositoryResponse repositoryResponse = gitHubClient.fetch("test", "repo");

        assertThat(repositoryResponse.id()).isEqualTo(1296269);
    }

    @Test
    public void testRepositoryUpdatedAt_isCorrect() {
        RepositoryResponse repositoryResponse = gitHubClient.fetch("test", "repo");

        assertThat(repositoryResponse.updatedAt()).isEqualTo(OffsetDateTime.parse("2024-02-15T09:50:13Z"));
    }

    static String getStubBody() {
        return """
            {
              "id": "1296269",
              "updated_at": "2024-02-15T09:50:13Z"
            }
            """;
    }
}

