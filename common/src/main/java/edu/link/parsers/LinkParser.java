package edu.link.parsers;

import edu.link.links.Link;

public abstract class LinkParser {
    protected LinkParser next;

    public abstract Link parse(String url);

    public void setNext(LinkParser next) {
        this.next = next;
    }
}
