package edu.java.scrapper.clients.stackoverflow;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import edu.java.clients.stackoverflow.QuestionResponse;
import edu.java.clients.stackoverflow.StackOverflowClient;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import edu.java.scrapper.IntegrationEnvironment;
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
public class StackOverflowWebClientTest extends IntegrationEnvironment {
    @Autowired
    StackOverflowClient stackOverflowClient;

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort())
        .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("app.client.stack-overflow.base-url", wireMockExtension::baseUrl);
    }

    @BeforeEach
    void setupStub() {
        wireMockExtension.stubFor(
            WireMock.get("/questions/68769565?site=stackoverflow")
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(getStubBody()))
        );
    }

    @Test
    public void testQuestionId_isCorrect() {
        QuestionResponse questionResponse = stackOverflowClient.fetchQuestion(68769565);

        assertThat(questionResponse.items().getFirst().id()).isEqualTo(68769565);
    }

    @Test
    public void testAmountOfQuestions_isCorrect() {
        List<QuestionResponse> questionResponses = stackOverflowClient.fetchQuestions(List.of(68769565L));

        assertThat(questionResponses.size()).isEqualTo(1);
    }

    @Test
    public void testQuestionLastActivityDate_isCorrect() {
        QuestionResponse questionResponse = stackOverflowClient.fetchQuestion(68769565);

        assertThat(questionResponse.items().getFirst()
            .lastActivityDate()).isEqualTo(OffsetDateTime.ofInstant(Instant.ofEpochSecond(1628848812), ZoneOffset.UTC));
    }

    static String getStubBody() {
        return """
            {
                 "items": [
                     {
                         "view_count": 7872,
                         "answer_count": 1,
                         "last_activity_date": 1628848812,
                         "question_id": 68769565,
                         "title": "How to create JSON Object Using Java"
                     }
                 ]
             }
            """;
    }
}
