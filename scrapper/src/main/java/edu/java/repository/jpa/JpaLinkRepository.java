package edu.java.repository.jpa;

import edu.java.dto.LinkDto;
import edu.java.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
}
