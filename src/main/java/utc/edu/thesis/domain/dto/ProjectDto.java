package utc.edu.thesis.domain.dto;

import lombok.Data;
import utc.edu.thesis.domain.entity.Student;
import utc.edu.thesis.domain.entity.Teacher;
import utc.edu.thesis.domain.entity.Topic;

import java.time.LocalDateTime;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime endDate;
    private Student student;
    private Teacher teacher;
    private Topic topic;
}
