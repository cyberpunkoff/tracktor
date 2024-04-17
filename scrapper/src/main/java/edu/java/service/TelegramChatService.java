package edu.java.service;

public interface TelegramChatService {
    void register(long tgChatId);

    void unregister(long tgChatId);
}
