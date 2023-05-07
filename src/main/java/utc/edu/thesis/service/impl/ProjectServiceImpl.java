package utc.edu.thesis.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import utc.edu.thesis.domain.dto.*;
import utc.edu.thesis.domain.entity.Project;
import utc.edu.thesis.domain.enumaration.StatusEnum;
import utc.edu.thesis.exception.request.BadRequestException;
import utc.edu.thesis.exception.request.NotFoundException;
import utc.edu.thesis.repository.ProjectRepository;
import utc.edu.thesis.service.AmazonUploadService;
import utc.edu.thesis.service.ProjectService;
import utc.edu.thesis.service.SessionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final AmazonUploadService aws3Service;
    private final SessionService sessionService;
    private final EntityManager entityManager;

    @Override
    public List<ProjectDto> getProjects(SearchDto dto) {
        if (dto == null) {
            throw new NotFoundException("not found");
        }

        String whereClause = "";
        String orderBy = " ";
        String sql = "select e from Project as e where(1=1) ";
        if (StringUtils.hasText(dto.getValueSearch())) {
            if ("NAME".equals(dto.getConditionSearch())) {
                whereClause += "AND e.name like '%" + dto.getValueSearch() + "%'";
            }
            if ("STUDENT".equals(dto.getConditionSearch())) {
                whereClause += "AND e.student.id = " + dto.getValueSearch()
                        + " AND e.session.id = " + sessionService.getSessionActive().getId();
            }
        }
        sql += whereClause + orderBy;
        Query q = entityManager.createQuery(sql, Project.class);
        List<Project> resQuery = q.getResultList();
        List<ProjectDto> res = new ArrayList<>();

        resQuery.forEach(project -> {
            ProjectDto projectDto = ProjectDto.of(project);
            projectDto.setStatus(StatusEnum.values()[project.getStatus()]);
            res.add(projectDto);
        });
        return res;
    }

    @Override
    public ProjectDto addProject(ProjectDto dto) {
        if (dto != null) {
            List<ProjectDto> projectDtos = getProjects(new SearchDto(String.valueOf(dto.getStudent().getId()), "STUDENT"));
            if(!projectDtos.isEmpty()) {
                throw new BadRequestException("Student had a project");
            }
            Project project = Project.builder()
                    .createDate(LocalDateTime.now())
                    .name(dto.getName())
                    .outlineFile(dto.getOutlineFile())
                    .reportFile(dto.getReportFile())
                    .topic(TopicDto.toEntity(dto.getTopic()))
                    .session(SessionDto.toEntity(dto.getSession()))
                    .teacher(TeacherDto.toEntity(dto.getTeacher()))
                    .student(StudentDto.toEntity(dto.getStudent()))
                    .status(0)
                    .build();

            projectRepository.save(project);

            return ProjectDto.of(project);
        }
        return null;
    }

    @Override
    public ProjectDto addOutlineFile(Long projectId, MultipartFile file) {
        if (!file.isEmpty()) {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> {
                        throw new NotFoundException("Can not find project with id: %lf".formatted(projectId));
                    });

            String outlineFile = aws3Service.uploadFile(file);
            project.setOutlineFile(outlineFile);
            projectRepository.save(project);
            return ProjectDto.of(project);
        }
        return null;
    }
}
