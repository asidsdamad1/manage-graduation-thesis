package utc.edu.thesis.domain.dto;

import lombok.Data;
import utc.edu.thesis.domain.entity.Assign;
import utc.edu.thesis.domain.entity.Session;
import utc.edu.thesis.domain.entity.Student;
import utc.edu.thesis.domain.entity.Teacher;
import utc.edu.thesis.util.ObjectMapperUtil;

import java.time.LocalDateTime;

@Data
public class AssignDto {
    private Long id;
    private LocalDateTime createdDate;
    private String createdBy;
    private Session session;
    private Student student;
    private Teacher teacher;
    private Integer amount;

    public static AssignDto of(Assign entity) {
        return ObjectMapperUtil.OBJECT_MAPPER.convertValue(entity, AssignDto.class);
    }

    public static Assign toEntity(AssignDto dto) {
        return ObjectMapperUtil.OBJECT_MAPPER.convertValue(dto, Assign.class);
    }
}
