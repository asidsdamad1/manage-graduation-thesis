package utc.edu.thesis.domain.dto;

import lombok.Builder;
import lombok.Data;
import utc.edu.thesis.domain.entity.Teacher;
import utc.edu.thesis.util.ObjectMapperUtil;

import java.time.LocalDate;

@Data
@Builder
public class TeacherDto {
    private Long id;
    private String fullName;
    private LocalDate dob;
    private String gender;
    private String address;
    private String phone;
    private String email;

    public static TeacherDto of(Teacher entity) {
        return ObjectMapperUtil.OBJECT_MAPPER.convertValue(entity, TeacherDto.class);
    }
}
