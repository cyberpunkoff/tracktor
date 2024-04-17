package edu.java.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat")
public class ChatEntity {
    @Id
    private Long id;
    @ManyToMany(mappedBy = "trackedBy")
    private List<LinkEntity> links;

    public ChatEntity(long tgChatId) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long chatId) {
        this.id = chatId;
    }

    public List<LinkEntity> getLinks() {
        return links;
    }

    public void setLinks(List<LinkEntity> links) {
        this.links = links;
    }
}
