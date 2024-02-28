package edu.java.bot.model;

import jakarta.validation.constraints.NotNull;

public record LinkUpdateRequest(
    @NotNull
    int id,
    @NotNull
    String url,
    @NotNull
    String description,
    @NotNull
    int[] tgChatIds
) {
}
