package utc.edu.thesis.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import utc.edu.thesis.domain.dto.AssignmentDto;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.dto.TeacherDto;
import utc.edu.thesis.domain.entity.Assignment;
import utc.edu.thesis.exception.request.BadRequestException;
import utc.edu.thesis.exception.request.NotFoundException;
import utc.edu.thesis.repository.AssignmentRepository;
import utc.edu.thesis.service.AssignmentService;
import utc.edu.thesis.service.StudentService;
import utc.edu.thesis.service.TeacherService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final EntityManager entityManager;

    @Override
    public Assignment addAssign(Assignment request) {
        if (request != null) {
            if (studentService.findByCode(request.getStudent().getCode()) != null) {
                throw new BadRequestException("Student with code: %s existed".formatted(request.getStudent().getCode()));
            }

            Assignment assignment = Assignment.builder()
                    .createdDate(LocalDateTime.now())
                    .createdBy(userService.getCurrentUser().getUsername())
                    .teacher(request.getTeacher())
                    .student(request.getStudent())
                    .build();

            assignmentRepository.save(assignment);
        }
        return null;
    }

    @Override
    public Boolean deleteAssign(Long id) {
        if (id != null) {
            Assignment res = assignmentRepository.findById(id).orElseThrow(() -> {
                throw new NotFoundException("not found id: %d".formatted(id));
            });
            if (res != null) {
                assignmentRepository.delete(res);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<AssignmentDto> getAssign(SearchDto dto) {
        if (dto == null) {
            throw new NotFoundException("not found");
        }

        String whereClause = "";
        String orderBy = " ";
        String sql = "select e from Assignment as e where(1=1) ";
        if (StringUtils.hasText(dto.getValueSearch())) {
            List<TeacherDto> teacher = teacherService.getTeacher(new SearchDto(dto.getValueSearch(), dto.getConditionSearch()));
            if ("EMAIL".equals(dto.getConditionSearch())) {
                whereClause += " AND e.teacher_id = " + teacher.get(0).getId();
            } else if ("SESSION".equals(dto.getConditionSearch())) {
                whereClause += " AND e.session.id = " + dto.getValueSearch();
            } else if ("ID".equals(dto.getConditionSearch())) {
                whereClause += " AND e.id = " + dto.getValueSearch();
            }
        }
        sql += whereClause+ orderBy;
        Query q = entityManager.createQuery(sql, Assignment.class);
        List<Assignment> resultQuery = q.getResultList();
        List<AssignmentDto> res = new ArrayList<>();
        for (Assignment assignment : resultQuery) {
            AssignmentDto assignmentDto = AssignmentDto.of(assignment);
            assignmentDto.setAmount(
                    assignmentRepository.countStudentByAssignment(
                            assignment.getSession().getId(), assignment.getTeacher().getId()).size());
            res.add(assignmentDto);
        }

        return res.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDto> getStudent(Long sessionId, Long teacherId) {
        if(sessionId != null && teacherId != null) {
            return assignmentRepository.countStudentByAssignment(sessionId, teacherId).stream()
                    .map(AssignmentDto::of)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Integer countAssignmentBySession(Long sessionId) {

        return assignmentRepository.countAssignmentBySession(sessionId);
    }
}
