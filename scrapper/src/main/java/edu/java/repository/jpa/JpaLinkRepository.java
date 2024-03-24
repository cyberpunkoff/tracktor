package edu.java.repository.jpa;

import edu.java.entity.LinkEntity;
import java.net.URI;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    @Query("SELECT l from LinkEntity l JOIN l.trackedBy c WHERE c.chatId = :id")
    List<LinkEntity> findByTrackedById(Long id);

    @Modifying
    @Query("update LinkEntity set updatedAt = ?1 where url = ?2")
    void updateUpdatedAt(OffsetDateTime time, URI url);

    @Modifying
    @Query("update LinkEntity set checkedAt = ?1 where url = ?2")
    void updateCheckedAt(OffsetDateTime time, URI url);

    LinkEntity findFirstByUrl(URI url);

    List<LinkEntity> findByCheckedAtBefore(Timestamp before);
}
