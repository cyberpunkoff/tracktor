package edu.java.clients.stackoverflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record QuestionResponse(List<ItemResponse> items) {
    public record ItemResponse(
            @JsonProperty("question_id") Long id,
            @JsonProperty("view_count")
            Integer viewCount,
            @JsonProperty("answer_count")
            Integer answerCount,
            @JsonProperty("last_activity_date")
            OffsetDateTime lastActivityDate,
            String title
    ) {
    }
}
