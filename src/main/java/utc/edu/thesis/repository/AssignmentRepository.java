package utc.edu.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utc.edu.thesis.domain.entity.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    @Query("select count(distinct e.teacher.id) from Assignment e where e.session.id = ?1")
    Integer countAssignmentBySession(Long sessionId);

    @Query("select count(e.student.id) from Assignment e where e.session.id = ?1 and e.teacher.id=?2")
    Integer countStudentBySession(Long sessionId, Long teacherId);
}
