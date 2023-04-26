package utc.edu.thesis.service.teacher.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import utc.edu.thesis.domain.dto.ProjectDto;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.entity.Project;
import utc.edu.thesis.exception.request.NotFoundException;
import utc.edu.thesis.repository.ProjectRepository;
import utc.edu.thesis.service.teacher.ManagerProjectService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerProjectServiceImpl implements ManagerProjectService {
    private final EntityManager manager;
    private final ProjectRepository projectRepository;

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
        }
        sql += whereClause + orderBy;
        Query q = manager.createQuery(sql, Project.class);
        List<Project> resQuery = q.getResultList();
        List<ProjectDto> res = new ArrayList<>();

        resQuery.forEach(project -> res.add(ProjectDto.of(project)));
        return res;
    }

    @Override
    public ProjectDto addProject(ProjectDto projectDto) {
        return null;
    }
}
