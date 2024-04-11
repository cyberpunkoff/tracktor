package edu.java.scrapper.clients.kispython;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.kispython.JSoupKispythonClient;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest
public class JsoupKispythonClientTest {
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
            WireMock.get("/group/23")
                .willReturn(aResponse()
                    .withHeader("Content-Type", MediaType.TEXT_HTML_VALUE)
                    .withBodyFile("page-with-10-tasks.html"))
        );
    }

    @Test
    void getLastTaskNumber_shouldReturnLastNumber() {
        JSoupKispythonClient client = new JSoupKispythonClient(wireMockExtension.baseUrl());

        assertThat(client.getLastTaskNumber()).isEqualTo(10);
    }

    @Test
    void getAllTaskNumbers_shouldReturnListOfNumbers() {
        JSoupKispythonClient client = new JSoupKispythonClient(wireMockExtension.baseUrl());

        List<Integer> expected = IntStream.range(1, 10).boxed().toList();

        assertThat(client.getAllTaskNumbers()).isEqualTo(expected);
    }
}
