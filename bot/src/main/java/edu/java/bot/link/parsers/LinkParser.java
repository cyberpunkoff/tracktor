package edu.java.bot.link.parsers;

import edu.java.bot.link.links.Link;

public abstract class LinkParser {
    protected LinkParser next;

    public abstract Link parse(String url);

    public void setNext(LinkParser next) {
        this.next = next;
    }
}
