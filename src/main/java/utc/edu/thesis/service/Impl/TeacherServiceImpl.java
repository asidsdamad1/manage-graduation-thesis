package utc.edu.thesis.service.Impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.dto.TeacherDto;
import utc.edu.thesis.domain.entity.Teacher;
import utc.edu.thesis.exception.request.BadRequestException;
import utc.edu.thesis.repository.TeacherRepository;
import utc.edu.thesis.service.TeacherService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final EntityManager manager;

    @Override
    public List<Teacher> getAll() {
        return teacherRepository.getAll();
    }

    @Override
    public TeacherDto getById(Long id) {
        return teacherRepository.findById(id)
                .map(TeacherDto::of)
                .orElseThrow(() -> {
                    throw new BadRequestException("Can't not find teacher by Id: %d".formatted(id));
                });
    }

    @Override
    public TeacherDto addTeacher(TeacherDto dto) {
        if (dto != null) {
            Teacher teacher = Teacher.builder()
                    .fullName(dto.getFullName())
                    .dob(dto.getDob())
                    .gender(dto.getGender())
                    .email(dto.getEmail())
                    .address(dto.getAddress())
                    .phone(dto.getPhone())
                    .build();

            teacherRepository.save(teacher);

            return TeacherDto.of(teacher);
        }
        return null;
    }

    @Override
    public Boolean deleteTeacher(Long id) {
        if (id != null) {
            TeacherDto res = getById(id);
            if (res != null) {
                teacherRepository.delete(TeacherDto.toEntity(res));
                return true;
            }
        }
        return false;
    }

    @Override
    public TeacherDto editTeacher(TeacherDto dto) {
        if (dto.getId() == null) {
            throw new BadRequestException("Id is null");
        }
        TeacherDto response = getById(dto.getId());

        Teacher entity = Teacher.builder()
                .id(response.getId())
                .fullName(dto.getFullName())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .dob(dto.getDob())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .build();

        return TeacherDto.of(teacherRepository.save(entity));
    }

    @Override
    public List<TeacherDto> getTeacher(SearchDto dto) {
        if(dto == null) {
            return null;
        }

        String whereClause = "";
        String orderBy = " ";
        String sql = "select e from Teacher as e where(1=1) ";
        if(StringUtils.hasText(dto.getValueSearch())) {

            if("NAME".equals(dto.getConditionSearch())) {
                whereClause += "AND e.fullName like '%" + dto.getValueSearch() +"%'";
            } else if("EMAIL".equals(dto.getConditionSearch())) {
                whereClause += "AND e.email like '%" + dto.getValueSearch() +"%'";
            } else if("PHONE".equals(dto.getConditionSearch())) {
                whereClause += "AND e.phone like '%" + dto.getValueSearch() +"%'";
            } else {
                whereClause += "AND (e.fullName like '%" + dto.getValueSearch() +"%'" +
                        "OR e.email like '%" + dto.getValueSearch() +"%'" +
                        "OR e.phone like '%" + dto.getValueSearch() +"%'" +
                        "OR e.address like '%" + dto.getValueSearch() +"%'" +
                        "OR e.dob like '%" + dto.getValueSearch() +"%')";
            }
        }
        sql += whereClause + orderBy;
        Query q = manager.createQuery(sql, Teacher.class);

        return q.getResultList();
    }
}