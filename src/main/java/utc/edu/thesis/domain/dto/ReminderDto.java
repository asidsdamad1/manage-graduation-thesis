package utc.edu.thesis.domain.dto;

import lombok.Data;
import utc.edu.thesis.domain.entity.Student;
import utc.edu.thesis.domain.entity.Teacher;

import java.time.LocalDateTime;

@Data
public class ReminderDto {
    private Long id;
    private String title;
    private LocalDateTime time;
    private String content;
    private Teacher teacher;
    private Student student;
}
