package edu.java.clients.stackoverflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    @JsonProperty("view_count")
    private Integer viewCount;
    @JsonProperty("answer_count")
    private Integer answerCount;
    @JsonProperty("last_activity_date")
    private OffsetDateTime lastActivityDate;
    private String title;
}
