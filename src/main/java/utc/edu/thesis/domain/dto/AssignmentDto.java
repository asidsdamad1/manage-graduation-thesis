package utc.edu.thesis.domain.dto;

import lombok.Data;
import utc.edu.thesis.domain.entity.Assignment;
import utc.edu.thesis.domain.entity.Session;
import utc.edu.thesis.domain.entity.Student;
import utc.edu.thesis.domain.entity.Teacher;
import utc.edu.thesis.util.ObjectMapperUtil;

import java.time.LocalDateTime;

@Data
public class AssignmentDto {
    private Long id;
    private LocalDateTime createdDate;
    private String createdBy;
    private Session session;
    private Student student;
    private Teacher teacher;
    private Integer amount;

    public static AssignmentDto of(Assignment entity) {
        return ObjectMapperUtil.OBJECT_MAPPER.convertValue(entity, AssignmentDto.class);
    }

    public static Assignment toEntity(AssignmentDto dto) {
        return ObjectMapperUtil.OBJECT_MAPPER.convertValue(dto, Assignment.class);
    }
}
