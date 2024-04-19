package edu.java.model.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;

public record ListLinksResponse(
    @Schema(name = "links", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Valid
    List<@Valid LinkResponse> links,
    @Schema(name = "size", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    int size
) {
}
