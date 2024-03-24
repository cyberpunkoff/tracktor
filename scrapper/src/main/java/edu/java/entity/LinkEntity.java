package edu.java.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "links")
public class LinkEntity {
    @Id
    private Long id;
    private URI url;
    private Timestamp updatedAt;
    private Timestamp checkedAt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "chats_links",
        joinColumns = @JoinColumn(name = "link_id"),
        inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private List<ChatEntity> trackedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(Timestamp checkedAt) {
        this.checkedAt = checkedAt;
    }

    public List<ChatEntity> getTrackedBy() {
        return trackedBy;
    }

    public void setTrackedBy(List<ChatEntity> trackedBy) {
        this.trackedBy = trackedBy;
    }
}
