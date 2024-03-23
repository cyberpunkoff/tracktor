package edu.java.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import jakarta.persistence.Table;
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
    @ManyToMany
    @JoinTable(
        name = "chats_links",
        joinColumns = @JoinColumn(name = "link_id"),
        inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private List<ChatEntity> trackedBy;

}
