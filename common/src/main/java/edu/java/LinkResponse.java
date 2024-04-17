package edu.java;

import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;

public record LinkResponse(
    @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    int id,
    @Schema(name = "url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    URI url
) {
}
