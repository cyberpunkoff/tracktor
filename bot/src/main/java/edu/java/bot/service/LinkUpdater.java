package edu.java.bot.service;

import edu.java.bot.model.controller.LinkUpdateRequest;

public interface LinkUpdater {
    int update(LinkUpdateRequest linkUpdateRequest);
}
