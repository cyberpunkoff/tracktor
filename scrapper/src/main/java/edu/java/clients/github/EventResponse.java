package edu.java.clients.github;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record EventResponse(EventType type, @JsonProperty("created_at") OffsetDateTime createdAt) {
    public enum EventType {
        @JsonProperty("ForkEvent")
        FORK,
        @JsonProperty("PullRequestEvent")
        PULL_REQUEST,
        @JsonProperty("IssuesEvent")
        ISSUE,
        @JsonProperty("PushEvent")
        PUSH,
        @JsonEnumDefaultValue
        DEFAULT
    }
}
