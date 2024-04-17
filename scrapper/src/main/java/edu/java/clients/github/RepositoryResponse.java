package edu.java.clients.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RepositoryResponse(
    Long id,
    @JsonProperty("pushed_at")
    OffsetDateTime updatedAt
) {
}
