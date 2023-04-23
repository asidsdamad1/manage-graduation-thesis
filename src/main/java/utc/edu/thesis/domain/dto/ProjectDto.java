package utc.edu.thesis.domain.dto;

import lombok.Data;
import utc.edu.thesis.domain.entity.Project;
import utc.edu.thesis.domain.entity.Student;
import utc.edu.thesis.domain.entity.Teacher;
import utc.edu.thesis.domain.entity.Topic;
import utc.edu.thesis.util.ObjectMapperUtil;

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
    private String reportFile;
    private String outlineFile;

    public static ProjectDto of(Project entity) {
        return ObjectMapperUtil.OBJECT_MAPPER.convertValue(entity, ProjectDto.class);
    }

    public static Project toEntity(ProjectDto dto) {
        return ObjectMapperUtil.OBJECT_MAPPER.convertValue(dto, Project.class);
    }
}
