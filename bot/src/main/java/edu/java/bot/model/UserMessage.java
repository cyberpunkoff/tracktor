package edu.java.bot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserMessage {
    private String command;
    private String[] arguments;
}
