package utc.edu.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utc.edu.thesis.domain.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select e from Student e where e.code = ?1")
    Student findByCode(String code);
}
