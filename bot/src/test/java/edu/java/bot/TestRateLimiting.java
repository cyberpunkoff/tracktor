package edu.java.bot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestRateLimiting {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRateLimiting() throws Exception {
        for (int i = 0; i < 100; i++) {
            mockMvc.perform(get("/updates")).andExpect(status().is(405));
        }

        mockMvc.perform(get("/updates")).andExpect(status().isTooManyRequests());
    }
}