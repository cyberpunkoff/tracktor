package edu.java.scrapper.clients;

import static edu.java.scrapper.IntegrationEnvironment.POSTGRES;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext
public class TestRateLimiting {
    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Test
    void testRateLimiting() throws Exception {
        mockMvc.perform(get("/links").header("Tg-Chat-Id", "123")).andExpect(status().isOk());

        for (int i = 0; i < 110; i++) {
            mockMvc.perform(get("/links").header("Tg-Chat-Id", "123"));
        }

        mockMvc.perform(get("/links")).andExpect(status().isTooManyRequests());
    }
}
