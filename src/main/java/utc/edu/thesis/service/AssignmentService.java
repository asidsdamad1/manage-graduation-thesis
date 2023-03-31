package utc.edu.thesis.service;

import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.entity.Assignment;

import java.util.List;

@Service
public interface AssignmentService {
    Assignment addAssign(Assignment request);
    Boolean deleteAssign(Long id);
    List<Assignment> getAssign(SearchDto dto);
    Integer countAssignmentBySession(Long sessionId);
}
