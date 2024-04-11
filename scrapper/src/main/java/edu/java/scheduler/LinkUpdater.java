package edu.java.scheduler;

import edu.java.LinkUpdateRequest;
import edu.link.links.Link;
import java.time.OffsetDateTime;
import java.util.List;

public abstract class LinkUpdater {
    public abstract List<LinkUpdateRequest> getLinkUpdates();
    public abstract boolean isLinkUpdated();
    public abstract OffsetDateTime getUpdatedAt();
}
