package utc.edu.thesis.service;

import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.dto.ProjectDto;

@Service
public interface ProjectService {
    ProjectDto addProject(ProjectDto projectDto);
}
