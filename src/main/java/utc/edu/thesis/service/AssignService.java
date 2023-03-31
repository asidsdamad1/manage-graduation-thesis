package utc.edu.thesis.service;

import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.entity.Assign;

import java.util.List;

@Service
public interface AssignService {
    Assign addAssign(Assign request);
    Boolean deleteAssign(Long id);
    List<Assign> getAssign(SearchDto dto);
}
