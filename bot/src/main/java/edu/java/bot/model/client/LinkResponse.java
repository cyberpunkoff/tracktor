package edu.java.bot.model.client;

import java.util.List;

public record LinkResponse(
    List<LinkResponse> links,
    int size
) {
}
