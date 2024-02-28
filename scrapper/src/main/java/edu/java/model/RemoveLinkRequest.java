package edu.java.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;

public record RemoveLinkRequest(
    @Schema(name = "link", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    URI request
) {
}
