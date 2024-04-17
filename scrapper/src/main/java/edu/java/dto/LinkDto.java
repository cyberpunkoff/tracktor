package edu.java.dto;

import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LinkDto {
    private Long id;
    private URI url;
    private Timestamp updatedAt;
    private Timestamp checkedAt;
    private final List<Chat> trackedBy = new ArrayList<>();

    public LinkDto(URI url) {
        this.url = url;
    }
}
