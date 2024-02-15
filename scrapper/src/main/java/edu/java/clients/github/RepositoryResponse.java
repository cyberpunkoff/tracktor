package edu.java.clients.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryResponse {
    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
}
