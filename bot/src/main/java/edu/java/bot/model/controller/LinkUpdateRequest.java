package edu.java.bot.model.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

public record LinkUpdateRequest(
    @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @NotNull
    Long id,
    @Schema(name = "url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @NotNull
    @Valid
    URI url,
    @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @NotNull
    String description,
    @Schema(name = "tgChatIds", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @NotNull
    List<Long> tgChatIds
) {
}
