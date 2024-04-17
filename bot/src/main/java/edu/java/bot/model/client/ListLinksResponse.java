package edu.java.bot.model.client;

import java.util.List;

public record ListLinksResponse(
    List<LinkResponse> links,
    int size
) {
}
