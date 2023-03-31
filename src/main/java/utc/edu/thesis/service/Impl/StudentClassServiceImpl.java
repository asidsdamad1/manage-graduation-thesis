package utc.edu.thesis.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.entity.Student;
import utc.edu.thesis.domain.entity.StudentClass;
import utc.edu.thesis.exception.request.NotFoundException;
import utc.edu.thesis.repository.StudentClassRepository;
import utc.edu.thesis.service.StudentClassService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentClassServiceImpl implements StudentClassService {
    private final StudentClassRepository studentClassRepository;
    private final EntityManager entityManager;

    @Override
    public StudentClass findByNameAndCourse(String name, Integer course) {
        return studentClassRepository.findByNameAndCourse(name, course).orElseThrow(() -> {
            throw new NotFoundException("not found class");
        });
    }

    @Override
    public List<StudentClass> getAll() {
        return studentClassRepository.findAll();
    }

    @Override
    public List<StudentClass> getStudentClass(SearchDto dto) {
        if (dto == null) {
            throw new NotFoundException("not found");
        }

        String whereClause = "";
        String orderBy = " ";
        String sql = "select e from StudentClass as e where(1=1) ";
        if (StringUtils.hasText(dto.getValueSearch())) {
            if ("ID".equals(dto.getConditionSearch())) {
                whereClause += " AND e.id = " + dto.getValueSearch();
            }
        }
        sql += whereClause + orderBy;
        Query q = entityManager.createQuery(sql, StudentClass.class);

        return q.getResultList();
    }
}