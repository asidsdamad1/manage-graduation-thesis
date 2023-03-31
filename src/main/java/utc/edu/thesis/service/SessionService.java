package utc.edu.thesis.service;

import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.dto.SessionDto;
import utc.edu.thesis.domain.entity.Assign;
import utc.edu.thesis.domain.entity.Session;

import java.util.List;

@Service
public interface SessionService {
    Session addSession(Session request);
    Session editSession(Session request);
    Boolean deleteSession(Long id);
    List<SessionDto> getSession(SearchDto dto);
}
