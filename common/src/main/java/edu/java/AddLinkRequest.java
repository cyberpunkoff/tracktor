package edu.java;

import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;

public record AddLinkRequest(
    @Schema(name = "link", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    URI link
) {
}
