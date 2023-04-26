package utc.edu.thesis.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.dto.*;
import utc.edu.thesis.domain.entity.Project;
import utc.edu.thesis.repository.ProjectRepository;
import utc.edu.thesis.service.ProjectService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public ProjectDto addProject(ProjectDto dto) {
        if (dto != null) {
            Project project = Project.builder()
                    .createDate(LocalDateTime.now())
                    .endDate(dto.getEndDate())
                    .name(dto.getName())
                    .outlineFile(dto.getOutlineFile())
                    .reportFile(dto.getReportFile())
                    .topic(TopicDto.toEntity(dto.getTopic()))
                    .session(SessionDto.toEntity(dto.getSession()))
                    .teacher(TeacherDto.toEntity(dto.getTeacher()))
                    .student(StudentDto.toEntity(dto.getStudent()))
                    .build();

            projectRepository.save(project);

            return ProjectDto.of(project);
        }
        return null;
    }
}
