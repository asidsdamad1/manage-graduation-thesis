package utc.edu.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utc.edu.thesis.domain.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
