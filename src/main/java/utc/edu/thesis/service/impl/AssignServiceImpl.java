package utc.edu.thesis.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.dto.TeacherDto;
import utc.edu.thesis.domain.entity.Assign;
import utc.edu.thesis.domain.entity.Student;
import utc.edu.thesis.exception.request.BadRequestException;
import utc.edu.thesis.exception.request.NotFoundException;
import utc.edu.thesis.repository.AssignRepository;
import utc.edu.thesis.service.AssignService;
import utc.edu.thesis.service.StudentService;
import utc.edu.thesis.service.TeacherService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignServiceImpl implements AssignService {
    private final AssignRepository assignRepository;
    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final EntityManager entityManager;

    @Override
    public Assign addAssign(Assign request) {
        if (request != null) {
            if (studentService.findByCode(request.getStudent().getCode()) != null) {
                throw new BadRequestException("Student with code: %s existed".formatted(request.getStudent().getCode()));
            }

            Assign assign = Assign.builder()
                    .createdDate(LocalDateTime.now())
                    .createdBy(userService.getCurrentUser().getUsername())
                    .teacher(request.getTeacher())
                    .student(request.getStudent())
                    .build();

            assignRepository.save(assign);
        }
        return null;
    }

    @Override
    public Boolean deleteAssign(Long id) {
        if (id != null) {
            Assign res = assignRepository.findById(id).orElseThrow(() -> {
                throw new NotFoundException("not found id: %d".formatted(id));
            });
            if (res != null) {
                assignRepository.delete(res);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Assign> getAssign(SearchDto dto) {
        if (dto == null) {
            throw new NotFoundException("not found");
        }

        String whereClause = "";
        String orderBy = " ";
        String sql = "select e from Assign as e where(1=1) ";
        if (StringUtils.hasText(dto.getValueSearch())) {
            List<TeacherDto> teacher = teacherService.getTeacher(new SearchDto(dto.getValueSearch(), dto.getConditionSearch()));

            if ("EMAIL".equals(dto.getConditionSearch())) {
                whereClause += " AND e.teacher_id = " + teacher.get(0).getId();
            }
        }
        sql += whereClause + orderBy;
        Query q = entityManager.createQuery(sql, Student.class);

        return q.getResultList();
    }
}
